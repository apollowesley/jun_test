package com.xieke.java8.demo.abstractfactory;

/**
 * 抽象工厂模式测试
 * 
 * @author 邪客
 *
 */
public class FactoryTest {

	public static void main(String[] args) {
		SendFactory sendFactory = new SendMailFactory();
		Sender sender = sendFactory.build();
		sender.Send();
	}

}
