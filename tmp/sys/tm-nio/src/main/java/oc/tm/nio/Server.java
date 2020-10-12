package oc.tm.nio;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import oc.tm.nio.core.IoAdaptor;
import oc.tm.nio.core.SelectorGroup;
import oc.tm.nio.log.Logger;
import oc.tm.nio.util.NetUtils;

/**
 * A Server is just the utility functionality of SelectorGroup which manages multiple server side
 * IoAdaptors, nothing else special
 * 
 * Callers could new a Server with a SelectorGroup and start all stuff together to quickly generate
 * a socket server.
 */
public class Server implements Closeable{
	
	private static final Logger log = Logger.getLogger(Server.class);  
	
	private static class IoAdaptorInfo{
		public String adaptorName;
		public String adaptorAddr;
		public ServerSocketChannel serverChannel;
		public IoAdaptor serverAdaptor;
	}
	
	protected SelectorGroup selectorGroup;   
	protected String serverAddr; //Server address = address of the first registered IoAdaptor
	protected String serverName = "Server";  
	protected String serverMainIpOrder = null;
	protected volatile boolean started = false;
	
	protected Map<String, IoAdaptorInfo> adaptors = new ConcurrentHashMap<String, IoAdaptorInfo>();
	
	public Server(){	
	}
	
	public Server(SelectorGroup selectorGroup){
		this.selectorGroup = selectorGroup;
	}
	
	public Server(SelectorGroup selectorGroup, IoAdaptor serverAdaptor, int port){
		this(selectorGroup, serverAdaptor, "0.0.0.0:"+port);
	}
	
	public Server(SelectorGroup selectorGroup, IoAdaptor serverAdaptor, String address){
		this.selectorGroup = selectorGroup;
		registerAdaptor(address, serverAdaptor);
	}
	
	public void registerAdaptor(int port, IoAdaptor ioAdaptor){
		registerAdaptor("0.0.0.0:"+port, ioAdaptor, null);
	}
	
	public void registerAdaptor(int port, IoAdaptor ioAdaptor, String name){
		registerAdaptor("0.0.0.0:"+port, ioAdaptor, name);
	}
	
	public void registerAdaptor(String address, IoAdaptor ioAdaptor){
		registerAdaptor(address, ioAdaptor, null);
	}

	public void registerAdaptor(String address, IoAdaptor ioAdaptor, String name){
		IoAdaptorInfo adaptor = new IoAdaptorInfo();
		String[] blocks = address.split("[:]");
		if(blocks.length != 2){
			throw new IllegalArgumentException(address + " is invalid address");
		}  
		adaptor.adaptorName = name;
		adaptor.adaptorAddr = address; 
		adaptor.serverAdaptor = ioAdaptor;  
		
		if(adaptors.isEmpty()){ //the first
			this.serverAddr = address;
		}
		
		adaptors.put(address, adaptor);
	}
	
	public void start(int port, IoAdaptor ioAdaptor) throws IOException{
		registerAdaptor(port, ioAdaptor);
		start();
	}
	
	public void start() throws IOException{   
		if(started) return;
		started = true;
		
		if(selectorGroup == null){
			throw new IllegalStateException("Missing SelectorGroup");
		}
    	this.selectorGroup.start();  
    	
    	for(Entry<String, IoAdaptorInfo> e : adaptors.entrySet()){ 
    		IoAdaptorInfo adaptor = e.getValue();
    		if(adaptor.serverChannel != null) continue;
    		
    		String address = adaptor.adaptorAddr; 
    		String[] bb = address.split("[:]");
    		if(bb.length !=2 ) continue;
    		String host = bb[0];
    		String port = bb[1];
    		
    		adaptor.serverChannel = selectorGroup.registerServerChannel(adaptor.adaptorAddr, adaptor.serverAdaptor);
        	
    		ServerSocket ss = adaptor.serverChannel.socket();
    		if(address.equals(serverAddr)){ 
        		String localHost = host;
        		if("0.0.0.0".equals(host)){
        			if(serverMainIpOrder == null){
        				localHost = NetUtils.getLocalIp();
        			} else {
        				localHost = NetUtils.getLocalIp(serverMainIpOrder);
        			}
        		}
        		serverAddr = String.format("%s:%d", localHost, ss.getLocalPort());
        	} 
    		if("0".equals(port)){
    			adaptor.adaptorAddr = String.format("%s:%d", host, ss.getLocalPort());
    		} 
    		
    		String info = String.format("%s-%s listening [%s]", this.serverName, adaptor.adaptorName, adaptor.adaptorAddr);
    		if(adaptor.adaptorName == null){
    			info = String.format("%s listening [%s]", this.serverName, adaptor.adaptorAddr);
    		}
        	log.info(info);
    	}  
    } 

    public void close() throws IOException { 
		for(Entry<String, IoAdaptorInfo> e : adaptors.entrySet()){ 
    		IoAdaptorInfo adaptor = e.getValue();
    		if(selectorGroup == null) continue; //
    		selectorGroup.unregisterServerChannel(adaptor.serverChannel);
    		adaptor.serverChannel = null;
    	}  
		adaptors.clear();
		started = false;
    }
    
	public void setSelectorGroup(SelectorGroup selectorGroup) {
		this.selectorGroup = selectorGroup;
	}
	
    public void setServerName(String serverName){
    	this.serverName = serverName;
    }  
    
	public String getServerAddr() {
		return serverAddr;
	}

	public boolean isStarted() {
		return started;
	} 
	
}
