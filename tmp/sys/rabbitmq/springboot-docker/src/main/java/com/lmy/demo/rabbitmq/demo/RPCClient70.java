package com.lmy.demo.rabbitmq.demo;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;

public class RPCClient70 {
	private static final String REQUEST_QUEUE_NAME="rpc_queue";
	private static final String IP_ADDRESS="127.0.0.1";
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
		QueueingConsumer consumer=new QueueingConsumer(channel);
//		Consumer consumer=new DefaultConsumer(channel){
//			@Override
//			public void handleDelivery(String consumerTag, Envelope envelope,
//					BasicProperties properties, byte[] body) throws IOException {
//				String response="";
//				System.out.println(" RPC client 70: handleDelivery start ");
//				if(properties.getCorrelationId()!=null&&properties.getCorrelationId().equals(corrId)){
//					response=new String(body);
//				}else{
//					response=new String(body)+" response ";
//				}
//				System.out.println(response);
//			}
//		};
		channel.basicConsume(REQUEST_QUEUE_NAME, consumer);
		
		String replyQueueName = channel.queueDeclare().getQueue();
		String response=null;
		String corrId=UUID.randomUUID().toString();
		BasicProperties props=new BasicProperties.Builder().correlationId(corrId)
				.replyTo(replyQueueName).build();
		channel.basicPublish("", "rpc_queue", props, " RPC client 70 ".getBytes());
		while(true){
			QueueingConsumer.Delivery delivery=consumer.nextDelivery();
			if(delivery.getProperties().getCorrelationId().equals(corrId)){
				response=new String(delivery.getBody());
				break;
			}
		}
		System.out.println("response: "+response);
	}
}
