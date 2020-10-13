package com.github.xieyuqian.jms.simple03;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 注释的部分说明：
 * 	当没有在MessageListenerAdapter类型的Bean中注入Queue时则可以使用注释部分来设置用于回复消息的Queue
 * @author asus
 *
 */
public class MlaProducer {

	private JmsTemplate jmsTemplate;
	private Destination mlaQueue;
//	private Destination mlaResponseQueue;
	
	public void sendMessage(final String message) {
		jmsTemplate.convertAndSend(mlaQueue, message);
//		jmsTemplate.send(mlaQueue, new MessageCreator() {
//
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				TextMessage tm = session.createTextMessage(message);
//				tm.setJMSReplyTo(mlaResponseQueue);
//				return tm;
//			}
//			
//		});
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setMlaQueue(Destination mlaQueue) {
		this.mlaQueue = mlaQueue;
	}

//	public void setMlaResponseQueue(Destination mlaResponseQueue) {
//		this.mlaResponseQueue = mlaResponseQueue;
//	}
	
	
	
	
}
