package common.framework.dsb.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.rmi.MarshalledObject;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import common.framework.datetime.DateTimeUtils;
import common.framework.dsb.DynamicService;
import common.framework.dsb.ServiceException;
import common.framework.dsb.proxy.LookupResult;
import common.framework.dsb.proxy.ServiceProxy;
import common.framework.dsb.service.ServiceDescriptor;

/**
 * @author David Yuan
 * 
 */
public class DefaultServiceFactory implements ServiceFactory {

	/**
	 * Default timeout of socket connection, 0.1 seconds.
	 */
	private int socket_timeout = 500;

	/**
	 * 一个JVM中只存在一个localServiceProxy, DSB Server启动时进行初始化
	 */
	private static ServiceProxy localServiceProxy = null;

	/**
	 * 缓存server,针对每个server在client侧保存service proxy远程对象, proxy
	 * class在执行invoke方法时实时查找对应的service proxy
	 */
	private static Map<String, ServerObject> serverList = new Hashtable<String, ServerObject>();

	/**
	 * 缓存Service,针对每个service在client侧通过Proxy生成proxy class代理该service的所有方法调用
	 */
	private static Map<String, ServiceInvocationHandler> proxyClassList = new Hashtable<String, ServiceInvocationHandler>();

	/**
	 * 远程上传service时，每次上传的包的大小，在生产系统中，上传service的应用场景不多。
	 */
	private static final int BUFFER_SIZE = 2048; // 2k

	public DefaultServiceFactory() {
		if (System.getProperties().containsKey(DSB_SOCKET_TIMEOUT)) {
			socket_timeout = Integer.parseInt(System.getProperty(DSB_SOCKET_TIMEOUT));
		}
	}

	public DefaultServiceFactory(int socket_timeout) {
		super();
		this.socket_timeout = socket_timeout;
	}

	/**
	 * setup local service proxy
	 * 
	 * @param serviceProxy
	 *            ServiceProxy
	 */
	public static void initLocalProxy(ServiceProxy serviceProxy) {
		DefaultServiceFactory.localServiceProxy = serviceProxy;
	}

	/*
	 * @see
	 * common.dynamic.service.client.ServiceFactory#lookup(java.lang.String)
	 */
	public <T extends DynamicService> T lookup(String serviceName) throws Exception {
		return _lookup(DSB_LOCAL_CALL, serviceName, null, "localhost", 0);
	}

	/*
	 * @see
	 * common.dynamic.service.client.ServiceFactory#lookup(java.lang.String,
	 * java.lang.String, int)
	 */
	public <T extends DynamicService> T lookup(String serviceName, String host, int port) throws Exception {
		return _lookup(DSB_REMOTE_CALL, serviceName, null, host, port);
	}

	@Override
	public <T extends DynamicService> T lookup_ha(String serviceName) throws Exception {
		String haValue = System.getProperties().getProperty(DSB_HA_SERVER);
		if (haValue == null) {
			throw new NullPointerException("HA [" + DSB_HA_SERVER + "] not configured!");
		}
		return _lookup(DSB_HA_CALL, serviceName, DSB_HA_SERVER, null, 0);
	}

	@Override
	public <T extends DynamicService> T lookup_ha(String serviceName, String haName) throws Exception {
		String haValue = System.getProperties().getProperty(haName);
		if (haValue == null) {
			throw new NullPointerException("HA [" + haName + "] not configured!");
		}
		return _lookup(DSB_HA_CALL, serviceName, haName, null, 0);
	}

	@Override
	public ServiceProxy _lookup() throws Exception {
		return DefaultServiceFactory.localServiceProxy;
	}

