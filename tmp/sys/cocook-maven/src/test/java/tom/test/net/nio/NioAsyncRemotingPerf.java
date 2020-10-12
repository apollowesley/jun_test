package tom.test.net.nio;

import java.util.concurrent.atomic.AtomicLong;

import tom.net.Helper;
import tom.net.http.HttpMessage;
import tom.net.nio.NioClient;
import tom.test.net.msg.MessageAdaptorClientT;

class Task1 extends Thread {
	private final NioClient client;
	private final long N;

	public Task1(NioClient client,long N) {
		this.client = client;
		this.N = N;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < N; i++) {
				HttpMessage msg = new HttpMessage("hello aio");
				msg.getMeta().setPath("get");
//				client.invokeAsync(msg, null); //带回调
				client.send(msg);  //测试 性能
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class NioAsyncRemotingPerf {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		final long N = Helper.option(args, "-N", 100000);
		final int threadCount = Helper.option(args, "-thread", 4);
		final String serverAddress = Helper.option(args, "-s", "127.0.0.1:80");
//		final String serverAddress = Helper.option(args, "-s", "192.168.5.112:80");
		final AtomicLong counter = new AtomicLong(0);
		NioClient[] clients = new NioClient[threadCount];
		for (int i = 0; i < clients.length; i++) {
			NioClient client = new NioClient(serverAddress);
			client.setIoAdaptor(new MessageAdaptorClientT(counter));
			clients[i] = client;
			client.connectIfNeed();
		}
		Thread.sleep(1000);

		Task1[] tasks = new Task1[threadCount];
		for (int i = 0; i < threadCount; i++) {
			tasks[i] = new Task1(clients[i], N);
		}
		for (Task1 task : tasks) {
			task.start();
		}

		// 4）释放链接资源与线程池相关资源
		// client.close();
		// dispatcher.close();
	}
}
