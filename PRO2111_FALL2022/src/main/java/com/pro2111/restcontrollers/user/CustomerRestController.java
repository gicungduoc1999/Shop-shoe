package com.pro2111.restcontrollers.user;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.beans.OTP;
import com.pro2111.entities.User;
import com.pro2111.service.SmtpMailSender;
import com.pro2111.service.UserService;
import com.pro2111.utils.Common;
import com.pro2111.utils.EncryptUtil;

@CrossOrigin("*")
@RestController
@RequestMapping("user/rest/customer")
public class CustomerRestController {
	@Autowired
	private UserService userService;
	@Autowired
	private SmtpMailSender smtpMailSender;

	@PostMapping
	public ResponseEntity<User> createCustomer(@Valid @RequestBody User user) {
		try {
			String pwd = EncryptUtil.encrypt(user.getPassword());
			Random random = new Random();
			int otp = random.nextInt(900000) + 100000;
			user.setOtp(otp);
			user.setStatus(2);
			user.setCreatedDate(LocalDateTime.now());
			user.setPassword(pwd);
			user.setRole(1);
			smtpMailSender.sendMail(user.getEmail(), "Xác thực tài khoản", "Mã OTP của bạn là:" + otp);
			return ResponseEntity.ok(userService.create(user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<?> confirmOtp(@PathVariable("id") Integer id, @RequestBody OTP userOTP) {
		try {
			User user = userService.findById(id);
			int otp = userOTP.getOtp();
			Map<String, String> errorsMap = new HashMap<>();
			if (!Common.checkCreatedDateUser(user.getCreatedDate())) {
				errorsMap.put("createdDate", "Mã OTP của bạn đã hết hạn !");
			} else {
				if(otp == 0) {
					errorsMap.put("OTP", "Bạn chưa nhập mã OTP!");
				}else if (otp != user.getOtp()) {
					errorsMap.put("OTP", "Mã OTP của không đúng !");
				}
			}
			if (errorsMap != null && !errorsMap.isEmpty()) {
				return ResponseEntity.badRequest().body(errorsMap);
			} else {
				user.setStatus(1);
				return ResponseEntity.ok().body(userService.update(user));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
