package com.springboot.Util;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {

	@Autowired
	private static JavaMailSender javaMailSender;
	
	public static Boolean sendEmail(String email, String url) throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("nk685602@gmail.com","E-Commerce");
		helper.setText(email);
		String content = "<p>Hello,</p>"+"<p>You have request to reset Password</p>"
						+"<p>Click the below link to reset password:</p>"
						+"<p><a href=\""+url+"\">Change my password</a></p>";
		helper.setSubject("Password Reset");
		helper.setText(content, true);
		javaMailSender.send(message);
		return true;
	}

	public String generateUrl(HttpServletRequest request) {
		// TODO Auto-generated method stub	
		String siteUrl = request.getRequestURL().toString();		
		return siteUrl.replace(request.getServletPath(), "");
	}
}
