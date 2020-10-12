package tom.net.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.kit.ThreadPool;
import tom.net.IoAdaptor;
import tom.net.Server;

public class AioServer implements Server {
	private static final Logger log = LoggerFactory.getLogger(AioServer.class);
	protected String host = "0.0.0.0";
	protected int port;
	protected String serverAddr;
	protected String serverName = "AioServer";
	protected AsynchronousServerSocketChannel serverChannel;
	private AcceptHandler acceptHandler;
	
	public AioServer(int port) {
		this.port = port;
	}
	
	public AioServer(int port, int poolsize) {
		this.port = port;
	}

	public AioServer(String host, int port, int poolsize) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void start() throws IOException {
		AsynchronousChannelGroup asynchronousChannelGroup = AsynchronousChannelGroup.withThreadPool(ThreadPool.getAioPool());
		serverChannel = AsynchronousServerSocketChannel.open(asynchronousChannelGroup);
		serverChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		serverChannel.setOption(StandardSocketOptions.SO_RCVBUF, 64 * 1024);
		InetSocketAddress socketAddress = new InetSocketAddress(host, port);
		serverChannel.bind(socketAddress, 1000);
		accept(acceptHandler);
	}
	
	public void sync(){
		while(serverChannel.isOpen()){
			try{
				synchronized (serverChannel) {
					serverChannel.wait(100000);
				}
			}catch(InterruptedException e){}
		}
	}

	public void accept(AcceptHandler acceptCompletionHandler) {
		serverChannel.accept(this, acceptCompletionHandler);
	}

	@Override
	public void setIoAdaptor(IoAdaptor ioAdaptor) {
		acceptHandler = new AcceptHandler(ioAdaptor);
	}

	@Override
	public void close() throws IOException {
		if (serverChannel != null && serverChannel.isOpen()) {
			serverChannel.close();
		}
	}
	

}
