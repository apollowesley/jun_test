package tom.net;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.net.http.Callback.ResultCallback;
import tom.net.http.id.Ticket;
import tom.net.http.id.TicketManager.Id;

public abstract class IoAdaptor {

	protected static final Logger log = LoggerFactory.getLogger(IoAdaptor.class);
	private int readWritebufferSize = 8 * 1024; // 实现最大读写数, 防止默认 buffersize 扩展大小, 不进行扩展
	private Map<Long, Session> sessionMap = new ConcurrentHashMap<>();

	/**
	 * 消息对象到网络字节码编码
	 * 
	 * @param msg
	 * @return
	 */
	public abstract IoBuffer encode(Object msg);

	/**
	 * 从网络字节码中解码消息对象
	 * 
	 * @param buff
	 * @return
	 */
	public abstract Object decode(IoBuffer buff);

	public void heartbeat(Session sess) {

	}

	/**
	 * 服务器端侦听到链接接入回调，此时Session尚未注册，默认注册该Session
	 * 
	 * @param sess
	 * @throws IOException
	 */
	public void onSessionAccepted(Session sess) throws IOException {
		sessionMap.put(sess.id(), sess);
		log.info("ACCEPT: {}-->{} sessionSize:{}", sess.getRemoteAddress(), sess.getLocalAddress(), sessionMap.size());
	}

	/**
	 * Session注册到Dispatcher成功后回调
	 * 
	 * @param sess
	 * @throws IOException
	 */
	public void onSessionRegistered(Session sess) throws IOException {
		log.info("Registered: {}-->{}", sess.getRemoteAddress(), sess.getLocalAddress());
	}

	/**
	 * 客户端链接成功后回调
	 * 
	 * @param sess
	 * @throws IOException
	 */
	public void onSessionConnected(Session sess) throws IOException {
		log.info("CONNECT: {}-->{} ", sess.getRemoteAddress(), sess.getLocalAddress());
	}

	/**
	 * Session注销前回调
	 * 
	 * @param sess
	 * @throws IOException
	 */
	public void onSessionDestroyed(Session sess) throws IOException {
		sessionMap.remove(sess.id());
		log.info("SessionDestroyed: {}-->{} sessionSize:{}", sess.getRemoteAddress(), sess.getLocalAddress(), sessionMap.size());
	}

	/**
	 * Session接受到消息对象
	 * 
	 * @param msg
	 * @param sess
	 * @throws IOException
	 */
	public void onMessage(Object msg, Session sess) throws IOException {

	}
	
	public Map<Long, Session> getSessions(){
		return sessionMap;
	}

	/**
	 * Session各类错误发生时回调
	 * 
	 * @param e
	 * @param sess
	 * @throws IOException
	 */
	public void onException(Throwable e, Session sess) throws Exception {
		if (e instanceof IOException) {
			throw (IOException) e;
		} else if (e instanceof RuntimeException) {
			throw (RuntimeException) e;
		} else {
			throw new RuntimeException(e.getMessage(), e); // rethrow by default
		}
	}

	public void close() {
	}

	public Ticket createTicket(Id req, long timeout, ResultCallback callback) {
		return null;
	}

	public Ticket removeTicket(String id) {
		return null;
	}

	public int getReadWritebufferSize() {
		return readWritebufferSize;
	}

	public void setReadWritebufferSize(int readWritebufferSize) {
		this.readWritebufferSize = readWritebufferSize;
	}

}
