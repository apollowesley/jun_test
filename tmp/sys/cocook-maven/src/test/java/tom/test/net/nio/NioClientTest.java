package tom.test.net.nio;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import tom.kit.io.FileUtil;
import tom.net.http.Callback.ResultCallback;
import tom.net.Session;
import tom.net.http.HttpMessage;
import tom.net.http.MessageAdaptorClient;
import tom.net.http.MessageHandler;
import tom.net.http.id.TicketManager.Id;
import tom.net.nio.NioClient;
import tom.test.net.msg.MessageAdaptorClientT;
import tom.test.net.msg.StringAdaptor;

public class NioClientTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		NioClient client = new NioClient("127.0.0.1:80");
		final AtomicLong counter = new AtomicLong(0);
		MessageAdaptorClient adaptorClient = new MessageAdaptorClient();
		adaptorClient.setReadWritebufferSize(10*1024);
//		adaptorClient.registerHandler("exec", new MessageHandler() {
//			
//			@Override
//			public void handleMessage(HttpMessage msg, Session sess) throws IOException {
//				System.out.println(msg);
//				msg.setStatus("200");
//				msg.setBody("exec 执行完成");
//				sess.write(msg);
//			}
//		});
		client.setIoAdaptor(adaptorClient);
//		client.setIoAdaptor(new StringAdaptor(counter));
		String content = FileUtil.readLine(new File("D:/aa.txt"), "UTF-8").substring(0,10);
		int N = 100;
		for (int i = 0; i < N; i++) {
			HttpMessage msg = new HttpMessage(content);
			msg.setCommand("get");
			msg.getMeta().setParam("hell", "888");
			
			
			msg.getMeta().setPath("get");
			client.invokeAsync(msg, new ResultCallback() {
				
				@Override
				public void onCompleted(Id result) {
					System.out.println(result);
				}
			});
//			msg = client.invokeSync(msg);
//			System.out.println(msg);
			
//			client.send(content);
		}
		
		long end = System.currentTimeMillis();

//		Thread.sleep(5000);
//		client.close();

	}
}
