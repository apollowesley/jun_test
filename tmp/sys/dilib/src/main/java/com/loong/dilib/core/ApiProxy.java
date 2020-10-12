package com.loong.dilib.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Api实例化工具类
 */
public class ApiProxy {

	/** 日志输出对象 */
	private static Log log = LogFactory.getLog(ApiProxy.class);

	/** 接口处理实体对象 */
	private MethodInterceptor interceptor;

	/**
	 * 设置接口处理实体对象
	 * 
	 * @param interceptor 接口处理实体对象
	 */
	public void setInterceptor(MethodInterceptor interceptor) {

		this.interceptor = interceptor;
	}

	/**
	 * 实例化Api对象
	 * 
	 * @param className 实例化接口名
	 * @return 实例对象
	 * @throws ClassNotFoundException ClassNotFound异常
	 */
	public Object createApi(String className) throws ClassNotFoundException {

		return createApi(Class.forName(className));
	}

	/**
	 * 实例化Api对象
	 * 
	 * @param targetClass 接口类型
	 * @return 实例对象
	 */
	@SuppressWarnings("unchecked")
	public <T> T createApi(Class<T> targetClass) {

		log.info("[dilib] createApi: " + targetClass.getName());

		// 校验Api注释
		ApiChecker.check(targetClass);
		// 创建代理类
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(interceptor);
		return (T) enhancer.create();
	}
}
