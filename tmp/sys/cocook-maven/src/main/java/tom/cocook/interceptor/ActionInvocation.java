package tom.cocook.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tom.cocook.config.Constants;
import tom.cocook.core.ControllerModel;
import tom.cocook.core.RequestContext;
import tom.cocook.handler.HandlerInfo;
import tom.cocook.handler.RequestMapping;

public class ActionInvocation{

	private transient HandlerInfo handlerInfo;  // 不被序列化
	private transient RequestContext context = RequestContext.get();
	private transient Map<String, String> params;

	private transient RequestMapping mapping;

	public ActionInvocation(HandlerInfo handlerInfo, RequestMapping mapping) {
		this.handlerInfo = handlerInfo;
		params = mergreParams();
		this.mapping = mapping;
	}

	/**
	 * 合并{params} + url标准传参
	 * @return
	 */
	private Map<String, String> mergreParams() {
		try {
			context.setCharacterEncoding(Constants.getEncoding());
		} catch (Exception e) {
		}

		Map<String, String> map = context.getParameterMap();
		map.putAll(context.getUrlMap());
		return map;
	}

	public RequestContext getRequestContext() {
		return context;
	}

	public ServletContext getServletContext() {
		return context.getServletContext();
	}

	public HttpSession getSession() {
		return context.getSession();
	}

	public HttpServletRequest getRequest() {
		return context.getRequest();
	}

	public HttpServletResponse getResponse() {
		return context.getResponse();
	}

	public Map<String, String> getParaMap() {
		return params;
	}

	public ControllerModel getAppControl() {
		return handlerInfo.getController();
	}

	public HandlerInfo getHandlerInfo() {
		return handlerInfo;
	}

	public Method getMethod() {
		return getHandlerInfo().getMethods().get(mapping);
	}

	public RequestMapping getMapping() {
		return mapping;
	}

	public void setMapping(RequestMapping mapping) {
		this.mapping = mapping;
	}

	public void recycle() {
		handlerInfo = null;
		params = null;
	}

}
