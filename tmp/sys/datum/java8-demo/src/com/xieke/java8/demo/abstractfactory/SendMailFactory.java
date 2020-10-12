package com.xieke.java8.demo.abstractfactory;

/**
 * 发送邮件工厂实现类
 * 
 * @author 邪客
 *
 */
public class SendMailFactory implements SendFactory {

	@Override
	public Sender build() {
		return new MailSender();
	}

}
