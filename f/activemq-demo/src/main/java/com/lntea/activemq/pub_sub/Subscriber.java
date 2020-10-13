package com.lntea.activemq.pub_sub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Subscriber {
	//brokeurl
	private String brokeURL = "tcp://localhost:61616";
	//connection factory
	private ConnectionFactory connectionFactory;
	//connection
	private Connection connection = null;
	//session
	private Session session;
	
	public Subscriber() {
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				brokeURL);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws JMSException {
		Subscriber subscriber = new Subscriber();
		
		String[] stocks = {"��ʯ��","��ʯ��","�й��ϳ�"};
		
		for(int i=0;i<stocks.length;i++){
			Destination destination = subscriber.getSession().createTopic("STOCKS."+stocks[i]);
			MessageConsumer consumer = subscriber.getSession().createConsumer(destination);
			consumer.setMessageListener(new Listener());
		}
	}

	public Session getSession() {
		return session;
	}
}

class Listener implements MessageListener{

	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;
		try {
			String stock = mapMessage.getString("stock");
			double price = mapMessage.getDouble("price");
			boolean up = mapMessage.getBoolean("up");
			System.out.println("stock:" + stock + ",price:"+price+",up:"+up);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
