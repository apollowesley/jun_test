package com.xieke.java8.demo.abstractfactory;

/**
 * 短信发送实现类
 * 
 * @author 邪客
 *
 */
public class SmsSender implements Sender {

	@Override
	public void Send() {
		System.out.println("this is sms sender!");
	}

}
