package org.jiucheng.aop;

import java.util.HashMap;
import java.util.Map;

import org.jiucheng.ioc.IOC;
import org.jiucheng.log.Logger;

public class AopFactory {
	private static final Logger log = Logger.getLogger(AopFactory.class);
	private static final Map<String, Object> interceptors = new HashMap<String, Object>();
	
	public static <T extends Interceptor> Interceptor getInterceptor(Class<T> clazz) {
		String className = clazz.getCanonicalName();
		Interceptor interceptor = (Interceptor) interceptors.get(className);
		if(null == interceptor) {
			try {
				interceptor = clazz.newInstance();
				IOC.injectField(interceptor);
				interceptors.put(className, interceptor);
			} catch (InstantiationException e) {
				if(log.isErrorEnabled()) {
					log.error("", e);
				}
			} catch (IllegalAccessException e) {
				if(log.isErrorEnabled()) {
					log.error("", e);
				}
			}
		}
		return interceptor;
	}
}
