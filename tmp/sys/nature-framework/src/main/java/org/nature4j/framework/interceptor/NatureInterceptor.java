package org.nature4j.framework.interceptor;

public interface NatureInterceptor{
	void intercept(Invocation invocation);
	
	/**
	 * 当前拦截器级别，值越大级别越高，拦截器越靠前,系统最高级别1024，默认为0
	 * @version 1.2.9
	 */
	int level();
}
