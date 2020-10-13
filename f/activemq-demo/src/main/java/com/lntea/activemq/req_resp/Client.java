package com.lntea.activemq.req_resp;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Client implements MessageListener{
	public Client() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("client.messages");
			
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			//create a temporary queue
			Destination tempDest = session.createTemporaryQueue();
			MessageConsumer responseConsumer = session.createConsumer(tempDest);
			
			responseConsumer.setMessageListener(this);
			
			//send text message
			TextMessage textMessage = session.createTextMessage("request message");
			textMessage.setJMSReplyTo(tempDest);
			textMessage.setJMSCorrelationID(this.createRandomString());
			
			producer.send(textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private String createRandomString() {
		Random random = new Random(System.currentTimeMillis());
		long randomLong = random.nextLong();
		return Long.toHexString(randomLong);
	}

	public void onMessage(Message message) {
		try {
			if(message instanceof TextMessage){
				String responseString = ((TextMessage) message).getText();
				System.out.println("response: "+ responseString);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new Client();
	}
}
