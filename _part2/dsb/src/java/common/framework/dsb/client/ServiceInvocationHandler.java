package common.framework.dsb.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import common.framework.dsb.proxy.ServiceProxy;

/**
 * Service invocation handler
 * 
 * @author David Yuan
 * 
 */
public class ServiceInvocationHandler implements InvocationHandler {
	private ServiceFactory serviceFactory = null;
	private Object proxyClass = null; // 封装proxyClass，防止被GC

	private int callType;
	private String serviceName = null;
	private String haName = null;
	private String host = null;
	private int port;

	/**
	 * Constructor
	 * 
	 * @param callType
	 *            DSB_LOCAL_CALL,DSB_REMOTE_CALL,DSB_HA_CALL
	 * @param serviceName
	 * @param haName
	 * @param host
	 * @param port
	 */
	public ServiceInvocationHandler(ServiceFactory serviceFactory, int callType, String serviceName, String haName, String host, int port) {
		super();
		this.serviceFactory = serviceFactory;
		this.callType = callType;
		this.serviceName = serviceName;
		this.haName = haName;
		this.host = host;
		this.port = port;
	}

	public Object getProxyClass() {
		return proxyClass;
	}

	public void setProxyClass(Object proxyClass) {
		this.proxyClass = proxyClass;
	}

	/*
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		ServiceProxy serviceProxy = null;

		if (ServiceFactory.DSB_LOCAL_CALL == callType) {
			serviceProxy = serviceFactory._lookup();
		} else if (ServiceFactory.DSB_REMOTE_CALL == callType) {
			serviceProxy = serviceFactory._lookup(host, port);
		} else if (ServiceFactory.DSB_HA_CALL == callType) {
			serviceProxy = serviceFactory._lookup_ha(haName);
		} else {
			throw new Exception("Invalid call type:" + callType);
		}

		String methodName = method.getName();
		Class[] paramTypes = method.getParameterTypes();
		return serviceProxy.invoke(serviceName, methodName, paramTypes, args);
	}
}
