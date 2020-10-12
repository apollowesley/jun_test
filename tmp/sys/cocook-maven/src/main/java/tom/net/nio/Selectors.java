package tom.net.nio;
import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.kit.ThreadPool;
import tom.net.IoAdaptor;

public class Selectors implements Closeable {
	private static final Logger log = LoggerFactory.getLogger(Selectors.class); 
	
	private int selectCnt= ThreadPool.cpuCoreCount>2 ? ThreadPool.cpuCoreCount/2 :ThreadPool.cpuCoreCount;
	private SelectorThread[] selectors;
	private AtomicInteger selectorIndex = new AtomicInteger(0);
	private String selectorName  = "cocook-nio-Selector";
	
	protected volatile boolean started = false;  //直接读内存
	private IoAdaptor serverIoAdaptor;
	
	private void init() throws IOException{
		this.selectors = new SelectorThread[this.selectCnt];
		for(int i=0;i<this.selectCnt;i++){
			String name = String.format("%s-%d",selectorName, i);
			this.selectors[i] = new SelectorThread(this, name);
		}
	}
	
	public SelectorThread getSelector(int index){
		if(index <0 || index>=this.selectCnt){
			throw new IllegalArgumentException("Selector index should >=0 and <"+this.selectCnt);
		}
		return this.selectors[index];
	}
	
	public SelectorThread nextSelector(){
		int nextIdx = this.selectorIndex.getAndIncrement()%this.selectCnt;
		if(nextIdx < 0){
			this.selectorIndex.set(0);
			nextIdx = 0;
		} 
		return this.selectors[nextIdx];
	}

	public void registerChannel(SelectableChannel channel, int ops) throws IOException{
		this.nextSelector().registerChannel(channel, ops);
	}
	
	public void registerSession(int ops, NioSession sess) throws IOException{
		if(sess.selectors() != this){
			throw new IOException("Unmatched Dispatcher");
		}
	
		this.nextSelector().registerSession(ops, sess);
	}
	
	public SelectorThread getSelector(SelectionKey key){
		for(SelectorThread e : this.selectors){
			if(key.selector() == e.selector){
				return e;
			}
		}
		return null;
	}
	
	public synchronized void start() {
		if (this.started) {
			return;
		}
		try {
			this.init();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return;
		}
		this.started = true;
		for (SelectorThread selector : this.selectors) {
			selector.setDaemon(false);
			selector.start();
		} 
	}
	
	public synchronized void stop() {
		if (!this.started)
			return;

		this.started = false;
		for (SelectorThread selector : this.selectors) {
			selector.interrupt();
			selector = null;
		}
		log.info("{}(SelecctorCount={}) stopped", this.selectorName, this.selectCnt);
	}
	
	public void close() throws IOException {
		this.stop();
	}
	
	public boolean isStarted(){
		return this.started;
	}
	
	public int getSelectCnt() {
		return selectCnt;
	}

	public void setSelectCnt(int selectCnt) {
		this.selectCnt = selectCnt;
	}

	public IoAdaptor serverIoAdaptor(){
		return this.serverIoAdaptor; 
	}
	public Selectors serverIoAdaptor(IoAdaptor serverIoAdaptor){ 
		if(this.serverIoAdaptor != null){
			throw new IllegalStateException("Server IoAdaptor already exists");
		}
		this.serverIoAdaptor = serverIoAdaptor;
		return this;
	}
	
	public ServerSocketChannel registerServerChannel(String host, int port) throws IOException{
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    	serverSocketChannel.configureBlocking(false);
    	serverSocketChannel.socket().bind(new InetSocketAddress(host, port)); 
    	serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
    	serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 64 * 1024);
    	this.registerChannel(serverSocketChannel, SelectionKey.OP_ACCEPT);
    	return serverSocketChannel;
	}
	
	
	public NioSession registerClientChannel(String host, int port, IoAdaptor ioAdaptor) throws IOException{
		SocketChannel channel = SocketChannel.open();
    	channel.configureBlocking(false);
    	channel.connect(new InetSocketAddress(host, port)); 
    	NioSession session = new NioSession(this, channel, ioAdaptor);
    	session.setServer(false);
    	this.registerSession(SelectionKey.OP_CONNECT, session);
    	session.waitToConnect(20000);  //等待连接释放
    	return session;
	}
	
}
