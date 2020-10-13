package com.github.xieyuqian.jms.simple;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

public class NotifyMessageProducer {
	
	private JmsTemplate jmsTemplate;
	private Destination notifyQueue;
	private Destination responseQueue;
	
	private void sendMessage(String phoneNum, String smsContent,Destination destination, final Destination destination2) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phoneNum", phoneNum);
		map.put("smsContent", smsContent);
		jmsTemplate.convertAndSend(destination, map);
	}
	
	public void sendQueue(String phoneNum, String smsContent) {
		sendMessage(phoneNum, smsContent, notifyQueue, responseQueue);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setNotifyQueue(Destination notifyQueue) {
		this.notifyQueue = notifyQueue;
	}

	public void setResponseQueue(Destination responseQueue) {
		this.responseQueue = responseQueue;
	}
	
}
