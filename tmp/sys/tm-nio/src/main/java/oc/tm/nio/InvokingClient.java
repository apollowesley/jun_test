package oc.tm.nio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import oc.tm.nio.Sync.Id;
import oc.tm.nio.Sync.ResultCallback;
import oc.tm.nio.Sync.Ticket;
import oc.tm.nio.core.SelectorGroup;
import oc.tm.nio.core.Session;

/**
 * A client with functionality of <code>Invoker</code>
 * Internally, the InvokingClient mark every request with an Id, and match the response message
 * with the request matching table to perform the asynchronous invoking mechanism.
 *
 * @param <REQ> request message type that is Identifiable
 * @param <RES> response message type that is Identifiable
 */
public class InvokingClient<REQ extends Id, RES extends Id> 
		extends Client<REQ, RES> implements Invoker<REQ, RES> {	
	
	protected final Sync<REQ, RES> sync = new Sync<REQ, RES>();
	
	public InvokingClient(String host, int port, SelectorGroup selectorGroup) {
		super(host, port, selectorGroup); 
	} 
	
	public InvokingClient(String address, SelectorGroup selectorGroup) {
		super(address, selectorGroup); 
	} 
	
    protected void onMessage(Object obj, Session sess) throws IOException {  
		@SuppressWarnings("unchecked")
		RES res = (RES)obj; 
    	//先验证是否有Ticket处理
    	Ticket<REQ, RES> ticket = sync.removeTicket(res.getId());
    	if(ticket != null){
    		ticket.notifyResponse(res); 
    		return;
    	}  
    	
    	super.onMessage(obj, sess);
	}
	
	public void invokeAsync(REQ req, ResultCallback<RES> callback) throws IOException { 
    	connectSync();
    	
		Ticket<REQ, RES> ticket = null;
		if(callback != null){
			ticket = sync.createTicket(req, readTimeout, callback);
		} else {
			if(req.getId() == null){
				req.setId(Ticket.nextId());
			}
		} 
		try{
			session.write(req);  
		} catch(IOException e) {
			if(ticket != null){
				sync.removeTicket(ticket.getId());
			}
			throw e;
		}  
	} 
	
	public RES invokeSync(REQ req) throws IOException, InterruptedException {
		return this.invokeSync(req, this.readTimeout);
	}

	protected void onSessionDestroyed(Session sess) throws IOException { 
		super.onSessionDestroyed(sess);
		sync.clearTicket();
	}
	
	public RES invokeSync(REQ req, int timeout) throws IOException, InterruptedException {
		Ticket<REQ, RES> ticket = null;
		try {
			connectSync();
			ticket = sync.createTicket(req, timeout);
			session.write(req);

			if (!ticket.await(timeout, TimeUnit.MILLISECONDS)) {
				if (!session.isActive()) {
					throw new IOException("Connection reset by peer");
				} else {
					return null;
				}
			}
			return ticket.response();
		} finally {
			if (ticket != null) {
				sync.removeTicket(ticket.getId());
			}
		}
	} 
	
}
