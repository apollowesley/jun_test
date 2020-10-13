package com.lntea.activemq.p2p;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
	//brokeurl
	private String brokeurl = "tcp://localhost:61616";
	//connection factory
	private ConnectionFactory connectionFactory;
	//connection
	private Connection connection = null;
	//session
	private Session session;
	//producer
	private MessageProducer producer;
	//jobs
	private String[] jobs = {"learningEnglish","cleaningRoom","workingForBoss"};
	
	public Producer() {
		connectionFactory = new ActiveMQConnectionFactory(brokeurl);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(null);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * send message
	 * @param jobs
	 * @throws JMSException 
	 */
	protected void sendMessage() throws JMSException{
		for(int i=0;i<jobs.length;i++){
			String job = jobs[i];
			Destination destination = session.createQueue("JOBS."+job);
			Message message = session.createObjectMessage(i);
			System.out.println("Sending id: "+((ObjectMessage)message).getObject()
					+" on queue: "+destination);
			producer.send(destination, message);
		}
	}
	
	public static void main(String[] args) throws JMSException {
		Producer producer = new Producer();
		
		for(int i=0;i<10;i++){
			producer.sendMessage();
			System.out.println("Producer :" + i + " job");
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		producer.close();
	}
	
	protected void close() throws JMSException{
		if(connection!=null){
			connection.close();
		}
	}
}
