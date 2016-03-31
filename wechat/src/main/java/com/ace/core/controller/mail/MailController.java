package com.ace.core.controller.mail;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.core.bean.mail.Mail;
import com.ace.mail.SimpleMailSender;



@Controller
@RequestMapping("/mail")
public class MailController {

	
	@RequestMapping("/compose.do")
	public String writeMail(Model model,HttpServletRequest request){
		model.addAttribute("base", getHttpPath(request));
		return "mail_compose";
	}
	
	@RequestMapping("/box.do")
	public String getMail(){
		return "mail_box";
	}
	private String getHttpPath(HttpServletRequest request){
		StringBuilder path = new StringBuilder();
		
		path.append(request.getScheme() + "://");
		path.append(request.getServerName() + ":");
		path.append(request.getServerPort());
		path.append(request.getContextPath());
		
		return path.toString();
	}
	@RequestMapping("/sendMail.do")
	public String sendMail(Mail mail){
		mail.setAttachFileNames(new String[]{"F://办公电脑IP列表.xlsx"});		// 这个类主要来发送邮件
		System.out.println(SimpleMailSender.sendHtmlMail(mail));
		return "mail_box";
	}
}
