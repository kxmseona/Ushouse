package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Service.MailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MailController {
	
	private final MailService mailservice;
	
	@PostMapping("/email/auth")
	public @ResponseBody String sendEmail(@RequestParam String email, Model model) throws Exception {
		log.info("받아온 이메일:" + email);
		String codecheck = mailservice.sendCertificationMail(email);
		log.info("받아온 코드값 :" + codecheck);
		//model.addAttribute("codecheck", codecheck);
		
		return codecheck;
	}
	

}
