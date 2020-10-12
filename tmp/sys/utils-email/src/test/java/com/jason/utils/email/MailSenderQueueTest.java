package com.jason.utils.email;

import com.jason.utils.email.message.SimpleMessage;

public class MailSenderQueueTest {
	
	public static void main(String[] args) {
		Account account=new Account("邮箱","密码");
		MailSenderQueue senderQueue=new MailSenderQueue(account);
		
		SimpleMessage message=new SimpleMessage();
		message.setToAddress("");
		message.setSubject("");
		message.setText("");
		senderQueue.addMessage(message);
	}
}