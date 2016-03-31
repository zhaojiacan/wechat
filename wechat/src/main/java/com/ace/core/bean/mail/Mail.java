package com.ace.core.bean.mail;

import java.util.Properties;

import com.ace.core.util.PropertiesUtil;
import com.ace.mail.MailSenderInfo;

public class Mail extends MailSenderInfo{
	public Mail() {
		super();
		//读取邮件基本配置属性
		String filepath="/";
		String filename="config.properties";
		Properties mailProperties = PropertiesUtil.loadPropertyInstance(filepath, filename);
		setMailServerHost(mailProperties.getProperty("mail.serverHost"));
		setMailServerPort(mailProperties.getProperty("mail.serverPort"));
		setUserName(mailProperties.getProperty("mail.username"));
		setPassword(mailProperties.getProperty("mail.password"));
		setFromAddress(mailProperties.getProperty("mail.fromAdress"));
		setValidate(Boolean.parseBoolean(mailProperties.getProperty("mail.vilidata")));
	}
	
	
}
