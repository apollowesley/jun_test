package tom.cocook.interceptor;

import java.io.Serializable;
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


@SuppressWarnings("serial")
public class ActionInvocation implements Serializable {
	
	private transient HandlerInfo  handlerInfo= null;
	private transient RequestContext context= RequestContext.get();
	private transient Map<String, String> params;
	private transient boolean isPost = false;
	
	private transient String methodUrl;

	public ActionInvocation(HandlerInfo  handlerInfo,String methodUrl, boolean isPost) {
		this.handlerInfo = handlerInfo;
		params = mergreParams();
		this.setMethodUrl(methodUrl);
		this.setPost(isPost);
	}

	
	private Map<String, String> mergreParams() {
		try{
			context.setCharacterEncoding(Constants.getEncoding());
		}catch(Exception e){}
		
		Map<String, String> map =  context.getParameterMap();
		map.putAll(handlerInfo.getUrlParams());
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

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}

	public boolean isPost() {
		return isPost;
	}

	public HandlerInfo getHandlerInfo() {
		return handlerInfo;
	}

	public void setMethodUrl(String methodUrl) {
		this.methodUrl = methodUrl;
	}

	public String getMethodUrl() {
		return methodUrl;
	}
	
	public Method getMethod(){
		return getHandlerInfo().getMethods().get(methodUrl);
	}
	
	public void recycle(){
		handlerInfo = null;
		params = null;
	}

}
