package com.github.xieyuqian.jms.simple;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class NotifyMessageListener implements MessageListener {

	/**
	 * MessageListener回调函数
	 */
	@Override
	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;
		try {
			System.out.println("[*]-消费者-接收到消息：PhoneNum: " + mapMessage.getString("phoneNum")
					+ ", SMS: " + mapMessage.getString("smsContent"));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}
