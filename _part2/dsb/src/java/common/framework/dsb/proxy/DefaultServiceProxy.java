package common.framework.dsb.proxy;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import common.framework.dsb.DynamicServiceBean;
import common.framework.dsb.ServiceException;
import common.framework.dsb.annotation.DSBLog;
import common.framework.dsb.annotation.DSBSql;
import common.framework.dsb.client.DefaultServiceFactory;
import common.framework.dsb.orm.SqlSessionProvider;
import common.framework.dsb.service.DefaultServiceContext;
import common.framework.dsb.service.ServiceConfig;
import common.framework.dsb.service.ServiceContext;
import common.framework.dsb.service.ServiceDescriptor;
import common.framework.log.Logger;

/**
 * Default service interpreter
 * 
 * @author David Yuan
 * 
 */
public class DefaultServiceProxy implements ServiceProxy {
	private Map<String, DynamicServiceBean> serviceList = new Hashtable<String, DynamicServiceBean>();

	private ServiceConfig serviceConfig = null;
	private UploadHandler uploadHandler = null;
	private SqlSessionProvider sqlSessionProvider = null;
	private boolean isClosed = false;
	private ClassLoader clazzLoader = null;

	public DefaultServiceProxy(ServiceConfig serviceConfig, SqlSessionProvider sqlSessionProvider) throws Exception {
		super();
		this.serviceConfig = serviceConfig;
		this.sqlSessionProvider = sqlSessionProvider;
		this.clazzLoader = Thread.currentThread().getContextClassLoader();
		DefaultServiceFactory.initLocalProxy(this);
	}

	@Override
	public void start(List<String> startupServices, List<String> disabledServies) throws Exception {
		// verify cycled references
		verifyReferrences();
		// load all startup services
		for (String serviceName : startupServices) {
			if (!disabledServies.contains(serviceName)) {
				getServiceBean(serviceName.trim());
			}
		}
		// load all startup services
		for (ServiceDescriptor sd : serviceConfig.listService()) {
			// startup features disabled if it contained by disabled service
			// list.
			if (sd.startup && !disabledServies.contains(sd.serviceName)) {
				getServiceBean(sd.serviceName);
			}
		}
	}

	@Override
	public void close() throws Exception {
		isClosed = true;

		System.out.println(new Date() + " ServiceProxy is closing.");
		// then, close all running services.
		Iterator<Map.Entry<String, DynamicServiceBean>> iter = serviceList.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, DynamicServiceBean> me = iter.next();
			String serviceName = me.getKey();
			DynamicServiceBean serviceObject = me.getValue();
			Logger.log(Logger.FUNCTION_LEVEL, " Service bean: " + serviceName + " is closing.");
			System.out.println(new Date() + " Service bean: " + serviceName + " is closing.");
			try {
				serviceObject.close();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
			Logger.log(Logger.FUNCTION_LEVEL, " Service bean: " + serviceName + " was closed.");
			System.out.println(new Date() + " Service bean: " + serviceName + " was closed.");
		}
		System.out.println(new Date() + " ServiceProxy was closed.");
		serviceList.clear();
	}

	public void ping() throws Exception {
		if (isClosed) {
			throw new Exception("DSB server was closed!");
		}
	}

	/*
	 * @see
	 * common.dynamic.service.interpreter.ServiceInterpreter#invoke(java.lang
	 * .String, java.lang.String, java.lang.Class[], java.lang.Object[])
	 */
	public Object invoke(String serviceName, String method, Class[] types, Object[] args) throws Exception {
		// 当出现动态加载时，启用clazzLoader进行线程上下文加载
		Thread.currentThread().setContextClassLoader(clazzLoader);
		DynamicServiceBean beanObject = getServiceBean(serviceName);
		if (beanObject == null) {
			throw new ServiceException("Service bean [" + serviceName + "] not found.");
		}
		return invoke(serviceName, beanObject, method, types, args);
	}

	/*
	 * @see
	 * common.dynamic.service.interpreter.ServiceInterpreter#lookup(java.lang
	 * .String)
	 */
	public LookupResult lookup(String serviceName) throws RemoteException {
		LookupResult lookupResult = new LookupResult();
		lookupResult.setResultStatus(LookupResult.BEAN_NOT_FOUND);
		ServiceDescriptor serviceDescriptor = serviceConfig.getService(serviceName);
		if (serviceDescriptor != null) {
			lookupResult.setResultStatus(LookupResult.BEAN_WAS_FOUND);
			lookupResult.setBeanDescriptor(serviceDescriptor);
		}
		return lookupResult;
	}

	/*
	 * @see common.dynamic.service.interpreter.ServiceInterpreter#listService()
	 */
	public List<ServiceDescriptor> listService() throws RemoteException {
		return serviceConfig.listService();
	}

	@Override
	public List<String> listStartedService() throws RemoteException {
		List<String> startedService = new ArrayList<String>();
		startedService.addAll(serviceList.keySet());
		return startedService;
	}

	@Override
	public boolean startService(String serviceName) throws Exception {
		LookupResult lookupResult = lookup(serviceName);
		if (LookupResult.BEAN_NOT_FOUND == lookupResult.getResultStatus()) {
			return false;
		}
		if (serviceList.containsKey(serviceName)) {
			return true;
		}
		getServiceBean(serviceName);
		return true;
	}

