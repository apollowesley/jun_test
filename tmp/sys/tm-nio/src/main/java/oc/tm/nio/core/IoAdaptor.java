package oc.tm.nio.core;

import java.io.IOException;
import java.nio.channels.SelectionKey;
/**
 * IoAdaptor is the core configurable entry that a networking application should be focus on, 
 * it personalizes the codec between object on the wire and business domain message object, and all
 * the other life cycle events of networking such as session accepted, connected, registered, on error
 * and etc. It is pretty simple compared to netty channel chain design.
 */
public abstract class IoAdaptor implements Codec{ 
	
	private Codec codec; //pluggable codec
	
	public IoAdaptor codec(Codec codec){
		this.codec = codec;
		return this;
	}
	
	@Override
	public Object decode(IoBuffer buff) {
		return codec.decode(buff); 
	}
	
	@Override
	public IoBuffer encode(Object msg) { 
		return codec.encode(msg);
	}
	
	/**
	 * Server side event when server socket accept an incoming client socket.
	 * Session is still unregister during this stage, so the default action is to register it,
	 * typically with OP_READ interested, application can change this behavior for special usage.
	 * 
	 * This event is for server only
	 * 
	 * @param sess Session generated after accept of server channel
	 * @throws IOException if fails
	 */
	protected void onSessionAccepted(Session sess) throws IOException { 
		if(sess.selectorGroup() != null){
			sess.selectorGroup().registerSession(SelectionKey.OP_READ, sess); 
		}
	}
	/**
	 * Triggered after session registered, omit this event by default
	 * 
	 * @param sess session registered
	 * @throws IOException if fails
	 */
	protected void onSessionRegistered(Session sess) throws IOException {  
	
	} 
	/**
	 * Triggered after initiative client connection(session) is successful
	 * 
	 * This event is for client only
	 * 
	 * @param sess connected session
	 * @throws IOException if fails
	 */
	protected void onSessionConnected(Session sess) throws IOException{ 
		sess.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
	}
	
	/**
	 * Triggered before session is going to be destroyed, session is still legal in this stage
	 * 
	 * @param sess session to be destroyed
	 * @throws IOException if fails
	 */
	protected void onSessionToDestroy(Session sess) throws IOException{
		
	}
	
	/**
	 * Triggered after the session is destroyed
	 * 
	 * @param sess session destroyed, illegal in this stage
	 * @throws IOException if fails
	 */
	protected void onSessionDestroyed(Session sess) throws IOException{
		
	}
	/**
	 * Triggered when application level messaged is fully parsed(well framed).
	 * 
	 * @param msg application level message decided by the codec
	 * @param sess message generating session
	 * @throws IOException if fails
	 */
	protected void onMessage(Object msg, Session sess) throws IOException{
		
	}
	/**
	 * Triggered when session error caught
	 * @param e error ongoing
	 * @param sess corresponding session
	 * @throws IOException if fails
	 */
	protected void onException(Throwable e, Session sess) throws IOException{
		if(e instanceof IOException){
			throw (IOException) e;
		} else if (e instanceof RuntimeException){
			throw (RuntimeException)e;
		} else {
			throw new RuntimeException(e.getMessage(), e); //rethrow by default
		}
	}
}