package com.pro2111.restcontrollers.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.beans.PasswordBean;
import com.pro2111.entities.User;
import com.pro2111.service.UserService;
import com.pro2111.utils.EncryptUtil;


@RestController
@CrossOrigin("*")
@RequestMapping("admin/rest/users")
public class UserRestControllerAdmin {
	@Autowired
	private UserService userService;

	static User userStatic;

	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		try {
			return ResponseEntity.ok(userService.getAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<User> findById(@PathVariable("id") Integer id) {
		try {
			User user = userService.findById(id);
			userStatic = user;
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-user-sale")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<User>> findUserSalse() {
		try {
			return ResponseEntity.ok(userService.findUserSalse());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-by-status-true")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<User>> findByStatusTrue() {
		try {
			return ResponseEntity.ok(userService.findByStatus(1));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-approximate-phone/{phone}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<User>> findApproximatePhone(@PathVariable("phone") String phone) {
		try {
			return ResponseEntity.ok(userService.findApproximatePhone(phone));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("find-approximate-phone-or-email/{input}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<List<User>> findApproximatePhoneorEmail(@PathVariable("input") String input) {
		try {
			return ResponseEntity.ok(userService.findApproximatePhoneorEmail(input));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PostMapping
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<User> create(@Valid @RequestBody User user) {
		try {
			String pwd = EncryptUtil.encrypt(user.getPassword());
			user.setPassword(pwd);
			return ResponseEntity.ok(userService.create(user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<User> update(@PathVariable("id") Integer id, @Valid @RequestBody User user) {
		try {
			User userOld = userService.findById(id);
			user.setUserId(id);
			user.setPassword(userOld.getPassword());
			return ResponseEntity.ok(userService.update(user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("change-password/{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<?> changePasssword(@PathVariable("id") Integer id,
			@Valid @RequestBody PasswordBean passwordBean) {
		try {
			User user = userService.findById(id);
			Boolean checkPassOld = EncryptUtil.check(passwordBean.getPasswordOld(), user.getPassword());
			Map<String, String> mapError = new HashMap<>();
			if (!checkPassOld) {
				mapError.put("passwordOld", "Old password is not correct !");
				return ResponseEntity.badRequest().body(mapError);
			}else if(!passwordBean.getPasswordNew().equals(passwordBean.getConfirmPassword())) {
				mapError.put("confirmPassword", "Confirmation password is incorrect !");
				return ResponseEntity.badRequest().body(mapError);
			}
			String passwordHash = EncryptUtil.encrypt(passwordBean.getPasswordNew());
			user.setPassword(passwordHash);
			return ResponseEntity.ok(userService.update(user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("{id}")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<User> update(@PathVariable("id") User user) {
		try {
			return ResponseEntity.ok(userService.delete(user));
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

	public static User getUserStatic() {
		return userStatic;
	}

}
