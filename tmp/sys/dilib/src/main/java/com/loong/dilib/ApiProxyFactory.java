package com.loong.dilib;

import com.loong.dilib.cache.ApiCache;
import com.loong.dilib.core.ApiInterceptor;
import com.loong.dilib.core.ApiProxy;

/**
 * Api代理工厂类
 *
 * @author 张成轩
 */
public class ApiProxyFactory {

	/** 缓存处理器 */
	private ApiCache cache;
	/** 是否打印日志 */
	private Boolean showLog;

	/**
	 * @param cache 缓存处理器
	 */
	public void setCache(ApiCache cache) {

		this.cache = cache;
	}

	/**
	 * @param showLog 是否打印日志
	 */
	public void setShowLog(Boolean showLog) {

		this.showLog = showLog;
	}

	/**
	 * 创建Api代理类
	 * 
	 * @return Api代理
	 */
	public ApiProxy createApiProxy() {

		// 创建Api实体处理
		ApiInterceptor interceptor = new ApiInterceptor();
		// 添加缓存处理器
		if (cache != null)
			interceptor.setApiCache(cache);
		// 设置日志打印
		if (showLog != null)
			interceptor.setShowLog(showLog);
		// 创建Api代理类
		ApiProxy proxy = new ApiProxy();
		proxy.setInterceptor(interceptor);
		return proxy;
	}
}
