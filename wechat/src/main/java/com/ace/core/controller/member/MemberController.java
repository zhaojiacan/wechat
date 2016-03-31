package com.ace.core.controller.member;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.core.bean.mail.Mail;
import com.ace.core.bean.member.YwyMember;
import com.ace.core.service.member.MemberService;
import com.ace.core.util.ResponseUtils;
import com.ace.mail.SimpleMailSender;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/trialOpen.do")
	public void trialOpen(String data,HttpServletResponse response,ModelMap model){
		JSONObject jo = JSON.parseObject(data);
		JSONObject jo2=new JSONObject();
		String token = jo.getString("token");
		String trial  =jo.getString("data");
		JSONObject trialMap= JSON.parseObject(trial);
		//获取邮件发送地址
		String trialEmail = trialMap.getString("trialEmail");
		if(token.equals("48e1a118a99e59fb09254e42a0335bc8")){
			YwyMember member=new YwyMember();
			YwyMember addMember = memberService.addMember(member);

			
			Mail mail=new Mail();
			String[] toAddresses={trialEmail};
			mail.setToAddress(toAddresses);
			mail.setSubject("生活立方试用申请成功,欢迎试用云物业产品");
			mail.setContent("<p style='text-align:center;'> <img src='http://7xreud.com1.z0.glb.clouddn.com/logocolor-1024x1024.png' width='200' height='200'/>"
					+ "</p>"
					+ "<p style='text-align:center;'>生活立方®智慧物业、智慧社区项目，致力于改变物业传统、微利现状，秉承为行业提高工作效率、降低管理成本、增强盈利能力的宗旨，帮助国内上百家物业公司建立了信息化管理体系，实现了真正的互联网＋物业的智慧社区</p>"
					+ "<h3 style='text-align:center;'>尊敬的用户您好,您的试用信息审核通过,我们已经为您开通了3个月免费试用,请牢记您的用户名和密码：</h3>"
					+ "<p style='text-align:center;'>用户名：<h3  style='text-align:center; color:red;'>"+addMember.getServerName()+"</h3></p>"
							+ "<p style='text-align:center;'>密码：</p><h3 style='text-align:center; color:red;'>"+addMember.getServerPwd()+"</h3>");
			boolean sendHtmlMail = false;
			int flag=0;
			while(sendHtmlMail==false){
				if(flag<=10){	
					sendHtmlMail=SimpleMailSender.sendHtmlMail(mail);
				}else{
					jo2.put("status", 303);
					jo2.put("member", null);
					memberService.delMemberById(addMember.getId());
				}
				flag+=1;
			}
			if(sendHtmlMail){
				jo2.put("status", 200);
				jo2.put("member", JSON.toJSONString(addMember));
			}
		}
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		ResponseUtils.renderJson(response, jo2.toString());
		
	}
}
