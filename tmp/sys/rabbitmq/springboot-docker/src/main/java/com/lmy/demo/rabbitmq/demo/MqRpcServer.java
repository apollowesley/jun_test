package com.lmy.demo.rabbitmq.demo;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Map;
 
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
public class MqRpcServer {
	 private final static String REQUEST_QUEUE_NAME="rpc_request";
	 
	 public static void main(String[] args) throws Exception{
		 //创建连接工厂
		 ConnectionFactory factory = new ConnectionFactory();
		 //设置主机
		 factory.setHost("localhost");
		 //创建一个新的连接 即TCP连接
		 Connection connection = factory.newConnection();
		 //创建一个通道
		 final Channel channel = connection.createChannel();
		 //声明队列
		 channel.queueDeclare(REQUEST_QUEUE_NAME, true, false, false, null);
		 //设置prefetch值 一次处理1条数据
		 channel.basicQos(1);
		 //为请求队列设置监听 监听客户端请求 并手动应答
		 QueueingConsumer qConsumer = new QueueingConsumer(channel);
	     channel.basicConsume(REQUEST_QUEUE_NAME,false, qConsumer);
	     System.out.println("Server waiting Requeust.");
	     while(true){
	    	 QueueingConsumer.Delivery delivery = qConsumer.nextDelivery();
	    	 //将请求中的correlationId设置到回调的消息中
	    	 BasicProperties properties = delivery.getProperties();
	    	 BasicProperties replyProperties = new BasicProperties.Builder().correlationId(properties.getCorrelationId()).build();
	    	 //获取客户端指定的回调队列名
	    	 String replyQueue = properties.getReplyTo();
	    	 //返回获取消息的平方
	    	 String message = new String(delivery.getBody(),"UTF-8");
	    	 System.out.println("waiting message is:" + message);
	    	 Double mSquare =  Math.pow(Integer.parseInt(message),2);
	    	 String repMsg = String.valueOf(mSquare);
	    	 channel.basicPublish("",replyQueue,replyProperties,repMsg.getBytes());
	    	 //手动回应消息应答
	    	 channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
	     }
	}
}