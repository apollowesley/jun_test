package oc.tm.nio.perf;

import oc.tm.nio.Server;
import oc.tm.nio.core.IoAdaptor;
import oc.tm.nio.core.SelectorGroup;
import oc.tm.nio.http.Message;
import oc.tm.nio.http.Message.MessageProcessor;
import oc.tm.nio.http.MessageAdaptor;


public class NetServer extends MessageAdaptor{ 
	public NetServer(){  
		uri("/hello", new MessageProcessor() { 
			@Override
			public Message process(Message request) {
				Message res = new Message();
				res.setResponseStatus(200);
				res.setBody("hello");
				return res;
			}
		});  
	} 

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {  
		final SelectorGroup dispatcher = new SelectorGroup(); 
		dispatcher.selectorCount(1); 
		final Server server = new Server(dispatcher);
		 
		IoAdaptor ioAdaptor = new NetServer();  
    	server.start(15555, ioAdaptor); 
	}
}
