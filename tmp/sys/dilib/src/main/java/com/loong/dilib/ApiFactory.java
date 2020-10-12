package com.loong.dilib;

import com.loong.dilib.core.ApiProxy;
import com.loong.dilib.exception.DIAnnotationException;

/**
 * Api实例工厂类
 * 
 * @author 张成轩
 */
public class ApiFactory {

	private static ApiProxy apiProxy;

	/**
	 * 获取Api代理
	 * 
	 * @return Api代理
	 */
	private static ApiProxy getProxy() {

		if (apiProxy == null)
			apiProxy = new ApiProxyFactory().createApiProxy();
		return apiProxy;
	}

	/**
	 * 实例化Api对象
	 * 
	 * @param className 实例化接口名
	 * @return 实例对象
	 * @throws ClassNotFoundException
	 * @throws DIAnnotationException
	 */
	public static Object createApi(String className) throws ClassNotFoundException, DIAnnotationException {

		return getProxy().createApi(className);
	}

	/**
	 * 实例化Api对象
	 * 
	 * @param targetClass 接口类型
	 * @return 实例对象
	 * @throws DIAnnotationException
	 */
	public static <T> T createApi(Class<T> targetClass) throws DIAnnotationException {

		return getProxy().createApi(targetClass);
	}
}
