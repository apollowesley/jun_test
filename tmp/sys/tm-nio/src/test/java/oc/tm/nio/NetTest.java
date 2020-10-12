package oc.tm.nio;

import java.io.IOException;

import oc.tm.nio.core.SelectorGroup;
import oc.tm.nio.http.Message;
import oc.tm.nio.http.MessageClient;

public class NetTest {

	public static void main(String[] args) throws Exception { 
		SelectorGroup group = new SelectorGroup();
		
		MessageClient client = new MessageClient("127.0.0.1:15555", group);
		Message req = new Message();
		req.setBody("test");
		try{
			client.sendAsync(req);
		}catch(IOException e){
			System.err.println(e);
		}
		//client.connectAsync(); 
		Thread.sleep(100);
		client.close();
		
		group.close(); 
	}

}
