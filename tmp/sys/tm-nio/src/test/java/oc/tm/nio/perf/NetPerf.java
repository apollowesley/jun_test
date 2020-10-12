package oc.tm.nio.perf;

import java.io.IOException;

import oc.tm.nio.core.SelectorGroup;
import oc.tm.nio.http.Message;
import oc.tm.nio.http.MessageClient;

public class NetPerf{
	
	public static void main(String[] args) throws Exception { 
		final SelectorGroup group = new SelectorGroup();
		group.selectorCount(0);
		
		Perf perf = new Perf() { 
			@Override
			public Task buildTask() { 
				final MessageClient client = new MessageClient("127.0.0.1:15555", group);
				Task task = new Task() { 
					@Override
					public void doTask() throws Exception {
						Message msg = new Message();
						msg.setCmd("/hello");
						msg.setBody("hello world");
					    client.invokeSync(msg, 10000); 
					}
					
					@Override
					public void close() throws IOException {
						client.close();
					}
				};
				return task;
			}
			
			@Override
			public void close() throws IOException { 
				group.close(); 
			}
		};
		perf.threadCount = 16;
		perf.loopCount = 1000000;
		perf.logInterval = 10000;
		
		perf.run();
		
		perf.close();
	}
}
