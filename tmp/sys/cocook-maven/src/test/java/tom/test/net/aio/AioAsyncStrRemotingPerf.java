package tom.test.net.aio;

import java.util.concurrent.atomic.AtomicLong;

import tom.net.Helper;
import tom.net.aio.AioClient;
import tom.net.http.HttpMessage;
import tom.test.net.msg.MessageAdaptorClientT;
import tom.test.net.msg.StringAdaptor;

class Task2 extends Thread {
	private final AioClient client;
	private final long N;

	public Task2(AioClient client,long N) {
		this.client = client;
		this.N = N;
	}

	@Override
	public void run() {
		
		try{
			aa();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	void aa(){
		try {
			for (int i = 0; i < N; i++) {
				client.send("hello"); //测试性能
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class AioAsyncStrRemotingPerf {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		final long N = Helper.option(args, "-N", 100000);
		final int threadCount = Helper.option(args, "-thread", 8);
		final String serverAddress = Helper.option(args, "-s", "127.0.0.1:80");
//		final String serverAddress = Helper.option(args, "-s", "192.168.5.112:80");

		final AtomicLong counter = new AtomicLong(0);

		AioClient[] clients = new AioClient[threadCount];
		for (int i = 0; i < clients.length; i++) {
			AioClient client = new AioClient(serverAddress);
//			client.setIoAdaptor(new MessageAdaptorClient(counter));
			client.setIoAdaptor(new StringAdaptor(counter));
			clients[i] = client;
			client.connectIfNeed();
		}

		Task2[] tasks = new Task2[threadCount];
		for (int i = 0; i < threadCount; i++) {
			tasks[i] = new Task2(clients[i], N);
		}
		for (Task2 task : tasks) {
			task.setDaemon(false);
			task.start();
		}

		// 4）释放链接资源与线程池相关资源
		// client.close();
		// dispatcher.close();
	}
}
