package com.jason.utils.email;

import com.jason.utils.email.event.SimpleMailSendEventListener;

public class SimpleMailSenderTest {
	public static void main(String[] args) {
		Account account=new Account("账户","密码");
		MailSender simpleMailSender=new MailSender(account);
		simpleMailSender.setSendEventListener(new SimpleMailSendEventListener());
		simpleMailSender.send("380146330@qq.com",null,null);
	}
	
}