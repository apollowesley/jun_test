package org.nature.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.nature.framework.annotation.GlobalInterceptor;
import org.nature.framework.interceptor.NatureInterceptor;
import org.nature.framework.util.ClassUtil;
import org.nature.framework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterceptorHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(InterceptorHelper.class);
	private static Map<Class<?>, NatureInterceptor> interceptorMap = new HashMap<Class<?>, NatureInterceptor>();
	private static Map<Class<?>, NatureInterceptor> globleInterceptorMap = new HashMap<Class<?>, NatureInterceptor>();
	public static void initInterceptor() {
		Set<Class<?>> classSet = ClassUtil.getClassSetByInterface(ConfigHelper.getAppBasePackage(), NatureInterceptor.class);
		for (Class<?> cls : classSet) {
			NatureInterceptor obj = (NatureInterceptor) ReflectionUtil.newInstance(cls);
			interceptorMap.put(cls, obj);
		}
		Set<Class<?>> globleClassSet = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage(), GlobalInterceptor.class);
		for (Class<?> cls : globleClassSet) {
			NatureInterceptor obj = (NatureInterceptor) ReflectionUtil.newInstance(cls);
			globleInterceptorMap.put(cls, obj);
		}
	}
	public static Map<Class<?>, NatureInterceptor>  getInterceptorMap() {
		return interceptorMap;
	}
	
	public static Map<Class<?>, NatureInterceptor>  getGlobleInterceptorMap() {
		return globleInterceptorMap;
	}
	
	public static NatureInterceptor getInterceptor(Class<?> cls){
		NatureInterceptor object = interceptorMap.get(cls);
		if(object==null){
			LOGGER.error(cls.getName()+" is not init or forget implements Interceptor ");
		}
		return object;
	}
}
