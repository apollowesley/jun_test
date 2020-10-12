package tom.net.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.kit.ThreadPool;
import tom.net.Client;
import tom.net.IoAdaptor;
import tom.net.http.Callback.ResultCallback;
import tom.net.http.id.Ticket;
import tom.net.http.id.TicketManager.Id;
import tom.net.http.HttpMessage;

public class AioClient implements Client{
	private static final Logger log = LoggerFactory.getLogger(AioClient.class);
	protected final String brokerAddress;
	private int readTimeout = 10000;

	
	private String host = "127.0.0.1";
	private int port = 15555;
	private ConcurrentMap<String, Object> attributes = null;
	
	private IoAdaptor ioAdaptor;
	public AioSession	session;
	

	public AioClient(String address) throws IOException {
		this.brokerAddress = address;
		String[] blocks = address.split("[:]");
		if (blocks.length > 2) {
			throw new IllegalArgumentException("Illegal address: " + address);
		}
		String host = blocks[0].trim();
		this.host = host;
		int port = 15555;
		if (blocks.length > 1) {
			port = Integer.valueOf(blocks[1].trim());
		}
		this.port = port;
	}
	
	@Override
	public void heartbeat() {
		if(hasConnected()){
			ioAdaptor.heartbeat(session);
		}
	}
	
	private static AsynchronousChannelGroup asynchronousChannelGroup = null;
	private void initChannelGroup() throws IOException{
		asynchronousChannelGroup = AsynchronousChannelGroup.withThreadPool(ThreadPool.getAioPool());
	}

	@Override
	public void doConnect() throws IOException {
		if(asynchronousChannelGroup == null) initChannelGroup();
		AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open(asynchronousChannelGroup);
		socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
		socketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
		session = new AioSession(ioAdaptor, socketChannel);
		ConnectedHandler connectedCompletionHandler = new ConnectedHandler(session);
		InetSocketAddress socketAddress = new InetSocketAddress(this.host, this.port);
		socketChannel.connect(socketAddress, this, connectedCompletionHandler);
		session.waitToConnect(20000);
	}

	@Override
	public void invokeAsync(Object req, ResultCallback callback) throws IOException {
		connectIfNeed();
		if (req instanceof Id) {
			Id r = (Id) req;
			ioAdaptor.createTicket(r, readTimeout, callback);
		}
		session.write(req);
	}

	/**
	 * 同步AIO的时候有可能会阻塞无返回现象, 
	 * 天生的异步, 不推荐使用
	 */
	@Override
	public HttpMessage invokeSync(HttpMessage req) throws IOException, InterruptedException {
		return invokeSync(req, readTimeout);
	}
	
	/**
	 * @param req
	 * @param timeout
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public HttpMessage invokeSync(HttpMessage req, int timeout) throws IOException, InterruptedException {
    	Ticket ticket = null; 
		try {
			connectIfNeed();
			ticket = ioAdaptor.createTicket(req, timeout, null); 
			session.write(req);
			if(!ticket.await(timeout, TimeUnit.MILLISECONDS)){
				if(!session.isActive()){
					throw new IOException("Connection reset by peer");
				} else {
					return null;
				}
			}
		    return (HttpMessage) ticket.response(); 
		} finally{
			if(ticket != null){
				ioAdaptor.removeTicket(ticket.getId());
			}
		}   
    } 

	@Override
	public void send(Object msg) throws IOException {
		connectIfNeed();
		session.write(msg);
	}

	@Override
	public void setIoAdaptor(IoAdaptor ioAdaptor) {
		this.ioAdaptor = ioAdaptor;
	}
	

	public void connectIfNeed() throws IOException {
		if (!this.hasConnected()) {
			doConnect();
			heartbeat();
		}
	}
	
	public boolean hasConnected() {
		return session != null && session.isActive();
	}
	
	@Override
	public void close() throws IOException {
		if (this.session != null) {
			this.session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T attr(String key) {
		if (this.attributes == null) {
			return null;
		}
		return (T) this.attributes.get(key);
	}

	public <T> void attr(String key, T value) {
		if (this.attributes == null) {
			synchronized (this) {
				if (this.attributes == null) {
					this.attributes = new ConcurrentHashMap<String, Object>();
				}
			}
		}
		this.attributes.put(key, value);
	}

}
