package oc.tm.nio;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import oc.tm.nio.Sync.Id;

/**
 * Sync maintains all the request and response table, converting asynchronous mechanism into
 * synchonous one
 * @param <REQ> request type
 * @param <RES> response type
 */
public class Sync<REQ extends Id, RES extends Id> { 
	
	private static AtomicLong idGenerator = new AtomicLong(0);
	private static boolean useUuid = true;
	public static void enableUuid(boolean useUuid){
		Sync.useUuid = useUuid;
	}
	
	/**
	 * Ticket is a matching record of a request and a response message
	 * @param <REQ> request type
	 * @param <RES> response type
	 */
	public static class Ticket<REQ extends Id, RES> {    
		private CountDownLatch latch = new CountDownLatch(1);
		
		private String id = "";
		private REQ request = null; 
		private RES response = null;  
		private ResultCallback<RES> callback = null; 
		 
		private long timeout = 1000; 
		private final long startTime = System.currentTimeMillis(); 
		
		
		public Ticket(REQ request, long timeout) {   
			this.id = nextId();
			if(request != null){
				request.setId(this.id);
			}
			
			this.request = request; 
			this.timeout = timeout;
		} 
		
		public static String nextId(){
			if(useUuid){
				return UUID.randomUUID().toString(); 
			}
			return ""+idGenerator.incrementAndGet();
		}
	 
		public boolean await(long timeout, TimeUnit unit)
				throws InterruptedException {
			boolean status = this.latch.await(timeout, unit); 
			return status;
		}
	 
		public void await() throws InterruptedException {
			this.latch.await(); 
		}
	 
		public void expired() { 
			this.countDown(); 
		}
	 
		private void countDown() {
			this.latch.countDown();
		}
	 
		public boolean isDone() {
			return this.latch.getCount() == 0;
		}
	 
		public void notifyResponse(RES response) {
			this.response = response;
			if (this.callback != null)
				this.callback.onReturn(response); 
			this.countDown();
		} 
	 
		public ResultCallback<RES> getCallback() {
			return callback;
		}
	 
		public void setCallback(ResultCallback<RES> callback) {
			this.callback = callback;
		} 
		 
		public String getId() {
			return id;
		}

		public REQ request() {
			return this.request;
		}
		public RES response() {
			return this.response;
		}
		public long getTimeout() {
			return timeout;
		}
		public long getStartTime() {
			return startTime;
		} 
	}
	
	private ConcurrentMap<String, Ticket<REQ, RES>> tickets = new ConcurrentHashMap<String, Ticket<REQ, RES>>();
 
	public Ticket<REQ, RES> getTicket(String id) {
		if(id == null) return null;
		return tickets.get(id);
	}
 
	public Ticket<REQ, RES> createTicket(REQ req, long timeout) {
		return createTicket(req, timeout, null);
	}
 
	public Ticket<REQ, RES> createTicket(REQ req, long timeout, ResultCallback<RES> callback) {
		Ticket<REQ, RES> ticket = new Ticket<REQ, RES>(req, timeout);
		ticket.setCallback(callback);

		if (tickets.putIfAbsent(ticket.getId(), ticket) != null) {
			throw new IllegalArgumentException("duplicate ticket number.");
		}

		return ticket;
	} 
	
	public  Ticket<REQ, RES> removeTicket(String id) {
		if(id == null) return null;
		return tickets.remove(id);
	}
	
	public void clearTicket(){
		for(Ticket<REQ, RES> ticket : tickets.values()){
			ticket.countDown();
		}
		tickets.clear();
	}
	
	/**
	 * Identifiable message required by Sync/Async mechanism
	 */
	public static interface Id {
		void setId(String id);
		String getId();
	}
	
	/**
	 * Asynchronous message callback
	 * @param <T> returned message type
	 */
	public static interface ResultCallback<T> { 
		public void onReturn(T result);  
	}
}
