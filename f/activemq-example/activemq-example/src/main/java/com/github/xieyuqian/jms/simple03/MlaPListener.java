package com.github.xieyuqian.jms.simple03;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MlaPListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)message;
			try {
				System.out.println("[*]-生产者-MessageListenerAdapter-接收到消费者回复的消息：" + textMessage.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
	}

}
