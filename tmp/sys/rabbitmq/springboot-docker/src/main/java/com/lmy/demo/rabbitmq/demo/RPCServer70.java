package com.lmy.demo.rabbitmq.demo;

import java.io.IOException;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.AMQP.BasicProperties;

public class RPCServer70 {
	private static final String RPC_QUEUE_NAME="rpc_queue";
	private static final String RPC_EXCHANGE_NAME="rpc_exchange";
	private static final String RPC_ROUTING_KEY="rpc_rootingkey";
	private static final String IP_ADDRESS="127.0.0.1";
	private static final int PORT=5672; //rabbitMQ 服务端默认端口号为5672

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("root");
		factory.setPassword("root");
		Connection connection=factory.newConnection();
		Channel channel = connection.createChannel();
		// String exchange, String type, boolean durable, boolean autoDelete,
		channel.exchangeDeclare(RPC_EXCHANGE_NAME, "direct",true,false,null);
		// String queue, boolean durable, boolean exclusive, boolean autoDelete,
		channel.queueDeclare(RPC_QUEUE_NAME,true,false,false,null);
//		channel.queueBind(RPC_QUEUE_NAME, RPC_EXCHANGE_NAME, RPC_ROUTING_KEY);
		channel.basicQos(1);
		System.out.println(" [x] Awaiting RPC requests");
		Consumer consumer=new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				AMQP.BasicProperties replyProps=new AMQP.BasicProperties
						.Builder().correlationId(properties.getCorrelationId())
						.build();
				String response="dfsadfsdf";
				try {
					String message=new String(body,"UTF-8");
					System.out.println(" [.] fib("+message+")");
				} catch (Exception e) {
					System.out.println(" [.] "+e.toString());
				}finally{
					channel.basicPublish("", properties.getReplyTo(),
							replyProps, response.getBytes("UTF-8"));
					channel.basicAck(envelope.getDeliveryTag(),
							false);
				}
			}
		};
//		channel.basicPublish(RPC_EXCHANGE_NAME, RPC_ROUTING_KEY, 
//				MessageProperties.PERSISTENT_TEXT_PLAIN,
//				"hello RPC server".getBytes());
		channel.basicConsume(RPC_QUEUE_NAME, false,consumer);
	}
}
