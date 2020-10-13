package com.github.xieyuqian.jms.simple02;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

public class SamlProducer {

	private JmsTemplate jmsTemplate;
	private Destination samlQueue;
	
	public void sendMessage(String message) {
		jmsTemplate.convertAndSend(samlQueue, message);
	}
	
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	public void setSamlQueue(Destination samlQueue) {
		this.samlQueue = samlQueue;
	}
	
	
}
