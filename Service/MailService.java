package com.example.demo.Service;

import org.springframework.stereotype.Service;

import java.util.UUID;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.*;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MailService {
	
	private final JavaMailSender javaMailSender;
	
	private MimeMessage createMessage(String code, String email) throws Exception{
		MimeMessage message = javaMailSender.createMimeMessage();
		
		message.addRecipients(Message.RecipientType.TO, email);
		message.setSubject("어스하우스 회원가입 인증 번호입니다.");
		message.setText("인증코드 : " + code);
		
		message.setFrom("toyproject0010@naver.com");
		
		return message;
	}
	
	public String sendMail(String code, String email) throws Exception{
		MimeMessage mimeMessage = createMessage(code, email);
		try {
			
			javaMailSender.send(mimeMessage);
			
		}catch (MailException mailException) {
			mailException.printStackTrace();
			throw new IllegalAccessException();
		}
		return code;
	}
	
	public String sendCertificationMail(String email) throws Exception{
		
		
		String code =  UUID.randomUUID().toString().substring(0, 6);
		log.info("여기는 서비스 이메일 값은:"+ email +"생성된 코드는:" + code);
		String codecheck = sendMail(code, email);
		log.info("받아온 코드값:" + codecheck);
		return codecheck;
		
	}

}
