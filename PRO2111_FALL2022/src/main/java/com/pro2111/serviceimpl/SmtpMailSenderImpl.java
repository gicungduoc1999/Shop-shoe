/**
 * DATN_FALL2022, 2022
 * SmtpMailSenderIml.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pro2111.service.SmtpMailSender;

/**
 * Thực thi lại các phương thức của class SmtpMailSender và xử lý
 * 
 * @author BUI_QUANG_HIEU
 *
 */
@Service
public class SmtpMailSenderImpl implements SmtpMailSender {

	@Value("${spring.mail.username}")
	private String from;

	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * @param to:      email người nhận
	 * @param subject: tiêu đề mail
	 * @param body:    nội dung
	 */
	@Override
	public synchronized void sendMail(String to, String subject, String body) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true);// true indicates multipart message

		helper.setFrom(from); // <--- THIS IS IMPORTANT
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body, true);// true indicates body is html
		javaMailSender.send(message);

	}

}
