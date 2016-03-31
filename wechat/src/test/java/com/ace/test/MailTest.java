package com.ace.test;

import java.util.Properties;

import org.junit.Test;

import com.ace.core.util.PropertiesUtil;
import com.ace.mail.MailSenderInfo;
import com.ace.mail.SimpleMailSender;

public class MailTest {

	@Test
	public void testMail(){
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setMailServerHost("smtp.qq.com");
			mailInfo.setMailServerPort("465");
			mailInfo.setValidate(true);
			mailInfo.setUserName("2636074209@qq.com");
			mailInfo.setPassword("gkqqrgxhjowyecfb");// 您的邮箱密码
			mailInfo.setFromAddress("2636074209@qq.com");
			String[] to = {"898435128@qq.com"};
			mailInfo.setToAddress(to);
			String[] toCC = null;
			mailInfo.setToCarbonCopyAddress(toCC);
			String[] toBCC =null;
			mailInfo.setToBlindCarbonCopyAddress(toBCC);
			mailInfo.setAttachFileNames(new String[]{"F://办公电脑IP列表.xlsx"});
			mailInfo.setSubject("发送HTML邮件");
			String body = "<table width=\"80%\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" style=\"align:center;text-align:center\"><tr><td>你好</td><td>你好</td><td>你好</td></tr></table>";
			mailInfo.setContent(body);
			// 这个类主要来发送邮件
			System.out.println(SimpleMailSender.sendHtmlMail(mailInfo));
	}
	
	@Test
	public void testProperties(){
		//读取邮件基本配置属性
		String filepath="/";
		String filename="config.properties";
		Properties loadPropertyInstance = PropertiesUtil.loadPropertyInstance(filepath, filename);
		System.out.println("MailTest.testProperties()"+loadPropertyInstance.getProperty("mail.fromAdress"));
	}
}
