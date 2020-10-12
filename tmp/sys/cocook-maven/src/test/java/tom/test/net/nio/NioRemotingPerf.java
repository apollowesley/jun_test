package tom.test.net.nio;

import java.util.concurrent.atomic.AtomicLong;

import tom.net.Helper;
import tom.net.http.HttpMessage;
import tom.net.http.MessageAdaptorClient;
import tom.net.nio.NioClient;

class Task extends Thread {
	private final NioClient client;
	private final AtomicLong counter;
	private final long startTime;
	private final long N;

	public Task(NioClient client, AtomicLong counter, long startTime, long N) {
		this.client = client;
		this.counter = counter;
		this.startTime = startTime;
		this.N = N;
	}

	@Override
	public void run() {
		for (int i = 0; i < N; i++) {
			HttpMessage msg = new HttpMessage();
			msg.setCommand("test");
			msg.getMeta().setPath("get");
			try {
				client.invokeSync(msg);
				counter.incrementAndGet();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (counter.get() % 2000 == 0) {
				double qps = counter.get() * 1000.0 / (System.currentTimeMillis() - startTime);
				System.out.format(counter.get()+" :QPS: %.2f\n", qps);
			}
		}
	}
}

public class NioRemotingPerf {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		final long N = Helper.option(args, "-N", 5000);
		final int threadCount = Helper.option(args, "-thread", 10);
//		final String serverAddress = Helper.option(args, "-s", "127.0.0.1:80");
		final String serverAddress = Helper.option(args, "-s", "127.0.0.1:80");

		final AtomicLong counter = new AtomicLong(0);

		NioClient[] clients = new NioClient[threadCount];
		for (int i = 0; i < clients.length; i++) {
			NioClient client = new NioClient(serverAddress);
			client.setIoAdaptor(new MessageAdaptorClient());
			clients[i] = client;
		}

		final long startTime = System.currentTimeMillis();
		Task[] tasks = new Task[threadCount];
		for (int i = 0; i < threadCount; i++) {
			tasks[i] = new Task(clients[i], counter, startTime, N);
		}
		for (Task task : tasks) {
			task.start();
		}

		// 4）释放链接资源与线程池相关资源
		// client.close();
		// dispatcher.close();
	}
}
