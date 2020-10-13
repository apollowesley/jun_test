package com.lntea.activemq.pub_sub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMapMessage;

public class Publisher {
	//connection factory
	private ConnectionFactory connectionFactory;
	//connection
	private Connection connection = null;
	//session
	private Session session;
	//producer
	private MessageProducer producer;
	//destination array
	private Destination[] destinations;
	
	public Publisher() throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://localhost:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
		} catch (JMSException e) {
			if(connection!=null){
				connection.close();
			}
			throw e;
		}
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		producer = session.createProducer(null);
	}
	
	/**
	 * ����topic
	 * @param stocks
	 * @throws JMSException
	 */
	protected void setTopics(String[] stocks) throws JMSException{
		destinations = new Destination[stocks.length];
		for(int i=0;i<stocks.length;i++){
			destinations[i] = session.createTopic("STOCKS."+stocks[i]);
		}
	}
	
	/**
	 * ����topic��Ϣ
	 * @param stocks
	 * @throws JMSException 
	 */
	protected void sendMessage(String[] stocks) throws JMSException {
		for(int i=0;i<stocks.length;i++){
			Message message = createStockMessage(stocks[i],session);
			System.out.println("Sending: "+((ActiveMQMapMessage)message).getContentMap()
					+ " on destination " + destinations[i]);
			producer.send(destinations[i],message);
		}
	}
	
	private Message createStockMessage(String stock, Session session) throws JMSException {
		MapMessage mapMessage = session.createMapMessage();
		mapMessage.setString("stock",stock);
		mapMessage.setDouble("price", 1.00);
		mapMessage.setBoolean("up", true);
		return mapMessage;
	}

	public static void main(String[] args) throws JMSException {
		Publisher publisher = new Publisher();
		
		String[] stocks = {"��ʯ��","��ʯ��","�й��ϳ�"};
		publisher.setTopics(stocks);
		
		for(int i=0;i<10;i++){
			publisher.sendMessage(stocks);
			System.out.println("Publish " + i +" price message");
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		publisher.close();
	}

	private void close() throws JMSException {
		if(connection!=null){
			connection.close();
		}
	}
}
