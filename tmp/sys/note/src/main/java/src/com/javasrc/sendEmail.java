package com.javasrc;

import myEmail.MailSenderInfo;
import myEmail.SimpleMailSender;

public class sendEmail {
	public static void send(String email, String password) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("weiwo_ww@163.com");
		mailInfo.setPassword("weiwo_weiwo");// 您的邮箱密码
		mailInfo.setFromAddress("weiwo_ww@163.com");
		// mailInfo.setToAddress("bjzhny_lyb@foxmail.com");
		mailInfo.setToAddress(email);
		mailInfo.setSubject("密码修改 微我");
		mailInfo.setContent("微我\n您的新密码为：" + password + "\n请使用系统自动生成的密码登录网站后修改您的密码\n此邮件为系统自动发送，回复无效！");
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
		// sms.sendHtmlMail(mailInfo);// 发送html格式
	}
}
