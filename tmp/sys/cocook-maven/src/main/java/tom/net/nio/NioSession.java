package tom.net.nio;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.kit.ObjectId;
import tom.kit.ThreadPool;
import tom.net.Helper;
import tom.net.IoAdaptor;
import tom.net.IoBuffer;
import tom.net.Session;

public class NioSession implements Session,Closeable{
	public static enum SessionStatus {
		NEW, CONNECTED, ON_ERROR, CLOSED
	}
	private static final Logger log = LoggerFactory.getLogger(NioSession.class); 
	
	private SessionStatus status = SessionStatus.NEW;
	private final Long id; 
	
	private int bufferSize = 0;
	private IoBuffer readBuffer = null;
	private LinkedBlockingQueue<ByteBuffer> writeBufferQ = new LinkedBlockingQueue<ByteBuffer>(); //LinkedBlockingQueue
	
	private CountDownLatch connectLatch = new CountDownLatch(1);
	
	private final Selectors selectors;
	private final SocketChannel channel;
	private SelectionKey registeredKey;
	private SelectorThread selectorThread;
	private ConcurrentMap<String, Object> attributes;

	private Object attachment;
	private final IoAdaptor ioAdaptor;
	private boolean isServer = true; 
	public NioSession(Selectors selectors, SocketChannel channel, IoAdaptor ioAdaptor){
		this(selectors, channel, null, ioAdaptor); 
	}
	
	public NioSession(Selectors selectors, SocketChannel channel, Object attachment, IoAdaptor ioAdaptor){
		this.selectors = selectors;
		this.id = new ObjectId().toLong();
		this.channel = channel;
		this.attachment = attachment;
		this.ioAdaptor = ioAdaptor;
		this.bufferSize = ioAdaptor.getReadWritebufferSize();
//		readBuffer = IoBuffer.allocateDirect(bufferSize);
		readBuffer = IoBuffer.allocate(bufferSize);
	}
	
	public long id(){
		return this.id;
	} 
	
