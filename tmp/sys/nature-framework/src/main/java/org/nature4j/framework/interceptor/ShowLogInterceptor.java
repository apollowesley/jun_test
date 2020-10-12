package org.nature4j.framework.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.nature4j.framework.bean.JsonData;
import org.nature4j.framework.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowLogInterceptor implements NatureInterceptor {
	static Logger LOGGER = LoggerFactory.getLogger(ShowLogInterceptor.class);
	public void intercept(Invocation invocation) {
		long beg = System.currentTimeMillis();
		HttpServletRequest request = invocation.getRequest();
		LOGGER.info("====================["+ DateUtil.dateTime()+"]====================");
		LOGGER.info("请求地址："+request.getMethod()+":"+request.getRequestURI());
		LOGGER.info("请求参数："+invocation.getRequestParams().toString());
		invocation.invoke();
		Object returnValue = invocation.getReturnValue();
		if (returnValue!=null) {
			if (returnValue instanceof String) {
				LOGGER.info("返回结果："+returnValue.toString());
			}else if (returnValue instanceof JsonData) {
				LOGGER.info("返回结果："+((JsonData)returnValue).json);
			}else {
				LOGGER.info("返回结果："+returnValue);
			}
		}
		LOGGER.info("消耗时长："+(System.currentTimeMillis()-beg)+" ms");
		LOGGER.info("=============================================================\n");
	}
	
	public int level() {
		return 1024;
	}
	
}
