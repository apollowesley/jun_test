package tom.cocook.handler;

import java.util.ArrayList;
import java.util.HashMap;

import tom.cocook.core.ControllerModel;
import tom.cocook.ext.AntPathMatcher;
import tom.cocook.handler.CocookServlet.CurrentMethodUrl;
import tom.cocook.interceptor.Interceptor;

/**
 * @author panmg
 * Handler接口 记录handlers(defaultNamehandler, DefaultAnnotationHandler)
 * 每个 Handler 记录 各自的HandlerInfo, interceptors, 针对handler的单例
 */
public interface Handler {
	
	public static AntPathMatcher matcher = new AntPathMatcher();
	
	public static HashMap<String,Handler> handlers = new HashMap<String, Handler>();
	
	public abstract HandlerController proxyApp();

	public HashMap<String, HandlerInfo> getApps();

	public ArrayList<Interceptor> getInterceptors();

	public abstract void collectApps(String pattern, ControllerModel controller);

	public abstract void collectInterceptor(Class<?> clazz);
	
	public HandlerInfo match(String url, CurrentMethodUrl methodUrl);
	
	

}
