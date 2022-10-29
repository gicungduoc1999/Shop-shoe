/**
 * DATN_FALL2022, 2022
 * SendMailRestControllerAdmin.java, BUI_QUANG_HIEU
 */
package com.pro2111.restcontrollers.admin;

import java.util.Random;

import javax.mail.MessagingException;
import javax.management.loading.PrivateClassLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro2111.beans.SendMail;
import com.pro2111.entities.User;
import com.pro2111.service.SmtpMailSender;
import com.pro2111.service.UserService;
import com.pro2111.serviceimpl.SmtpMailSenderImpl;
import com.pro2111.utils.EncryptUtil;

/**
 * Tạo ra các API liên quan đến send mail
 * 
 * @author BUI_QUANG_HIEU
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping("admin/rest/send-email")
public class SendMailRestControllerAdmin {

	@Autowired
	private SmtpMailSender smtpMailSender;

	@Autowired
	private UserService userService;

	private String _to = "";
	private String _subject = "";
	private String _content = "";

	private Integer _countRun = 0;

	@PostMapping("test")
	public ResponseEntity<SendMail> sendMail(@RequestBody SendMail mail) throws MessagingException {
		try {
			String text = "<table width='100%' border='1' align='center'>" + "<tr align='center'>"
					+ "<td><b>Email <b></td>" + "<td><b>Connect<b></td>" + "</tr>";

			text = text + "<tr align='center'>" + "<td>" + mail.getTo() + "</td>" + "<td>" + "Hello world !" + "</td>"
					+ "</tr>";
			mail.setContent(mail.getContent() + "\n" + text);
			System.out.println(mail.toString());
			smtpMailSender.sendMail(mail.getTo(), mail.getSubject(), mail.getContent());
			return ResponseEntity.ok(mail);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	@PostMapping("reset-password")
	@PreAuthorize("@appAuthorizer.authorize(authentication)")
	public ResponseEntity<SendMail> resetPassword(@RequestBody SendMail mail) throws MessagingException {
		try {
			_countRun = 0;

			int leftLimit = 97; // letter 'a'
			int rightLimit = 122; // letter 'z'
			int targetStringLength = 10;
			Random random = new Random();

			String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

			String fullName = mail.getContent();
//			mail.setSubject("[Reset Password]");
//			mail.setContent("<p>Dear, <strong>" + fullName + "</strong></p>" + "<p>Your new password is: <strong>"
//					+ generatedString + "</strong></p>"
//					+ "<p>You access the website according to the password above and change it back!</p>"
//					+ "<p>Thank you for using the system!</p>");
//			System.out.println(mail.toString());
//			smtpMailSender.sendMail(mail.getTo(), mail.getSubject(), mail.getContent());

			_to = mail.getTo();
			_subject = "[Reset Password]";
			_content = "<p>Dear, <strong>" + fullName + "</strong></p>" + "<p>Your new password is: <strong>"
					+ generatedString + "</strong></p>"
					+ "<p>You access the website according to the password above and change it back!</p>"
					+ "<p>Thank you for using the system!</p>";
			run();
			try {
				User user = userService.findByEmail(mail.getTo());
				user.setPassword(EncryptUtil.encrypt(generatedString));
				userService.update(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(mail);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	@Scheduled(fixedRate = 1000, initialDelay = 2000)
	public void run() throws MessagingException {
		try {
			if (_countRun < 0) {
				_to = "";
				return;
			}
			_countRun = -1;
			smtpMailSender.sendMail(_to, _subject, _content);

		} catch (Exception e) {

		}
	}
}
