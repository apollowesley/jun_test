package org.nature.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.nature.framework.annotation.Service;
import org.nature.framework.proxy.CglibProxy;
import org.nature.framework.proxy.ProxyServiceInvocation;
import org.nature.framework.util.ClassUtil;
import org.nature.framework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(ServiceHelper.class);
	private static Map<Class<?>, Object> serviceMap = new HashMap<Class<?>, Object>();
	public static void initServices() {
		Set<Class<?>> classSet = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage(), Service.class);
		ProxyServiceInvocation proxyServiceInvocation = new ProxyServiceInvocation();
		for (Class<?> cls : classSet) {
			Object object = ReflectionUtil.newInstance(cls);
			Object proxyObject = CglibProxy.getInstance(object, proxyServiceInvocation);
			serviceMap.put(cls, proxyObject);
		}
	}
	
	public static Map<Class<?>, Object>  getServiceMap() {
		return serviceMap;
	}
	
	public static Object getService(Class<?> cls){
		Object object = serviceMap.get(cls);
		if(object==null){
			LOGGER.error(cls.getName()+" is not init or forget add @Service annotation ");
		}
		return object;
	}
}
