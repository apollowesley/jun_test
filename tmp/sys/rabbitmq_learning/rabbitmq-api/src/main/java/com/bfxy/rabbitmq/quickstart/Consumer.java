package com.bfxy.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
/**
 * 消费者
 * 
 * @author xuzhixiang
 * @date 2018年10月25日下午5:15:11
 */
public class Consumer {

	public static void main(String[] args) throws Exception {
		
		//1 创建一个ConnectionFactory, 并进行配置
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		//2 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();
		
		//3 通过connection创建一个Channel
		Channel channel = connection.createChannel();
				
		/**
		 * 4 声明（创建）一个队列 (用于存储消息)
		 * 第一个参数：队列名
		 * 第二个参数表示：是否持久化
		 * 第三个参数：是否独占，相当于加了锁，为了保证顺序消费
		 * 第四个参数：扩展参数
		 */
		String queueName = "mogu";
		channel.queueDeclare(queueName, true, false, false, null);
		
		//5 创建消费者，消费者需要建立在哪个连接上进行消费
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		
		//6 设置Channel   第二个参数：是否自动接收。  第三个参数：
		channel.basicConsume(queueName, true, queueingConsumer);
		
		while(true){
			//7 获取一条消息
			Delivery delivery = queueingConsumer.nextDelivery(); 
			// 将消息内容转换为String
			String msg = new String(delivery.getBody());			
			System.err.println("消费端: " + msg);
						
		}
		
	}
}
