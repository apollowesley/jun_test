package com.lntea.activemq.p2p;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {
	//brokeurl
	private String brokeurl = "tcp://localhost:61616";
	//connection factory
	private ConnectionFactory connectionFactory;
	//connection
	private Connection connection = null;
	//session
	private Session session;
	//jobs
	private static String[] jobs = {"learningEnglish","cleaningRoom","workingForBoss"};
	
	public Consumer() {
		connectionFactory = new ActiveMQConnectionFactory(brokeurl);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws JMSException {
		Consumer consumer = new Consumer();
		
		for(String job : jobs){
			Destination destination = consumer.getSession().createQueue("JOBS."+job);
			MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);
			messageConsumer.setMessageListener(new Listener(job));
		}
	}

	public Session getSession() {
		return session;
	}
}

class Listener implements MessageListener{
	private String job;
	
	public Listener(String job) {
		this.job = job;
	}

	public void onMessage(Message message) {
		try {
			System.out.println(job+ " id:"+ ((ObjectMessage)message).getObject());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}
