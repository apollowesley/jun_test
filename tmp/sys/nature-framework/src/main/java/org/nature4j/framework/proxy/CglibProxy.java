package org.nature4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public abstract class CglibProxy {
	
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(T target,MethodInterceptor invocationHandler) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(invocationHandler);
		return (T)enhancer.create();
	}
}