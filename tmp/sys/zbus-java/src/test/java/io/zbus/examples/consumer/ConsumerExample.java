package io.zbus.examples.consumer;

import java.io.IOException;

import io.zbus.mq.Broker;
import io.zbus.mq.MessageHandler;
import io.zbus.mq.Consumer;
import io.zbus.mq.ConsumerConfig;
import io.zbus.mq.Message;
import io.zbus.mq.MqClient;

//multiple instances load balancing on the same topic
//you may start many instances to check
public class ConsumerExample {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {   
		Broker broker = new Broker("localhost:15555");   
		//Broker broker = new Broker("localhost:15555;localhost:15556"); //Why not test HA?
		
		ConsumerConfig config = new ConsumerConfig(broker);
		config.setTopic("MyTopic");              // [R] Topic to consume
		//config.setConsumeGroup(consumerGroup); // [O] ConsumeGroup, by default null, consumes the group name = topic
		config.setMessageHandler(new MessageHandler() { //Message handler, biz logic goes here
			@Override
			public void handle(Message msg, MqClient client) throws IOException {
				// MqClient is the physical connection to MqServer, may be connected to different MqServer
				// if multiple MqServer avaialbe, with MqClient, the consumer handler may reply back, such as RPC case
				
				System.out.println(msg);
			}
		});
		
		Consumer consumer = new Consumer(config);
		consumer.start(); 
	} 
}
