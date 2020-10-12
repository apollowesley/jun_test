package com.lmy.demo.rabbitmq.demo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class RabbitConsumer {
	private static final String QUEUE_NAME="queue_demo";
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
		channel.basicQos(64);//设置客户端最多接收未被ack的消息的个数
		Consumer consumer=new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				System.out.println("recv message:"+new String(body));
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				channel.basicAck(envelope.getDeliveryTag(),false);
			}
		};
		channel.basicConsume(QUEUE_NAME,consumer);
		channel.basicConsume(QUEUE_NAME, false, "myConsumerTag",new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				String routingKey=envelope.getRoutingKey();
				String contentType=properties.getContentType();
				
			}
		});
		//等待回调函数执行完毕之后，关闭资源
		TimeUnit.SECONDS.sleep(5);
		channel.close();
		connection.close();
	}
}