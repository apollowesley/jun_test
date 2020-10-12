package tom.net.http;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import tom.net.Session;
import tom.net.http.Callback.ResultCallback;
import tom.net.http.id.Ticket;
import tom.net.http.id.TicketManager;
import tom.net.http.id.TicketManager.Id;

public class MessageAdaptorClient extends MessageAdaptor {
	protected int heartbeatInterval = 60 * 6; // 60s *6
	ScheduledThreadPoolExecutor heartService = new ScheduledThreadPoolExecutor(1);
	
	protected Map<String, MessageHandler> handlerMap = new ConcurrentHashMap<String, MessageHandler>();
	public void registerHandler(String command, MessageHandler handler) {
		this.handlerMap.put(command, handler);
	}

	public String findHandlerKey(HttpMessage msg) {
		return msg.getCommand();
	}

	public MessageAdaptorClient() {
	}

	public void onMessage(Object obj, Session sess) throws IOException {
		HttpMessage msg = (HttpMessage) obj;
		
		if(msg.isStatus200()){  // 200 client 回调
			// 先验证是否有Ticket处理
			if (msg.getMsgId() == null || msg.getMsgId().isEmpty()){
				return;
			}
			Ticket ticket = removeTicket(msg.getMsgId());
			if (ticket != null) { // 此种情况为 ResultCallback 为null
				ticket.notifyResponse(msg);
				return;
			}
		}
		
		/*服务端命令执行*/
		MessageHandler handler = handlerMap.get(findHandlerKey(msg));
		if (handler != null) {
			handler.handleMessage(msg, sess);
			return;
		}
	}

	@Override
	public void heartbeat(final Session session) {
		synchronized (heartService) {
			if(heartService.getPoolSize() == 1) return;
			heartService.scheduleAtFixedRate(new Runnable() {
				public void run() {
					try {
						HttpMessage hbt = new HttpMessage();
						hbt.setCommand(HttpMessage.HEARTBEAT);
						session.write(hbt);
					} catch (Exception e) {
						//ignore
					}
				}
			}, heartbeatInterval, heartbeatInterval, TimeUnit.SECONDS);
		}
	}

	@Override
	public void close() {
		super.close();
		heartService.shutdown();
		ticketManager.close();
	}
	
	final static TicketManager ticketManager = new TicketManager(20000);
	
	public Ticket removeTicket(String id) {
		return ticketManager.removeTicket(id);
	}
	
	@Override
	public Ticket createTicket(Id req, long timeout,ResultCallback callback) {
		return ticketManager.createTicket(req, timeout, callback);
	}

	
}