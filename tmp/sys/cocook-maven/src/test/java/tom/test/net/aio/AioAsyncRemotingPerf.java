package tom.test.net.aio;

import java.util.concurrent.atomic.AtomicLong;

import tom.net.Helper;
import tom.net.aio.AioClient;
import tom.net.http.HttpMessage;
import tom.test.net.msg.MessageAdaptorClientT;

class Task extends Thread {
	private final AioClient client;
	private final long N;

	public Task(AioClient client,long N) {
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
				HttpMessage msg = new HttpMessage("hello aio");
				msg.setCommand("test");
//				client.invokeAsync(msg, null); //带回调
				client.send(msg); //测试性能
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class AioAsyncRemotingPerf {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		final long N = Helper.option(args, "-N", 100000);
		final int threadCount = Helper.option(args, "-thread", 4);
		final String serverAddress = Helper.option(args, "-s", "127.0.0.1:8080");
//		final String serverAddress = Helper.option(args, "-s", "192.168.5.112:80");

		final AtomicLong counter = new AtomicLong(0);

		AioClient[] clients = new AioClient[threadCount];
		for (int i = 0; i < clients.length; i++) {
			AioClient client = new AioClient(serverAddress);
			client.setIoAdaptor(new MessageAdaptorClientT(counter));
			clients[i] = client;
//			client.connectIfNeed();
		}
		
//		Thread.sleep(1000);
		
		Task[] tasks = new Task[threadCount];
		HttpMessage msg = new HttpMessage("hello aio");
		msg.setCommand("test");
		for (int i = 0; i < clients.length; i++) {
			Task task 	= new Task(clients[i], N);
			task.setName("task_"+i);
			tasks[i]  = task;
		}
		for (Task task : tasks) {
			task.setDaemon(false);
			task.start();
		}

		// 4）释放链接资源与线程池相关资源
		// client.close();
		// dispatcher.close();
	}
}
