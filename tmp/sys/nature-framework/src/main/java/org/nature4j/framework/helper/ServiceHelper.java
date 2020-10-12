package org.nature4j.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.nature4j.framework.annotation.Service;
import org.nature4j.framework.core.NatureService;
import org.nature4j.framework.proxy.CglibProxy;
import org.nature4j.framework.proxy.ProxyServiceInvocation;
import org.nature4j.framework.util.ClassUtil;
import org.nature4j.framework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(ServiceHelper.class);
	private static Map<Class<?>, Object> serviceMap = new HashMap<Class<?>, Object>();
	public static void initServices() {
		Set<Class<?>> classSet = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage(), Service.class);
		classSet.add(NatureService.class);
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
	
	public static <T> T getService(Class<T> cls){
		T object = (T) serviceMap.get(cls);
		if(object==null){
			LOGGER.error(cls.getName()+" is not init or forget add @Service annotation ");
		} 
		return object;
	}

	public static void clear() {
		serviceMap.clear();
	}
}
