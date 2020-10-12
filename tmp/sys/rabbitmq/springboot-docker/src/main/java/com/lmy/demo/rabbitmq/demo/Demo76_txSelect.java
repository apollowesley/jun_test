package com.lmy.demo.rabbitmq.demo;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Demo76_txSelect {
	private static final String EXCHANGE_NAME="demo76_exchange";
	private static final String QUEUE_NAME="demo76_queue";
	private static final String ROUTING_KEY="demo76_routeKey";
	private static final String IP_ADDRESS="127.0.0.1";
	private static final String MESSAGE="hello demo76";
	private static final int PORT=5672;
	public static void main(String[] args) throws Exception {
		Address[] addresses=new Address[]{
				new Address(IP_ADDRESS,PORT)
		};
		ConnectionFactory factory=new ConnectionFactory();
		factory.setUsername("root");
		factory.setPassword("root");
		//这里的连接方式与生产者的demo略有不同，注意辨别区别
		Connection connection=factory.newConnection(addresses);//创建连接
		final Channel channel=connection.createChannel();//创建信道
		channel.exchangeDeclare(EXCHANGE_NAME, "direct",true,false,false,null);
		channel.queueDeclare(QUEUE_NAME,true,false,false,null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		try {
			channel.txSelect();
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, 
					MESSAGE.getBytes());
			int result=1/0;
		} catch (Exception e) {
			e.printStackTrace();
			channel.txRollback();
		}
	}
}
