package org.apache.center.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.playframework.domain.EasyuiJsonResult;
import org.apache.playframework.exception.MessagePromptException;
import org.apache.playframework.util.HttpServletUtils;
import org.apache.playframework.util.JacksonUtils;
import org.apache.playframework.util.ResponseUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	private final Log LOG = LogFactory.getLog(getClass());
	
	@ExceptionHandler
	protected void handleConflict(RuntimeException re, ServletWebRequest request) {  
		String msg = null;
		String url= null;
		if (re instanceof MessagePromptException) {
			MessagePromptException mpe = (MessagePromptException)re;
			msg = mpe.getMessage();
		} else {
			msg = "系统异常!";
			url = "/systemError";
			LOG.info("总异常捕获："+ re.getMessage(), re);
		}
		if (HttpServletUtils.isAjaxRequest(request.getRequest())) {
			ResponseUtils.renderText(request.getResponse(), JacksonUtils.writeValueAsString(EasyuiJsonResult.getFailureResult(msg)));
		} else {
			try {
				request.getRequest().getRequestDispatcher(url).forward(request.getRequest(),request.getResponse());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    } 
	
	
}