	@Override
	public boolean closeService(String serviceName) throws Exception {
		if (!serviceList.containsKey(serviceName)) {
			return false;
		}
		boolean result = false;
		Iterator<Map.Entry<String, DynamicServiceBean>> iter = serviceList.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, DynamicServiceBean> me = iter.next();
			String myName = me.getKey();
			DynamicServiceBean serviceObject = me.getValue();
			if (myName.equalsIgnoreCase(serviceName)) {
				Logger.log(Logger.FUNCTION_LEVEL, " Service bean: " + serviceName + " is closing.");
				System.out.println(new Date() + " Service bean: " + serviceName + " is closing.");
				try {
					serviceObject.close();
					Logger.log(Logger.FUNCTION_LEVEL, " Service bean: " + serviceName + " was closed.");
					System.out.println(new Date() + " Service bean: " + serviceName + " was closed.");
					result = true;
					break;
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
		}
		serviceList.remove(serviceName);
		return result;
	}

	@Override
	public boolean refreshService(String serviceName) throws Exception {

		if (!serviceList.containsKey(serviceName)) {
			return false;
		}
		boolean result = false;
		Iterator<Map.Entry<String, DynamicServiceBean>> iter = serviceList.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, DynamicServiceBean> me = iter.next();
			String myName = me.getKey();
			DynamicServiceBean serviceObject = me.getValue();
			if (myName.equalsIgnoreCase(serviceName)) {
				Logger.log(Logger.FUNCTION_LEVEL, " Service bean: " + serviceName + " is refreshing.");
				System.out.println(new Date() + " Service bean: " + serviceName + " is refreshing.");
				try {
					serviceObject.refresh();
					Logger.log(Logger.FUNCTION_LEVEL, " Service bean: " + serviceName + " was refreshed.");
					System.out.println(new Date() + " Service bean: " + serviceName + " was refreshed.");
					result = true;
					break;
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
		}
		return result;
	}

	@Override
	public String openSession(String jarName) throws Exception, RemoteException {
		return uploadHandler.openSession(jarName);
	}

	@Override
	public void upload(String sessionID, byte[] data, int length) throws Exception, RemoteException {
		uploadHandler.upload(sessionID, data, length);
	}

	@Override
	public void closeSession(String sessionID) throws Exception, RemoteException {
		uploadHandler.closeSession(sessionID);
	}

	private Object invoke(String service, DynamicServiceBean bean, String methodName, Class[] types, Object[] args) throws Exception {
		// lookup method
		Method m = bean.getClass().getMethod(methodName, types);
		// check DSBLog
		boolean log = m.isAnnotationPresent(DSBLog.class);
		boolean logConsole = false;
		if (log) {
			StringBuffer argValues = new StringBuffer();
			if (args != null) {
				for (Object obj : args) {
					argValues.append("|").append(obj.toString());
				}
			}
			DSBLog dsbLog = m.getAnnotation(DSBLog.class);
			String logType = dsbLog.log();
			if ("console".equalsIgnoreCase(logType)) {
				logConsole = true;
				System.out.println(new Date() + " Invoke:" + service + ":" + methodName + argValues.toString());
			} else {
				Logger.log(Logger.FUNCTION_LEVEL, "Invoke:" + service + ":" + methodName + argValues.toString());
			}
		}
		// check DBSession
		SqlSession sqlSession = null;
		bean.setSqlSessionProvider(sqlSessionProvider);
		boolean flag = m.isAnnotationPresent(DSBSql.class);
		if (flag) {
			DSBSql dbSession = m.getAnnotation(DSBSql.class);
			String environment = dbSession.environment();
			sqlSession = sqlSessionProvider.getSqlSession(environment, false);
			bean.setSqlSession(sqlSession);
		}
		// Invoke method
		long beginTime = System.nanoTime();
		Object result = null;
		try {
			result = m.invoke(bean, args);
			if (sqlSession != null) {
				sqlSession.commit();
			}
		} catch (Exception e) {
			Logger.printStackTrace(Logger.FUNCTION_LEVEL, service + ":" + methodName, e);
			if (logConsole) {
				e.printStackTrace(System.out);
			}
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			throw e;
		} finally {
			long endTime = System.nanoTime();
			double timeConsumed = (endTime - beginTime) / 1e6;
			if (logConsole) {
				System.out.println(new Date() + " Invoke:" + service + ":" + methodName + " in " + timeConsumed + " ms");
			} else if (log) {
				Logger.log(Logger.FUNCTION_LEVEL, "Invoke:" + service + ":" + methodName + " in " + timeConsumed + " ms");
			}
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return result;
	}

	private synchronized DynamicServiceBean getServiceBean(String serviceName) throws Exception {
		DynamicServiceBean beanObject = serviceList.get(serviceName);
		if (beanObject != null) {
			return beanObject;
		}
		ServiceDescriptor serviceDescriptor = serviceConfig.getService(serviceName);
		if (serviceDescriptor == null) {
			Logger.log(Logger.FUNCTION_LEVEL, serviceName + "  was not found, please check the service name.");
			System.out.println(serviceName + "  was not found, please check the service name.");
			return null;
		}
		Class beanClazz = clazzLoader.loadClass(serviceDescriptor.serviceClassName.trim());
		beanObject = (DynamicServiceBean) beanClazz.newInstance();
		Class[] types = { ServiceContext.class };
		Object[] args = { createServiceContext(serviceDescriptor) };
		invoke(serviceName, beanObject, "start", types, args);
		serviceList.put(serviceName, beanObject);
		return beanObject;
	}

	private ServiceContext createServiceContext(ServiceDescriptor beanDescriptor) {
		return new DefaultServiceContext(serviceConfig, beanDescriptor);
	}

	private void verifyReferrences() {

	}

	private boolean contains(String s, List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(s)) {
				return true;
			}
		}
		return false;
	}

}