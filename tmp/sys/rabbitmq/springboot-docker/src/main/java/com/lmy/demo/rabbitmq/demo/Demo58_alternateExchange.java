package com.lmy.demo.rabbitmq.demo;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Demo58_alternateExchange {
	private static final String EXCHANGE_NAME="exchange_demo";
	private static final String ROUTING_KEY="routingkey_demo";
	private static final String QUEUE_NAME="queue_demo";
	private static final String IP_ADDRESS="127.0.0.1";
	private static final int PORT=5672; //rabbitMQ 服务端默认端口号为5672
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("root");
		factory.setPassword("root");
		Connection connection=factory.newConnection();//创建连接
		Channel channel=connection.createChannel();//创建信道
		
		Map<String,Object> argss=new HashMap<String,Object>();
		argss.put("alternate-exchange","myAe");
		channel.exchangeDeclare("normalExchange", "direct",true,false,argss);
		channel.exchangeDeclare("myAe", "fanout",true,false,null);
		channel.queueDeclare("normalQueue",true,false,false,null);
		channel.queueBind("normalQueue", "normalExchange", "normalKey");
		channel.queueDeclare("unroutedQueue",true,false,false,null);
		channel.queueBind("unroutedQueue", "myAe", "");
		channel.basicPublish("normalQueue", "normalKey", MessageProperties.PERSISTENT_TEXT_PLAIN,
				"hello nomarlqueue".getBytes());
		
		channel.basicPublish("normalQueue", "fadsf", MessageProperties.PERSISTENT_TEXT_PLAIN,
				"hello nomarlqueue".getBytes());
//		//创建一个type="direct"、持久化的、非自动删除的交换器
//		channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
//		//创建一个持久化、非排他的、非自动删除的队列
//		channel.queueDeclare(QUEUE_NAME, true, false,false,null);
//		//将交换器与队列通过路由键绑定
//		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
//		//发送一条持久化的消息；hello world
//		String message="Hello world";
////		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,
////				message.getBytes());
//		channel.basicPublish(EXCHANGE_NAME, "", false,true, MessageProperties.PERSISTENT_TEXT_PLAIN,
//				"immediate".getBytes());
//		channel.addReturnListener(new ReturnListener() {
//			@Override
//			public void handleReturn(int replyCode,
//		            String replyText,
//		            String exchange,
//		            String routingKey,
//		            AMQP.BasicProperties properties,
//		            byte[] body) throws IOException {
//				String message=new String(body);
//				System.out.println("Basic.Return 返回的结果是:"+message);
//			}
//		});
		
	}
}
