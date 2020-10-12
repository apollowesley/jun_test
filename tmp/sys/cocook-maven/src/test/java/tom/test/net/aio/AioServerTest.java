package tom.test.net.aio;

import java.io.IOException;

import tom.net.Session;
import tom.net.aio.AioServer;
import tom.net.http.HttpMessage;
import tom.net.http.MessageAdaptorServer;
import tom.net.http.MessageHandler;
import tom.test.net.msg.StringAdaptor;
/**
 * [使用 阿里云 4核8G 测试 
 * ab -k -c 200 -n 50000  http://10.30.165.96:8080/test
 * Requests per second:    66540.95 [#/sec] (mean)]
 * 新测试有机器了加上
 * 
 * 
 * @author tomsun
 *
 */
public class AioServerTest {
	public static void main(String[] args) throws Exception {
		AioServer server = new AioServer(8080);
		MessageAdaptorServer adaptorServer= new MessageAdaptorServer();
		adaptorServer.setReadWritebufferSize(10*1024);
		adaptorServer.registerHandler("test", new MessageHandler() {
			@Override
			public void handleMessage(HttpMessage msg, Session sess) throws IOException {
				msg.setStatus("200");
				msg.setBody("hello world");
				sess.write(msg);
			}
		});
		server.setIoAdaptor(adaptorServer);
		
		server.start();
		
		Thread.sleep(7000);
		
//		server.setIoAdaptor(new StringAdaptor(null));
//		for(Session se :adaptorServer.getSessions().values()){
//			HttpMessage msg = new HttpMessage();
//			msg.setCommand("exec");
//			msg.setBody("开启命令执行");
//			se.write(msg);
//		} 
		server.sync();
		
	}
	
}