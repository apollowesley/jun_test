package tom.net.http.id;

import java.io.Serializable;

import tom.kit.cache.TimedCache;
import tom.net.http.HttpMessage;
import tom.net.http.Callback.ResultCallback;

public class TicketManager { 
	public static interface Id extends Serializable{
		Id setMsgId(String id);
		String getMsgId();
	}
	
	private TimedCache<String, Ticket> tickets = null;
	
	public TicketManager(long timeout) {
		tickets = new TimedCache<String, Ticket>(timeout);
		 
	}
	
	/**
	 * 超时回收
	 * @param delay
	 */
	public void schedulePrune(long delay){
		tickets.schedulePrune(delay);
	}
	
	
	public Ticket getTicket(String id) {
		if(id == null) return null;
		return tickets.get(id);
	}
 
	public Ticket createTicket(HttpMessage req, long timeout) {
		return createTicket(req, timeout, null);
	}
 
	public Ticket createTicket(Id req, long timeout, ResultCallback callback) {
		Ticket ticket = new Ticket(req, timeout, callback);
		tickets.put(ticket.getId(), ticket, timeout);
		return ticket;
	} 
	
	public Ticket removeTicket(String id) {
		return tickets.remove(id);
	}
	
	public void close(){
		tickets.clear();
		tickets.cancelPruneSchedule();
	}
	
	public int getCacheSize(){
		return tickets.size();
	}
}
