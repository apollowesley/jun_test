package com.jeasy.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jeasy.util.ThreadLocalUtils;

import lombok.extern.log4j.Log4j;

/**
 * Controller层日志拦截器
 * @author taomk
 * @version 1.0
 * @since 15-5-22 下午7:57
 */
@Log4j
public class ControllerCostLogInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ThreadLocalUtils.putTime(System.currentTimeMillis());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		try {
			long endTime = System.currentTimeMillis();
			long beginTime = ThreadLocalUtils.getTime();
			long consumeTime = endTime - beginTime;

			StringBuilder logMsg = new StringBuilder("\n\nController execute report -------- " + new Date() + " ----------------------------");
			logMsg.append("\nURI         : ").append(request.getRequestURI()).append(", Method : ").append(request.getMethod());

			if (handler instanceof HandlerMethod) {
				logMsg.append("\nController  : ").append(((HandlerMethod) handler).getBeanType().getName()).append(", Method : ").append(((HandlerMethod) handler).getMethod().getName());
			}

			if (request.getMethod().equalsIgnoreCase("GET")) {
				logMsg.append("\nQueryString : ").append(request.getQueryString());
			} else if (request.getMethod().equalsIgnoreCase("POST")) {
				logMsg.append("\nParameter   : ").append(request.getParameterMap());
			}

			logMsg.append("\nCost Time   : ").append(consumeTime).append(" ms");
			logMsg.append("\n--------------------------------------------------------------------------------\n");
			log.info(logMsg.toString());

			if (ex != null) log.error(ex);
		} catch (Exception e) {
			log.error("TimeCostLog Occur Exception : ", e);
		} finally {
			ThreadLocalUtils.removeTime();
		}
	}
}