	/**
	 * 客户端缓存远程对象ServiceProxy能极大地减少lookup该远程对象的次数，从而减少系统和网络开销；
	 * 只有当远程对象失效时(例如服务器端重启)才重新lookup该远程对象，否则缓存的远程对象无效导致调用失败；
	 * 因此每次使用ServiceProxy时，均通过ping()空方法检测其有效性
	 * 
	 * 
	 * @param host
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public synchronized ServiceProxy _lookup(String host, int port) throws Exception {
		StringBuffer serverKey = new StringBuffer(host).append(":").append(port);
		ServerObject serverObj = serverList.get(serverKey.toString());
		if (serverObj != null) {
			try {
				serverObj.serviceProxy.ping();
			} catch (Throwable th) {
				System.out.println("DSB server failed:" + host + ":" + port + " at " + DateTimeUtils.currentTime());
				serverList.remove(serverKey.toString());
				serverObj = null;
			}
		}
		if (serverObj == null) {
			// 重新读取远程对象，可能存在连接失败的情况
			serverObj = readRemteObject(host, port);
			serverList.put(serverKey.toString(), serverObj);
		}
		return serverObj.serviceProxy;
	}

	@Override
	public ServiceProxy _lookup_ha() throws Exception {
		return _lookup_ha(DSB_HA_SERVER);
	}

	@Override
	public synchronized ServiceProxy _lookup_ha(String haName) throws Exception {

		String recent_failed_host = null;
		int recent_failed_port = 0;

		// get from cache
		ServerObject serverObj = serverList.get(haName);
		if (serverObj != null) {
			// 防止server重启后，使用无效的serviceProxy
			try {
				serverObj.serviceProxy.ping();
			} catch (Throwable th) {
				System.out.println("DSB test failed:" + serverObj.host + ":" + serverObj.port + " at " + DateTimeUtils.currentTime());
				serverList.remove(haName);
				recent_failed_host = serverObj.host;
				recent_failed_port = serverObj.port;
				serverObj = null;
			}
		}
		if (serverObj == null) {
			Collection<HA> has = HA.readHA(haName);
			for (HA ha : has) {
				if (ha.dsb_host.equalsIgnoreCase(recent_failed_host) && ha.dsb_port == recent_failed_port) {
					// 跳过最近失败server，提升迁移速度
					continue;
				}
				try {
					// connect and read remote object
					serverObj = readRemteObject(ha.dsb_host, ha.dsb_port);
					serverList.put(haName, serverObj);
					break; // don't try any more, so break
				} catch (Throwable th) {
					System.out.println("Failed try connect : " + ha.dsb_host + ":" + ha.dsb_port);
					th.printStackTrace(System.out);
				}
			}// for
		}
		if (serverObj == null) {
			throw new Exception("No available servers in HA:" + haName);
		}
		return serverObj.serviceProxy;
	}

	/*
	 * @see common.dynamic.service.client.ServiceFactory#list(java.lang.String,
	 * int)
	 */
	public List<ServiceDescriptor> list(String host, int port) throws Exception {
		ServiceProxy serviceProxy = _lookup(host, port);
		return serviceProxy.listService();
	}

	public List<ServiceDescriptor> list() throws Exception {
		return DefaultServiceFactory.localServiceProxy.listService();
	}

	@Override
	public List<String> listStartedService() throws Exception {
		return DefaultServiceFactory.localServiceProxy.listStartedService();
	}

	@Override
	public boolean startService(String serviceName) throws Exception {
		return DefaultServiceFactory.localServiceProxy.startService(serviceName);
	}

	@Override
	public boolean closeService(String serviceName) throws Exception {
		return DefaultServiceFactory.localServiceProxy.closeService(serviceName);
	}

	@Override
	public boolean refreshService(String servieName) throws Exception {
		return DefaultServiceFactory.localServiceProxy.refreshService(servieName);
	}

	/*
	 * @see
	 * common.dynamic.service.client.ServiceFactory#installService(java.lang
	 * .String, java.lang.String, int)
	 */
	public void installService(String jarPath, String host, int port) throws Exception {
		ServiceProxy serviceProxy = _lookup(host, port);
		File file = new File(jarPath);
		InputStream inputStream = new FileInputStream(file);
		String sessionID = serviceProxy.openSession(file.getName());
		byte[] buffer = new byte[BUFFER_SIZE];
		int len = 0;
		try {
			while (true) {
				len = inputStream.read(buffer);
				if (len > 0) {
					ByteBuffer bb = ByteBuffer.wrap(buffer, 0, len);
					bb.flip();
					serviceProxy.upload(sessionID, bb.array(), len);
				} else {
					break;
				}
			}
		} finally {
			inputStream.close();
			serviceProxy.closeSession(sessionID);
		}
	}

