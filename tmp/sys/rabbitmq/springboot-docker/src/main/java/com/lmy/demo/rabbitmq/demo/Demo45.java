package com.lmy.demo.rabbitmq.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Demo45 {
	public static void main(String[] args) throws IOException, TimeoutException {
		Map<String,Object> headers=new HashMap<String,Object>();
		headers.put("location", "here");
		headers.put("time", "today");
		ConnectionFactory factory=new ConnectionFactory();
		Connection newConnection = factory.newConnection();
		Channel channel = newConnection.createChannel();
		channel.exchangeDeclare("exchange_demo", "difect");
		byte[] bytes=new byte[]{};
		channel.basicPublish("exchange_demo", "routingKey", new AMQP.BasicProperties.Builder()
		.expiration("60000").build(), bytes);
		
	}
}
