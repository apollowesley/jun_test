package tom.test.net.msg;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import tom.net.Session;
import tom.net.http.HttpMessage;
import tom.net.http.MessageAdaptor;
import tom.net.http.Callback.ResultCallback;
import tom.net.http.id.Ticket;
import tom.net.http.id.TicketManager;
import tom.net.http.id.TicketManager.Id;

public class MessageAdaptorClientT extends MessageAdaptor {
	protected int heartbeatInterval = 60 * 6; // 60s *6
	ScheduledThreadPoolExecutor heartService = new ScheduledThreadPoolExecutor(1);
	static long startTime = 0;
	private AtomicLong counter;

	public MessageAdaptorClientT(AtomicLong counter) {
		this.counter = counter;
	}

	public MessageAdaptorClientT() {
	}

	public void onMessage(Object obj, Session sess) throws IOException {
		HttpMessage msg = (HttpMessage) obj;
		if("200".equals(msg.getStatus())){
			// 先验证是否有Ticket处理
			if (counter != null) {
				if(startTime==0) startTime = System.currentTimeMillis();
				counter.incrementAndGet();
				if (counter.get() % 2000 == 0) {
					double qps = counter.get() * 1000.0 / (System.currentTimeMillis() - startTime);
					System.out.format(counter.get() + "==QPS: %.2f\n", qps);
					System.out.println("getCacheSize==" + ticketManager.getCacheSize());
				}
			}
			if (msg.getMsgId() == null || msg.getMsgId().isEmpty()) {
				return;
			}
			Ticket ticket = removeTicket(msg.getMsgId());
			if (ticket != null) { // 此种情况为 ResultCallback 为null
				ticket.notifyResponse(msg);
				return;
			}
			log.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!Drop,{},{}", msg);
			
		}else{
			System.out.println("client.respone");
			msg.setStatus("200");
			msg.setBody("客户端执行命令成功");
			sess.write(msg);
		}
	
		//
		//
		 
	}

	@Override
	public void heartbeat(final Session session) {
		if(heartService.getPoolSize() == 1) return;
		heartService.scheduleAtFixedRate(new Runnable() {
			public void run() {
				try {
					HttpMessage hbt = new HttpMessage();
					hbt.setCommand(HttpMessage.HEARTBEAT);
					session.write(hbt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, heartbeatInterval, heartbeatInterval, TimeUnit.SECONDS);
	}

	@Override
	public void close() {
		super.close();
		//heartService.shutdown();
		ticketManager.close();
	}

	final static TicketManager ticketManager = new TicketManager(20000);

	public Ticket removeTicket(String id) {
		return ticketManager.removeTicket(id);
	}

	@Override
	public Ticket createTicket(Id req, long timeout, ResultCallback callback) {
		return ticketManager.createTicket(req, timeout, callback);
	}

}