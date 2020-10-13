package com.lntea.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
	private static final int SEND_NUMBER = 5;
	
	public static void main(String[] args) {
		//���ӹ�������������JMS
		ConnectionFactory connectionFactory;
		//JMS�ͻ��˵�JMS Provider������
		Connection connection = null;
		//Session ���ͻ������Ϣ���߳�
		Session session;
		//��ϢĿ�ĵ�
		Destination destination;
		//��Ϣ������
		MessageProducer producer;
		//ʹ��activemq����connectionFactory
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, 
				"tcp://localhost:61616");
		try {
			//��������
			connection = connectionFactory.createConnection();
			//��ò�������
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//��ȡ��ϢĿ�ĵ�
			destination = session.createQueue("FirstQueue");
			//�õ���Ϣ�����
			producer = session.createProducer(destination);
			//���ò��־û�
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			//������Ϣ
			sendMessage(session,producer);
			//�ύ
			session.commit();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(null != connection){
					connection.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	private static void sendMessage(Session session, MessageProducer producer) throws JMSException {
		for(int i=0;i<SEND_NUMBER;i++){
			TextMessage message = session.createTextMessage("ActiveMQ ������Ϣ"+i);
			System.out.println("������Ϣ��"+message.getText());
			producer.send(message);
		}
	}
}
