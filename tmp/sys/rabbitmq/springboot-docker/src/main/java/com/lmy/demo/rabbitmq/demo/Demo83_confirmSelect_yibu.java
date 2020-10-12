package com.lmy.demo.rabbitmq.demo;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Demo83_confirmSelect_yibu {
	private static final String EXCHANGE_NAME="demo83_exchange";
	private static final String QUEUE_NAME="demo83_queue";
	private static final String ROUTING_KEY="demo83_routeKey";
	private static final String IP_ADDRESS="127.0.0.1";
	private static final String MESSAGE="hello demo83";
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
		SortedSet<Long> confirmSet=new TreeSet<Long>();
		channel.confirmSelect();
		channel.addConfirmListener(new ConfirmListener() {
			@Override
			public void handleNack(long deliveryTag, boolean multiple)
					throws IOException {
				if(multiple){
					confirmSet.headSet(deliveryTag-1).clear();
				}else{
					confirmSet.remove(deliveryTag);
				}
			}
			@Override
			public void handleAck(long deliveryTag, boolean multiple)
					throws IOException {
				if(multiple){
					confirmSet.headSet(deliveryTag-1).clear();
				}else{
					confirmSet.remove(deliveryTag);
				}
				//注意这里需要添加处理消息重发的场景
			}
		});
		// 下面是演示一直发送消息的场景
		while(true){
			long nextSeqNo=channel.getNextPublishSeqNo();
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,
					" msg ".getBytes());
			confirmSet.add(nextSeqNo);
		}
	}
}
