package tom.net.nio;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.net.Client;
import tom.net.IoAdaptor;
import tom.net.http.Callback.ResultCallback;
import tom.net.http.HttpMessage;
import tom.net.http.id.Ticket;
import tom.net.http.id.TicketManager.Id;

public class NioClient implements Client {
	private static final Logger log = LoggerFactory.getLogger(NioClient.class);
	private IoAdaptor ioAdaptor;
	private final Selectors selectors = new Selectors();
	private NioSession session;
	protected final String brokerAddress;
	private int readTimeout = 10000;

	private String host = "127.0.0.1";
	private int port = 15555;
	private ConcurrentMap<String, Object> attributes = null;

	public NioClient(String address) {
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
		start();
	}

	public NioClient(String host, int port) {
		this.brokerAddress = String.format("%s:%d", host, port);
		start();
	}

	private void start() {
		selectors.setSelectCnt(1);
		if (!selectors.isStarted()) {
			selectors.start();
		}
	}

	public IoAdaptor getIoAdaptor() {
		return ioAdaptor;
	}

	public void setIoAdaptor(IoAdaptor ioAdaptor) {
		this.ioAdaptor = ioAdaptor;
	}

	public boolean hasConnected() {
		return session != null && session.isActive();
	}

	public void close() throws IOException {
		if (this.session != null) {
			this.session.close();
		}
		if(selectors!=null){
			selectors.close();
		}
	}

	@Override
	public void heartbeat() {
		if(hasConnected()){
			ioAdaptor.heartbeat(session);
		}
	}

	@Override
	public void doConnect() throws IOException {
		if (this.session != null) {
			if (this.session.isActive() || this.session.isNew()) {
				return;
			}
		}
		this.session = selectors.registerClientChannel(host, port, ioAdaptor);
	}

	public void ensureConnected() {
		while (!this.hasConnected()) {
			try {
				doConnect();
			} catch (IOException e) {
				log.info(e.getMessage(), e);
			}
		}
	}

	public void connectIfNeed() throws IOException {
		if (!this.hasConnected()) {
			doConnect();
			heartbeat();
		}
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
	
	public HttpMessage invokeSync(HttpMessage msg) throws IOException, InterruptedException {
		return invokeSync(msg, readTimeout);
	}

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
		this.session.write(msg);
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
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
