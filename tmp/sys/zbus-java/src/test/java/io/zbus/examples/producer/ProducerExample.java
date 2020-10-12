package io.zbus.examples.producer;

import io.zbus.mq.Broker;
import io.zbus.mq.Message;
import io.zbus.mq.Producer; 

public class ProducerExample { 
	
	public static void main(String[] args) throws Exception { 
		Broker broker = new Broker("localhost:15555"); 
		//Broker broker = new Broker("localhost:15555;localhost:15556"); //Why not test HA?
		  
		Producer p = new Producer(broker);  //Producer is lightweight Java object, no need to destory
		p.declareTopic("MyTopic"); //If topic not exists, declare it 
		 
		Message msg = new Message();
		msg.setTopic("MyTopic");     // [R] required, a message needs Topic 
		
		msg.setBody("hello " + System.currentTimeMillis());  //a binary blob, application may define it's own format
		
		Message res = p.publish(msg);//Synchroneous, for async:  p.publishAsync(msg, callback);
		
		System.out.println(res);    
		
		
		broker.close(); //Broker is a heavey Java Object, it should be shared, and has to be destroyed
	}
}
