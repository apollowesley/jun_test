package tom.test.net.aio;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import tom.kit.io.FileUtil;
import tom.net.Session;
import tom.net.aio.AioClient;
import tom.net.http.HttpMessage;
import tom.net.http.MessageAdaptorClient;
import tom.net.http.MessageHandler;
import tom.test.net.msg.MessageAdaptorClientT;
import tom.test.net.msg.StringAdaptor;

public class AioClientTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		AioClient client = new AioClient("127.0.0.1:80");
		final AtomicLong counter = new AtomicLong(0);

		MessageAdaptorClient adaptor = new MessageAdaptorClient();
		adaptor.setReadWritebufferSize(10*1024);
//		adaptor.registerHandler("exec", new MessageHandler() {
//			
//			@Override
//			public void handleMessage(HttpMessage msg, Session sess) throws IOException {
//				System.out.println(msg);
//				msg.setStatus("200");
//				msg.setBody("exec 执行完成");
//				sess.write(msg);
//			}
//		});
		client.setIoAdaptor(adaptor);
		
//		client.setIoAdaptor(new StringAdaptor(counter));
		String content = FileUtil.readLine(new File("D:/aa.txt"), "UTF-8").substring(0,20);
		for(int i=0;i<1;i++){
			HttpMessage msg = new HttpMessage(content);
			msg.setCommand("test");
			msg.getMeta().setPath("test");
			
//			client.invokeAsync(msg, null);
			msg =client.invokeSync(msg);
			System.out.println(msg);
			
//			client.send("hello");
		}
//		Thread.sleep(5000);
//		client.close();
		
	}
}
