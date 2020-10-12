package com.lmy.demo.rabbitmq.demo;
 
import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;  
import com.rabbitmq.client.AMQP.BasicProperties.Builder; 
import com.rabbitmq.client.AMQP.Confirm.SelectOk;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
 
 
public class MqRpcClient{
	 private final static String REQUEST_QUEUE_NAME="rpc_request";
	 private final static String RESPONSE_QUEUE_NAME="rpc_resp";
	 private Channel channel;
	 private QueueingConsumer qConsumer;
	 
	 //构造函数 初始化连接
	 public MqRpcClient() throws IOException, InterruptedException, TimeoutException {
		 //创建连接工厂
		 ConnectionFactory factory = new ConnectionFactory();
		 //设置主机、用户名、密码和客户端端口号
		 factory.setHost("localhost");
		 factory.setUsername("root");
		 factory.setPassword("root");
		 factory.setPort(5672);
		 //创建一个新的连接 即TCP连接
		 Connection connection = factory.newConnection();
		 //创建一个通道
		 channel = connection.createChannel();
		 //创建一个请求队列
		 channel.queueDeclare(REQUEST_QUEUE_NAME, true, false, false, null);
		 //创建一个回调队列
		 channel.queueDeclare(RESPONSE_QUEUE_NAME,true,false,false,null);
		 //为通道创建一个监听（用于监听回调队列，获取返回消息）
		 qConsumer = new QueueingConsumer(channel);
		 //关联监听与监听队列 并手动应答
		 channel.basicConsume(RESPONSE_QUEUE_NAME,false,qConsumer);
	}
	 public String getSquare(String message) throws  Exception{
		 String response = "";
		 //定义消息属性中的correlationId
		 String correlationId = java.util.UUID.randomUUID().toString();
		 //设置消息属性的replTo和correlationId
		 BasicProperties properties = new BasicProperties.Builder().correlationId(correlationId).replyTo(RESPONSE_QUEUE_NAME).build();
		 //发送消息到请求队列rpc_request队列 ，前边说到过 如果没有exchange即没有routingKey 消息发送到与routingKey参数相同的队列中
		 channel.basicPublish("",REQUEST_QUEUE_NAME, properties,message.getBytes());
		 //阻塞监听
		 while(true){
			 QueueingConsumer.Delivery delivery = qConsumer.nextDelivery();
			 if(delivery.getProperties().getCorrelationId().equals(correlationId)){
				 response = new String(delivery.getBody(),"UTF-8");
		    	 //手动回应消息应答
		    	 channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
				 break;
			 }
		 }
		 return response;
	 }
	 public static void main(String[] args) throws Exception {
		MqRpcClient rpcClient = new MqRpcClient();
		String result = rpcClient.getSquare("4");
		System.out.println("resonse is :" + result);
	}
}