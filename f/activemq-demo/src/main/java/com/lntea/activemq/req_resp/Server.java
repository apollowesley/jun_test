package com.lntea.activemq.req_resp;

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
import org.apache.activemq.broker.BrokerService;

public class Server implements MessageListener{
	private MessageProducer replyProducer;
	
	private Session session ;
	
	public Server() {
		/*try {
			BrokerService brokerService = new BrokerService();
			brokerService.setPersistent(false);
			brokerService.setUseJmx(false);
			brokerService.addConnector("tcp://localhost:61616");
			brokerService.start();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		this.setupMessageQueueConsumer();
	}

	private void setupMessageQueueConsumer() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			Connection connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination destination = session.createQueue("client.messages");
			
			this.replyProducer = session.createProducer(null);
			this.replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(this);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
	
	public void onMessage(Message message) {
		try {
			String receiveText = ((TextMessage)message).getText();
			System.out.println("receive : " + receiveText);
			
			TextMessage responseMessage = this.session.createTextMessage();
			responseMessage.setText("response message");
			responseMessage.setJMSCorrelationID(message.getJMSCorrelationID());
			
			this.replyProducer.send(message.getJMSReplyTo(),responseMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		new Server();
	}
}
