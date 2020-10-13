package com.github.xieyuqian.jms.simple03;

public class MlaListener {

	public void handleMessage(String message) {
		System.out.println("[*]-消费者-MessageListenerAdapter-接收到的消息1：" + message);
	}
	
	public String receiveMessage(String message) {
		System.out.println("[*]-消费者-MessageListenerAdapter-接收到的消息2：" + message);
		return "send3 OK...";
	}
}
