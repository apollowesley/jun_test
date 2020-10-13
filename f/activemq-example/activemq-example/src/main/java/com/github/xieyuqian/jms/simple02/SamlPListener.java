package com.github.xieyuqian.jms.simple02;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SamlPListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage tm = (TextMessage)message;
		try {
			System.out.println("[*]-生产者-SessionAwareMessageListener-收到的消费者回复的消息：" + tm.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
