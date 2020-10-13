package common.framework.dsb.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.MarshalledObject;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import common.framework.dsb.proxy.ServiceProxy;

/**
 * RMI service provider
 * 
 * @author David Yuan
 * 
 */
public class RMIDSBServer implements DSBServer, Runnable {
	private int serverPort;
	private int rmiPort;
	private ServiceProxy serviceProxy = null;
	private MarshalledObject serverStub = null;
	private ServerSocket serverSocket = null;
	private boolean isClosed = false;

	/**
	 * Constructor
	 * 
	 * @param serverPort
	 *            the port of dynamic service
	 * @param rmiPort
	 *            RMI port
	 */
	public RMIDSBServer(int serverPort, int rmiPort, ServiceProxy serviceProxy) {
		super();
		this.serverPort = serverPort;
		this.rmiPort = rmiPort;
		this.serviceProxy = serviceProxy;
	}

	/*
	 * @see common.dynamic.service.server.ServiceProvider#start()
	 */
	public void start() throws Exception {
		Remote stub = UnicastRemoteObject.exportObject(serviceProxy, rmiPort);
		System.out.println(new Date() + " RMI service started at address: " + rmiPort);
		serverStub = new MarshalledObject(stub);
		serverSocket = new ServerSocket(serverPort);
		System.out.println(new Date() + " Dynamic service started at address: " + serverPort);
		Thread th = new Thread(this);
		th.setName("Dynamic service");
		th.setDaemon(true);
		th.start();
	}

	/*
	 * @see common.dynamic.service.server.ServiceProvider#close()
	 */
	public void close() throws Exception {
		isClosed = true;
		serverSocket.close();
		System.out.println(new Date() + " DSBServer was closed.");
	}

	public void run() {
		while (!isClosed) {
			Socket client = null;
			try {
				client = serverSocket.accept();
				OutputStream os = client.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.writeObject(serverStub);
				oos.flush();
				oos.close();
			} catch (Exception e) {
			} finally {
				if (client != null) {
					try {
						client.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
