package com.jeasy.base.interceptors;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.InvocationChain;
import net.paoding.rose.web.annotation.Interceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/17 10:26
 */
@Slf4j
@Interceptor(oncePerRequest = true)
public class ReqTimeCostInterceptor extends ControllerInterceptorAdapter {

	@Override
	protected Object round(Invocation inv, InvocationChain chain) throws Exception {
		long beginTime = System.currentTimeMillis();
		Object result = super.round(inv, chain);
		long costTime = System.currentTimeMillis() - beginTime;
		log.debug("Request URL: {}, Controller And Method: {}.{}, CostTime: {}ms", new Object[]{inv.getRequestPath().getUri(), inv.getControllerClass().getSimpleName(), inv.getMethod().getName(), costTime});
		return result;
	}
}
