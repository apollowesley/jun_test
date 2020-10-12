package com.lmy.demo.rabbitmq.demo;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Demo61_TimeToLive {
	private static final String EXCHANGE_NAME="exchange_demo";
	private static final String ROUTING_KEY="routingkey_demo";
	private static final String QUEUE_NAME="queue_demo";
	private static final String IP_ADDRESS="127.0.0.1";
	private static final int PORT=5672; //rabbitMQ 服务端默认端口号为5672
	public static void main(String[] aa) throws Exception {
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("root");
		factory.setPassword("root");
		Connection connection=factory.newConnection();//创建连接
		Channel channel=connection.createChannel();//创建信道
		
		Map<String,Object> args=new HashMap<String,Object>();
		args.put("x-message-ttl",6000);
//		channel.exchangeDeclare("myAe", "fanout",true,false,null);
		channel.exchangeDeclare("ttlExchange", "fanout",true,false,null);
		channel.queueDeclare("ttlQueue",true,false,false,args);
		channel.queueBind("ttlQueue", "ttlExchange", "ttl_route_key");
		channel.basicPublish("ttlExchange", "ttl_route_key",
				MessageProperties.PERSISTENT_TEXT_PLAIN, 
				"hello ttl".getBytes());
//		channel.queueBind("normalQueue", "normalExchange", "normalKey");
//		channel.queueDeclare("unroutedQueue",true,false,false,null);
//		channel.queueBind("unroutedQueue", "myAe", "");
//		channel.basicPublish("normalQueue", "normalKey", MessageProperties.PERSISTENT_TEXT_PLAIN,
//				"hello nomarlqueue".getBytes());
		
		
		
//		channel.basicPublish("normalQueue", "fadsf", MessageProperties.PERSISTENT_TEXT_PLAIN,
//				"hello nomarlqueue".getBytes());
		
	}
}
