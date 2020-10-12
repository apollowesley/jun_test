package tom.test.net.nio;

import java.util.concurrent.atomic.AtomicLong;

import tom.net.Helper;
import tom.net.nio.NioClient;
import tom.test.net.msg.StringAdaptor;

class Task2 extends Thread {
	private final NioClient client;
	private final long N;

	public Task2(NioClient client,long N) {
		this.client = client;
		this.N = N;
	}

	@Override
	public void run() {
		try {
//			client.connectIfNeed();
//			Thread.sleep(1000);
			for (int i = 0; i < N; i++) {
				client.send("hello");  //测试 性能
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class NioAsyncStrRemotingPerf {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		//-thread 100 -N 8000  ==> 800000==QPS: 107584.72
		final long N = Helper.option(args, "-N", 100000);
		final int threadCount = Helper.option(args, "-thread",4);
		final String serverAddress = Helper.option(args, "-s", "127.0.0.1:80");
//		final String serverAddress = Helper.option(args, "-s", "192.168.5.112:80");
		final AtomicLong counter = new AtomicLong(0);
		NioClient[] clients = new NioClient[threadCount];
		for (int i = 0; i < clients.length; i++) {
			NioClient client = new NioClient(serverAddress);
			client.setIoAdaptor(new StringAdaptor(counter));
			clients[i] = client;
			client.connectIfNeed();
		}
		
		Thread.sleep(2000);

		Task2[] tasks = new Task2[threadCount];
		for (int i = 0; i < threadCount; i++) {
			tasks[i] = new Task2(clients[i], N);
		}
		for (Task2 task : tasks) {
			task.start();
		}

		// 4）释放链接资源与线程池相关资源
		// client.close();
		// dispatcher.close();
	}
}
