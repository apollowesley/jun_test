package tom.cocook.handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import tom.cocook.core.ControllerModel;
import tom.cocook.core.RequestContext;
import tom.cocook.ext.AntPathMatcher;
import tom.cocook.interceptor.Interceptor;

/**
 * @author panmg
 * Handler接口 记录handlers(defaultNamehandler, DefaultAnnotationHandler)
 * 每个 Handler 记录 各自的HandlerInfo, interceptors, 针对handler的单例
 */
public interface Handler {
	
	public static AntPathMatcher matcher = new AntPathMatcher();
	
	public static LinkedHashMap<String,Handler> handlers = new LinkedHashMap<String, Handler>();
	
	public abstract Controller proxyApp();

	public Map<RequestMapping, HandlerInfo> getApps();

	public List<Interceptor> getInterceptors();

	public abstract void collectApps(String pattern, ControllerModel controller);

	public abstract void collectInterceptor(Class<?> clazz);
	
	public HandlerInfo match(RequestMapping mapping, RequestContext context);
	
	

}
