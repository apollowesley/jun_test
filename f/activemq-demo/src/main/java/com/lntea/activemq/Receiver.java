package com.lntea.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {
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
		MessageConsumer consumer;
		//ʹ��activemq����connectionFactory
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, 
				"tcp://localhost:61616");
		try {
			//��������
			connection = connectionFactory.createConnection();
			//����
			connection.start();
			//��ò�������
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			//��ȡ��ϢĿ�ĵ�
			destination = session.createQueue("FirstQueue");
			//�õ���Ϣ�����
			consumer = session.createConsumer(destination);
			
			while(true){
				//���ý����߽�����Ϣ��ʱ��
				TextMessage message = (TextMessage) consumer.receive(100000L);
				if(null!=message){
					System.out.println("�յ���Ϣ��"+message.getText());
				}else{
					break;
				}
			}
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
}
