package com.lmy.demo.rabbitmq.demo;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Demo64_TTL_DLX_RabbitProducer {
	private static final String EXCHANGE_NAME="exchange_demo";
	private static final String ROUTING_KEY="routingkey_demo";
	private static final String QUEUE_NAME="queue_demo";
	private static final String IP_ADDRESS="127.0.0.1";
	private static final int PORT=5672; //rabbitMQ 服务端默认端口号为5672
	public static void main(String[] aaa) throws Exception {
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("root");
		factory.setPassword("root");
		Connection connection=factory.newConnection();//创建连接
		Channel channel=connection.createChannel();//创建信道
		channel.exchangeDeclare("exchange.dlx", "direct",true);
		channel.exchangeDeclare("exchange.normal", "fanout",true);
		Map<String,Object> args=new HashMap<String,Object>();
		args.put("x-message-ttl", 10000);
		args.put("x-dead-letter-exchange", "exchange.dlx");
		args.put("x-dead-letter-routing-key", "routingkey");
		channel.queueDeclare("queue.normal",true,false,false,args);
		channel.queueBind("queue.normal", "exchange.normal", "");
		channel.queueDeclare("queue.dlx",true,false,false,null);
		channel.queueBind("queue.dlx", "exchange.dlx", "routingkey");
		channel.basicPublish("exchange.normal", "rk", 
				MessageProperties.PERSISTENT_TEXT_PLAIN,
				"dlx".getBytes());
	}
}
