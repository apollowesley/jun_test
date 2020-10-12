package com.xieke.java8.demo.abstractfactory;

/**
 * 邮件发送实现类
 * 
 * @author 邪客
 *
 */
public class MailSender implements Sender {

	@Override
	public void Send() {
		System.out.println("this is mail sender!");
	}

}
