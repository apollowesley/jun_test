package org.nature.framework.cache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature.framework.core.NatureCtrl;

public class NatureContext {
	static ThreadLocal<NatureCtrl> natureCtrlThreadLocal = new ThreadLocal<NatureCtrl>();
	static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();
	static ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<HttpServletResponse>();

	public static void setContext(NatureCtrl value, HttpServletRequest request, HttpServletResponse response) {
		natureCtrlThreadLocal.set(value);
		if (getRequest()==null) {
			requestThreadLocal.set(request);
			responseThreadLocal.set(response);
		}
	}
	
	public static void setContext(HttpServletRequest request, HttpServletResponse response) {
		requestThreadLocal.set(request);
		responseThreadLocal.set(response);
	}

	public static HttpServletRequest getRequest() {
		return requestThreadLocal.get();
	}

	public static HttpServletResponse getResponse() {
		return responseThreadLocal.get();
	}

	public static NatureCtrl getNatureCtrl() {
		return natureCtrlThreadLocal.get();
	}

}
