package oc.tm.nio.server;

import oc.tm.nio.core.SelectorGroup;
import oc.tm.nio.http.Message;
import oc.tm.nio.http.MessageClient;

public class MyClient {

	public static void main(String[] args) throws Exception {
		final SelectorGroup selectorGroup = new SelectorGroup();

		final MessageClient client = new MessageClient("127.0.0.1:8080", selectorGroup);

		Message msg = new Message();
		msg.setCmd("/hello"); 

		msg.setBody(new byte[10]);
		msg = client.invokeSync(msg, 100000);
		System.out.println(msg);
		
		client.close();
		
		selectorGroup.close();
	}
}