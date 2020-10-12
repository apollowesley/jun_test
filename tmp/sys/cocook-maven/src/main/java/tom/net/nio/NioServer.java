package tom.net.nio;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.net.Helper;
import tom.net.IoAdaptor;
import tom.net.Server;

public class NioServer implements Server{
	
	private static final Logger log = LoggerFactory.getLogger(NioServer.class);
	protected Selectors selectors;
	protected String host = "0.0.0.0";
	protected int port;
	protected String serverAddr;
	protected String serverName = "NioServer";
	protected ServerSocketChannel serverChannel;
	
	public NioServer(int port) {
		selectors = new Selectors();
		init("0.0.0.0", port, selectors);
	}

	public NioServer(int port, int selectCnt) {
		selectors = new Selectors();
		selectors.setSelectCnt(selectCnt);
		init("0.0.0.0", port, selectors);
	}

	public void init(String host, int port, Selectors selectors) {
		this.selectors = selectors;
		this.host = host;
		this.port = port;
		if ("0.0.0.0".equals(this.host)) {
			this.serverAddr = String.format("%s:%d", Helper.getLocalIp(), this.port);
		} else {
			this.serverAddr = String.format("%s:%d", this.host, this.port);
		}
	}

	public void start() throws IOException {
		if (serverChannel != null) {
			log.info("server already started");
			return;
		}
		if (!this.selectors.isStarted()) {
			this.selectors.start();
		}
		serverChannel = selectors.registerServerChannel(host, port);
		log.info("{} serving@{}:{}", this.serverName, this.host, this.port);
	}

	@Override
	public void close() throws IOException {
		if (serverChannel != null) {
			serverChannel.close();
		}
		if(selectors!=null){
			selectors.close();
		}
	}

	public void setIoAdaptor(IoAdaptor ioAdaptor) {
		selectors.serverIoAdaptor(ioAdaptor);
	}
	

}
