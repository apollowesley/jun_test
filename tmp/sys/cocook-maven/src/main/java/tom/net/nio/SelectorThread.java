package tom.net.nio;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.net.nio.NioSession.SessionStatus;

public class SelectorThread extends Thread {
	private static final Logger log = LoggerFactory.getLogger(SelectorThread.class);
	
	protected volatile java.nio.channels.Selector selector = null;
	protected Selectors selectors;
	private Queue<Object[]> register = new LinkedBlockingQueue<Object[]>();
	private Queue<NioSession> unregister = new LinkedBlockingQueue<NioSession>(); 
	
	public SelectorThread(Selectors selectors, String name) throws IOException{
		super(name);
		this.selectors = selectors;
		this.selector = java.nio.channels.Selector.open();
	}
	
	public SelectorThread(Selectors dispatcher) throws IOException{
		this(dispatcher, "Nio-Selector");
	}
	
	public void registerChannel(SelectableChannel channel, int ops) throws IOException{
		registerChannel(channel, ops, null); 
	}
	
	public void registerSession(int ops, NioSession sess) throws IOException{
		registerChannel(sess.getChannel(), ops, sess);
	}
	
	/**
	 * 在selector.select() 时会阻塞线程
	 * 此时channel.register(this.selector, ops, sess) 注册会被阻塞, 直到select()释放, 或主动registeredKey.selector().wakeup()
	 * 此时skey.interestOps(SelectionKey.OP_READ) 注册key时阻塞线程, 直到select()释放, 或主动registeredKey.selector().wakeup()
	 *  直接注册 无法注册成功
	 * @param channel
	 * @param ops
	 * @param sess
	 * @throws IOException
	 */
	public void registerChannel(SelectableChannel channel, int ops, NioSession sess) throws IOException{
		if(Thread.currentThread() == this){ 
			SelectionKey key = channel.register(this.selector, ops, sess);
			if(sess != null){
				sess.setRegisteredKey(key);
				sess.setSelectorThread(this);
				sess.setStatus(SessionStatus.CONNECTED);
				sess.getIoAdaptor().onSessionRegistered(sess);
			}
		} else { 
			this.register.offer(new Object[]{channel, ops, sess});
			this.selector.wakeup();
		}
	}
	

	public void interestOps(SelectionKey key, int ops){
		if(Thread.currentThread() == this){ 
			key.interestOps(ops);
		}else{
			key.interestOps(ops);
			this.selector.wakeup();
		}
	}
	
	public void unregisterSession(NioSession sess){
		if(this.unregister.contains(sess)){
			return;
		}
		if(Thread.currentThread() == this){ 
			this.unregister.add(sess);
		}else{
			this.unregister.add(sess);
			this.selector.wakeup();
		}
	}
	
	
	@Override
	public void interrupt() {  
		super.interrupt();
		try {
			this.selector.close();
		} catch (IOException e) { 
			log.error(e.getMessage(), e);
		}
	}
	
	
	@Override
	public void run() { 
		try{
			while(!isInterrupted()){
				selector.select(); 
				//log.debug("select warkup");
				handleRegister();
				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
				while(iter.hasNext()){
					SelectionKey key = iter.next();
					iter.remove();
					if(!key.isValid()) continue;
					
					try{ 
						if(key.isAcceptable()){
							handleAcceptEvent(key);
						}else
						if (key.isConnectable()){
							handleConnectEvent(key);  // 连接后跳出
							continue;
						}
						if (key.isReadable()){
							handleReadEvent(key);
						} else
						if (key.isWritable()){
							handleWriteEvent(key);
						}
					} catch(Exception e){ 
						log.error(e.getMessage(), e);
						disconnectWithException(key, e); 
					}
				}
				handleUnregister();
			}
		} catch(Exception e) {
			if(!selectors.isStarted()){
				if(log.isDebugEnabled()){
					log.debug(e.getMessage(), e);
				}
			} else {
				log.error(e.getMessage(), e);
			}
		}
		
	}
	
	private void disconnectWithException (SelectionKey key, Exception e){  
		NioSession sess = (NioSession)key.attachment();
		try{ 
			sess.setStatus(SessionStatus.ON_ERROR);
			sess.getIoAdaptor().onException(e, sess);
		} catch (Exception ex){
			if(!selectors.isStarted()){
				log.debug(e.getMessage(), ex);
			} else {
				log.error(e.getMessage(), ex);
			}
		}
		try{ 
			if(sess != null){ 
				sess.close();
			} else {
				key.channel().close();  
			}  
			key.cancel();
			sess = null;
		} catch(Exception ex){
			log.error(e.getMessage(), ex);
		}
	}
	
	protected void handleRegister(){
		synchronized (register) {
			Object[] item = null;
			while( (item=this.register.poll()) != null){
				try{
					SelectableChannel channel = (SelectableChannel) item[0];
					if (!channel.isOpen() ) continue;
					int ops = (Integer)item[1];
					NioSession sess = (NioSession) item[2]; 
					SelectionKey key = channel.register(this.selector, ops, sess);
					if(sess != null){
						sess.setRegisteredKey(key);
						sess.setSelectorThread(this);
						sess.getIoAdaptor().onSessionRegistered(sess);
					}
					item = null;
				}catch(Exception e){
					log.error(e.getMessage(), e);
				}
			}
		}
	}
	
	protected void handleUnregister(){
		synchronized (unregister) {
			NioSession sess = null;
			while( (sess = this.unregister.poll()) != null ){
				try {
					sess.close();
					log.info("session closed-->{}",sess);
					sess = null;
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}
	
	
	protected void handleAcceptEvent(SelectionKey key) throws Exception{ 
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		SocketChannel channel = server.accept();
		channel.configureBlocking(false);
		channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		channel.setOption(StandardSocketOptions.SO_RCVBUF, 64 * 1024);
		channel.setOption(StandardSocketOptions.SO_SNDBUF, 64 * 1024);
		channel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
		channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
		NioSession sess = new NioSession(selectors, channel, selectors.serverIoAdaptor()); 
		sess.setStatus(SessionStatus.CONNECTED); //set connected 
		sess.register(SelectionKey.OP_READ);
		sess.getIoAdaptor().onSessionAccepted(sess);
	} 
	
	protected void handleConnectEvent(SelectionKey key) throws IOException{
		final SocketChannel channel = (SocketChannel) key.channel();
		
		NioSession sess = (NioSession) key.attachment();
		if(sess == null){
			throw new IOException("Session not attached yet to SelectionKey");
		}  
		
		if(channel.finishConnect()){
			sess.setStatus(SessionStatus.CONNECTED);  
			key.interestOps(0); //!!!clear interest of OP_CONNECT to avoid looping CPU !!!
			interestOps(key, SelectionKey.OP_READ);
			sess.getIoAdaptor().onSessionConnected(sess);
			sess.finishConnect(); 
		}
	
	}
	
	protected void handleReadEvent(SelectionKey key) throws Exception{
		NioSession sess = (NioSession) key.attachment();
		if(sess == null){
			throw new IOException("Session not attached yet to SelectionKey");
		}
		log.debug("handleReadEvent-->{} ",sess);
		sess.doRead();
	}
	
	protected void handleWriteEvent(SelectionKey key) throws Exception{
		NioSession sess = (NioSession) key.attachment();
		if(sess == null){
			throw new IOException("Session not attached yet to SelectionKey");
		}
		log.debug("handleWriteEvent-->{} ",sess);
		sess.doWrite();   // 不合并 write
	}

}