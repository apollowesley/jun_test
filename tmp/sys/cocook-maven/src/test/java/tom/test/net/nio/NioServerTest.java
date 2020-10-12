package tom.test.net.nio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import tom.net.Session;
import tom.net.http.HttpMessage;
import tom.net.http.MessageAdaptorServer;
import tom.net.http.MessageHandler;
import tom.net.nio.NioServer;
import tom.test.net.msg.StringAdaptor;
/**
 * [使用 阿里云 4核8G 测试 
 * ab -k -c 200 -n 50000  http://10.30.165.96:8080/test
 * Requests per second:    58597.55  [#/sec] (mean)]
 * NIO测试 性能比 AIO 略低, 基本也在6W 左右
 * 新测试有机器了加上
 * @author tomsun
 *
 */
public class NioServerTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		NioServer server = new NioServer(8080);
		MessageAdaptorServer adaptorServer= new MessageAdaptorServer();
		adaptorServer.setReadWritebufferSize(10*1024);
		adaptorServer.registerHandler("test", new MessageHandler() {
			@Override
			public void handleMessage(HttpMessage msg, Session sess) throws IOException {
				
//					System.out.println(msg.toString());
					msg.setStatus("200");  
					msg.setBody("hello");
					sess.write(msg);
			}
		});
		server.setIoAdaptor(adaptorServer);
		
		server.start();
		
		
//		server.setIoAdaptor(new StringAdaptor(null));
		
		
//		Thread.sleep(5000);
//		
//		Session session = new ArrayList<>(adaptorServer.getSessions().values()).get(0);
//		
//			HttpMessage msg = new HttpMessage();
//			msg.setCommand("exec");
//			msg.setBody("开启命令执行");
//			session.write(msg);
//		
//		server.close();
	}

}
