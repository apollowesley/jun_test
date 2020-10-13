package com.github.xieyuqian.jms.simple02;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

public class SamlListener implements SessionAwareMessageListener<TextMessage> {

	private Destination samlResponseQueue;
	@Override
	public void onMessage(TextMessage tm, Session session) throws JMSException {
		System.out.println("[*]-消费者-SessionAwareMessageListener- 收到消息：" + tm.getText());
		MessageProducer producer = session.createProducer(samlResponseQueue);
		Message textMessage = session.createTextMessage("send2 OK...");
		producer.send(textMessage);
	}
	
	public void setSamlResponseQueue(Destination samlResponseQueue) {
		this.samlResponseQueue = samlResponseQueue;
	}
	
	
}