	private ServerObject readRemteObject(String host, int port) throws Exception {

		Socket socket = new Socket();
		ServerObject serverObj = null;
		try {
			InetSocketAddress address = new InetSocketAddress(host, port);
			socket.connect(address, socket_timeout);
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			MarshalledObject serverStub = (MarshalledObject) ois.readObject();
			serverObj = new ServerObject();
			serverObj.serviceProxy = (ServiceProxy) serverStub.get();
			serverObj.host = host;
			serverObj.port = port;
			ois.close();
		} finally {
			socket.close();
		}

		return serverObj;
	}

	/**
	 * 客户端缓存DynamicService能极大地减少产生动态代理实例的次数，从而减少系统开销. DynamicService 的 proxy
	 * class 在方法被调用时，重新获取service proxy (可能缓存在客户端)
	 * 
	 * @param callType
	 *            DSB_LOCAL_CALL,DSB_REMOTE_CALL,DSB_HA_CALL
	 * @param serviceName
	 *            service name
	 * @param haName
	 *            HA name( configured int system properties)
	 * @param host
	 *            server address
	 * @param port
	 *            server port
	 * @return
	 * @throws Exception
	 */
	private <T extends DynamicService> T _lookup(int callType, String serviceName, String haName, String host, int port) throws Exception {
		String serviceKey = null;
		if (DSB_LOCAL_CALL == callType || DSB_REMOTE_CALL == callType) {
			StringBuffer sb = new StringBuffer();
			// host:port/service
			sb.append(host).append(":").append(port).append("/").append(serviceName);
			serviceKey = sb.toString();
		} else if (DSB_HA_CALL == callType) {
			StringBuffer sb = new StringBuffer();
			// ha/service
			sb.append(haName).append("/").append(serviceName);
			serviceKey = sb.toString();
		}

		ServiceInvocationHandler invocationHandler = proxyClassList.get(serviceKey);
		// 内存中已经存在，直接返回，真正invoke method时，再重新lookup ServiceProxy
		if (invocationHandler != null) {
			return (T) invocationHandler.getProxyClass();
		}

		// 处理内存中没有proxy class的情况， 首先lookup remote service proxy (包括容错处理)
		ServiceProxy serviceProxy = null;
		if (DSB_LOCAL_CALL == callType) {
			serviceProxy = _lookup();
		} else if (DSB_REMOTE_CALL == callType) {
			serviceProxy = _lookup(host, port);
		} else if (DSB_HA_CALL == callType) {
			serviceProxy = _lookup_ha(haName);
		} else {
			throw new Exception("Invalid call type:" + callType);
		}

		LookupResult lookupResult = serviceProxy.lookup(serviceName);
		if (LookupResult.BEAN_NOT_FOUND == lookupResult.getResultStatus()) {
			throw new ServiceException("No such service on: " + serviceKey);
		}

		ServiceDescriptor serviceDescriptor = lookupResult.getServiceDescriptor();
		if (serviceDescriptor == null) {
			// HA模式时，异常信息的准确性存在问题
			throw new ServiceException(serviceKey + " descriptor not on: " + serviceKey);
		}

		// 其实可以把proxy class直接缓存，而不对ServiceInvocationHandler缓存，但由于proxy
		// class无法进一步封装,对应的ServiceInvocationHandler可能会被GC
		// 因此这里反过来，在ServiceInvocationHandler中封装proxy
		// class，同时对ServiceInvocationHandler进行缓存
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// client端加载远端服务的interface class
		Class beanClass = classLoader.loadClass(serviceDescriptor.interfaceClassName);
		invocationHandler = new ServiceInvocationHandler(this, callType, serviceName, haName, host, port);
		// 初始化proxy class, proxy class的任何调用通过ServiceInvocationHandler实现
		Object proxyClass = Proxy.newProxyInstance(classLoader, new Class[] { beanClass }, invocationHandler);
		// 在ServiceInvocationHandler中封装proxyClass，防止proxyClass被GC
		invocationHandler.setProxyClass(proxyClass);
		proxyClassList.put(serviceKey, invocationHandler);

		return (T) invocationHandler.getProxyClass();
	}
}