	public void write(Object msg){
		try {
			write(ioAdaptor.encode(msg));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void write(IoBuffer buf) throws IOException{
		if(buf.limit() > bufferSize){
			log.warn("writebuff {} > bufferSize {} ", buf.limit(), bufferSize);
			throw new IOException("writebuffSize  > initbufferSize  need ioadaptor set bufferSide");
		}
		if(!writeBufferQ.offer(buf.buf())){
			String msg = "Session write buffer queue is full, message count="+writeBufferQ.size();
			log.warn(msg);
			throw new IOException(msg);
		}
		upOpWrite();
	}
	
	private void upOpWrite() throws IOException{
		if(this.registeredKey == null){
			throw new IOException("Session not registered yet:"+this);
		}
		selectorThread.interestOps(registeredKey, registeredKey.interestOps() |SelectionKey.OP_WRITE);
	}
	
	
	protected int doWrite() throws IOException, InterruptedException{ 
		int n = 0;
		synchronized (writeBufferQ) {
			while(true){
				ByteBuffer buf = writeBufferQ.peek();
				if(buf == null){
					selectorThread.interestOps(registeredKey, SelectionKey.OP_READ);
					//registeredKey.selector().wakeup();  
					break;
				}
				log.debug("doWrite-->{}", buf);
				int wbytes = this.channel.write(buf);
				
				if(wbytes == 0 && buf.remaining() > 0){// 
					// 可以直接跳出, 不需要再添加 OP, 这个读写是循环的 OP次数> 循环次数
					//registeredKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
					//registeredKey.selector().wakeup();  
					//System.out.println(wbytes +"写不下了需要读=="+buf.remaining());
					break;
				}
				
				n += wbytes;
				if(buf.remaining() == 0){ // 使用 buf.remaining 来判断是否写完, 没写完, 继续写, 加入写不下了, 跳出 先读
					buf.clear();
					buf = null;
					writeBufferQ.remove();
					continue;
				} else {
					//没写完跳出,等待下次继续写
					//System.out.println(wbytes+"没写完, 继续写");
					break;
				}
			} 
		}
		return n;
	}
	
	
	@Override
	public void read(){
		try {
			doRead();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void doRead() throws Exception {
//		ByteBuffer data = ByteBuffer.allocate(1024*2);
		int n = 0;
		boolean readed = false;
		/* 一次性读写, 不多次扩展 readBuffer size , 否则使用 data 缓存读完后写入*/ 
		while((n = channel.read(readBuffer.buf())) > 0){
			readed = true;
//			data.flip();
//			readBuffer.writeBytes(data.array(), data.position(), data.remaining());
//			data.clear();
		}
		if(n == 0 && !readed) return;
		if(n < 0){
			NioSession.this.asyncClose();
			return;
		} 
		while (true) {
			readBuffer.markWriteIndex();
//			if(readBuffer.remaining() == 0) break;
			Object msg = ioAdaptor.decode(readBuffer.flip());
			if(msg == null){ 
				readBuffer.resetWriteIndex();
				break;
			}
			readBuffer.compact();
			log.debug("doRead-->{}", msg);
			ThreadPool.exec(new MessageRunnable(this, msg));
		}
	}
	
	void onMessage(Object msg){ 
		try{
			ioAdaptor.onMessage(msg, NioSession.this);
		} catch(IOException e){ 
			try {
				ioAdaptor.onException(e, NioSession.this);
			} catch (Exception e1) {
				log.error("ioAdaptor", e1);
			} 
		}finally{
			msg = null;
		}
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) { 
		if(obj instanceof NioSession){ 
			NioSession other = (NioSession)obj;
			return (this.hashCode() == other.hashCode());
		}
		return false;
	}

	public String getRemoteAddress() {
		if (this.status != SessionStatus.CLOSED) { 
			InetAddress addr = this.channel.socket().getInetAddress();
			return String.format("%s:%d", addr.getHostAddress(),channel.socket().getPort());
		} 
		return null;
	}
	
	public String getLocalAddress() {
		if (this.status != SessionStatus.CLOSED) { 
			return Helper.localAddress(this.channel);
		} 
		return null;
	}

	public int interestOps() throws IOException{
		if(this.registeredKey == null){
			throw new IOException("Session not registered yet:"+this);
		}
		return this.registeredKey.interestOps();
	}
	
	public void register(int interestOps) throws IOException{
		selectors.registerSession(interestOps, this);
	}

	public SelectionKey getRegisteredKey() {
		return registeredKey;
	}
	public void setRegisteredKey(SelectionKey key) {
		this.registeredKey = key;
	}
	
	@Override
	public void close() throws IOException {
		if(this.status == SessionStatus.CLOSED){
			return;
		}
		ioAdaptor.onSessionDestroyed(this);
		this.status = SessionStatus.CLOSED;
		//放到channel.close前面，避免ClosedChannelException
		if(this.registeredKey != null){
			this.registeredKey.cancel();
			this.registeredKey = null;
		} 
		
		if(this.channel != null){
			this.channel.close();
		}  
		this.writeBufferQ.clear();
		readBuffer.buf.clear();
		ioAdaptor.close();
	}
	
	public void asyncClose() throws IOException{ 
		selectorThread.unregisterSession(this);
	}
	
	public SessionStatus getStatus() {
		return status;
	}
	
	public boolean isActive(){
		return this.status == SessionStatus.CONNECTED;
	}
	public boolean isNew(){
		return this.status == SessionStatus.NEW;
	}

	public void setStatus(SessionStatus status) {
		this.status = status;
	}

	public SocketChannel getChannel() {
		return channel;
	} 
	
	public Selectors selectors() {
		return selectors;
	}
	
	public void finishConnect(){
		this.connectLatch.countDown();
	}
	
	public boolean waitToConnect(long millis){
		try { 
			return this.connectLatch.await(millis, TimeUnit.MILLISECONDS); 
		}catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T attr(String key){
		if(this.attributes == null){
			return null;
		}
		
		return (T)this.attributes.get(key);
	}
	
	public <T> void attr(String key, T value){
		if(this.attributes == null){
			synchronized (this) {
				if(this.attributes == null){
					this.attributes = new ConcurrentHashMap<String, Object>();
				}
			} 
		}
		this.attributes.put(key, value);
	}

	public String toString() {
		return "Session ["
				+ "remote=" + getRemoteAddress()
				+ ", status=" + status  
	            + ", id=" + id   
				+ "]";
	}

	public Object getAttachment() {
		return attachment;
	}

	public void setAttachment(Object attachment) {
		this.attachment = attachment;
	}

	public IoAdaptor getIoAdaptor() {
		return ioAdaptor;
	}

	public SelectorThread getSelectorThread() {
		return selectorThread;
	}

	public void setSelectorThread(SelectorThread selectorThread) {
		this.selectorThread = selectorThread;
	}

	@Override
	public void writeAndFlush(Object msg) {
		write(msg);
	}

	@Override
	public void flush() {
		//ignore
	}
	
	@Override
	public void catchError(Throwable e) {
		log.error(e.getMessage(), e);
	}

	@Override
	public boolean isServer() {
		return isServer;
	}

	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}
	
	
	public static class MessageRunnable implements Runnable{

		private NioSession session;
		private Object msg ;

		public MessageRunnable(NioSession session, Object msg) {
			this.session = session;
			this.msg = msg;
		}

		@Override
		public void run() {
			session.onMessage(msg);
		}
		
	}
}
