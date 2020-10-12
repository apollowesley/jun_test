package tom.net.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.kit.ObjectId;
import tom.kit.ThreadPool;
import tom.net.IoAdaptor;
import tom.net.IoBuffer;
import tom.net.Session;
import tom.net.nio.NioSession.MessageRunnable;

public class AioSession implements Session{
	private static final Logger log = LoggerFactory.getLogger(AioSession.class); 
	private final Long id; 
	
	private int bufferSize = 0;
	private IoBuffer readBuffer = null;
	public  LinkedBlockingQueue<ByteBuffer> writeBufferQ = new LinkedBlockingQueue<ByteBuffer>();
	private ConcurrentMap<String, Object> attributes;
	
	private CountDownLatch connectLatch = new CountDownLatch(1);
	private AsynchronousSocketChannel	channel;
	private IoAdaptor ioAdaptor;
	private AioServer aioServer;
	
	private boolean isServer = true; 
	
	public AioSession(IoAdaptor ioAdaptor, AsynchronousSocketChannel channel) {
		this.ioAdaptor = ioAdaptor;
		this.channel = channel;
		this.id = new ObjectId().toLong();
		this.bufferSize = ioAdaptor.getReadWritebufferSize();
//		readBuffer = IoBuffer.allocateDirect(bufferSize);
		readBuffer = IoBuffer.allocate(bufferSize);
	}

	@Override
	public long id(){
		return this.id;
	} 
	
	@Override
	public String getRemoteAddress() {
		if (isActive()) { 
			try {
				InetSocketAddress addr = (InetSocketAddress) this.channel.getRemoteAddress();
				return String.format("%s:%d", addr.getHostString(),addr.getPort());
			} catch (IOException e) {}
		} 
		return null;
	}

	@Override
	public String getLocalAddress() {
		if (isActive()) {
			try {
				InetSocketAddress addr = (InetSocketAddress) this.channel.getLocalAddress();
				return String.format("%s:%d", addr.getHostString(),addr.getPort());
			} catch (IOException e) {}
		}
		return null;
	}

	@Override
	public void write(Object msg) {
		try {
			write(ioAdaptor.encode(msg));
			ThreadPool.exec(new MergeRunnable(this)); //多线程的时候 增大writeBufferQ 的数量, 一次性提交
		} catch (Exception e) {
			catchError(e);
		}
	}

	
	@Override
	public void writeAndFlush(Object msg) {
		syncWrite(ioAdaptor.encode(msg).buf);
	}
	
	protected int syncWrite(ByteBuffer buf) {
		synchronized (channel) {
			try {
				return this.channel.write(buf).get();
			} catch (InterruptedException | ExecutionException e) {
				catchError(e);
				return 0;
			}
		}
	}
	
	protected void write(IoBuffer buf) throws IOException{
		log.debug("write");
		if(buf.limit() > bufferSize){
			log.warn("writebuff {} > bufferSize {} ", buf.limit(), bufferSize);
			throw new IOException("writebuffSize  > initbufferSize need ioadaptor set bufferSide");
		}
		if(!writeBufferQ.offer(buf.buf())){
			String msg = "Session write buffer queue is full, message count="+writeBufferQ.size();
			log.warn(msg);
			throw new IOException(msg);
		}
	}
	
	protected void doMergeWrite() throws InterruptedException, ExecutionException {
		synchronized (writeBufferQ) {
			while(true){
				int size = writeBufferQ.size();
				if (size == 0) {
					return;
				}
				if(size == 1){
					ByteBuffer buf = writeBufferQ.poll();
					log.debug("doWrite-->{}", buf);
					this.channel.write(buf).get();
					return;
				}
				if (size >500) size = 500;
				IoBuffer buffer = IoBuffer.allocate(bufferSize * size);
				for (int i = 0; i < size; i++) {
					ByteBuffer buf = writeBufferQ.poll();
					//System.out.println(buf.limit()+"==="+ buf.array().length);
					buffer.writeBytes(buf.array(), 0, buf.limit());
				}
				buffer.flip();
				ByteBuffer buf = buffer.buf();
				log.debug("doWrite-->{}", buf);
				this.channel.write(buf).get();
			}
		}
	}
	
	
	protected int doWrite() throws IOException, InterruptedException, ExecutionException{ 
		int n = 0;
		synchronized (writeBufferQ) {
			while(true){
				ByteBuffer buf = writeBufferQ.peek();
				if(buf == null) break;
				log.debug("doWrite-->{}", buf);
				int wbytes = this.channel.write(buf).get();
				if(wbytes == 0 && buf.remaining() > 0){
					break;
				}
				n += wbytes;
				if(buf.remaining() == 0){
					writeBufferQ.remove();
					continue;
				} else {
					break;
				}
			}
		}
		return n;
	}
	
	@Override
	public void read() {
		log.debug("read");
		channel.read(readBuffer.buf(), this, new ReadHandler());
	}
	
	public void doRead() { 
		while(true){
			readBuffer.markWriteIndex();
			Object msg = ioAdaptor.decode(readBuffer.flip());
			if(msg == null){
				readBuffer.resetWriteIndex();
				break;
			}
			readBuffer.compact();
			log.debug("doRead-->{}", msg);
			onMessage(msg);  //本身是多线程的, 不需要再开线程
		}
	}
	
	
	void onMessage(Object msg){ 
		try{
			ioAdaptor.onMessage(msg, this);
		} catch(IOException e){ 
			try {
				ioAdaptor.onException(e, this);
			} catch (Exception e1) {
				log.error("ioAdaptor", e1);
			} 
		}finally{
			msg = null;
		}
	}
	

	@Override
	public void flush() {
		//ignore
	}

	@Override
	public boolean isActive() {
		if (channel != null && channel.isOpen()) {
			return true;
		}
		return false;
	}

	@Override
	public void asyncClose() throws IOException {
		if(isActive()){
			ioAdaptor.onSessionDestroyed(this);
			this.channel.close();  
			ioAdaptor.close();
		}  
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

	public void setIoAdaptor(IoAdaptor ioAdaptor) {
		this.ioAdaptor = ioAdaptor;
	}
	
	public IoAdaptor getIoAdaptor() {
		return ioAdaptor;
	}

	public void close() {
		try {
			asyncClose();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void catchError(Throwable e) {
		if(e instanceof IOException){
			close();
		}
		log.error(e.getMessage(), e);
	}

	@Override
	public boolean isServer() {
		return isServer;
	}

	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}

	public AioServer getAioServer() {
		return aioServer;
	}

	public void setAioServer(AioServer aioServer) {
		this.aioServer = aioServer;
	}
	
	
	public static class MergeRunnable implements Runnable{

		private AioSession session;

		public MergeRunnable(AioSession session) {
			this.session = session;
		}

		@Override
		public void run() {
			try{
				session.doMergeWrite();
			}catch(Exception e){
				session.catchError(e);
			}
		}
		
	}
	
}
