package tom.cocook.handler;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import tom.cocook.core.ControllerModel;
import tom.cocook.core.RequestContext;
import tom.cocook.ext.BeanFactory;
import tom.cocook.interceptor.Interceptor;
import tom.cocook.interceptor.ProxyHandler;

public class DefaultAnnotationHandler implements tom.cocook.handler.Handler {
	private Logger logger = LoggerFactory.getLogger(DefaultAnnotationHandler.class);

	/*@path-->handlerInfo, 每个@path都会对于一个handlerInfo(value重复多个handlerinfo)
	 * handlerInfo中有所有controller 的方法*/
	private final HashMap<RequestMapping, HandlerInfo> apps = new HashMap<RequestMapping, HandlerInfo>();
	
	// 存储 /user/{id} 的映射关系
	private final HashMap<RequestMapping, HandlerInfo> fuz = new HashMap<RequestMapping, HandlerInfo>();

	private final ArrayList<Interceptor> interceptors = new ArrayList<Interceptor>();
	
	@Override
	public HashMap<RequestMapping, HandlerInfo> getApps() {
		return apps;
	}

	@Override
	public ArrayList<Interceptor> getInterceptors() {
		return interceptors;
	}

	@Override
	public Controller proxyApp() {
		Controller handlerController = new DefaultController();
		if(interceptors.isEmpty()) return handlerController;
		ProxyHandler proxy = new ProxyHandler();
		return (Controller)proxy.bind(handlerController,interceptors);
	}

	@Override
	public void collectApps(String _pattern,ControllerModel _app) {
		/*收集所有的path-->method*/
		HandlerInfo handlerInfo = new HandlerInfo(_app, false);
		/*一个controller 只对应 一个handlerMapping*/
		Handler handler = handlers.get(DefaultNameHandler.class.getName());
		if(handler != null){
			for(HandlerInfo handleriinfo :  handler.getApps().values()){
				if(handleriinfo.getController() == _app){
					return;
				}
			}
		}
		
		for(RequestMapping key : handlerInfo.getMethods().keySet()){
			apps.put(key, handlerInfo);
			if(key.getPath().indexOf("{") != -1){
				fuz.put(key, handlerInfo);
			}
			logger.info("Mapped  URL path ["+ key +"] onto Controller "+ _app.getClass().getSimpleName());
		}
	}

	@Override
	public void collectInterceptor(Class<?> clazz) {
		Interceptor interceptor =  (Interceptor) BeanFactory.newInstance(clazz);
		interceptors.add(interceptor);
		logger.info( clazz.getSimpleName() +" Binding onto "+ this.getClass().getName());
	}

	/*解析@path("/demo/{user}"|| "demo/panmg")*/
	@Override
	public HandlerInfo match(RequestMapping _mapping, RequestContext context) {
		//直接匹配到 path
		HandlerInfo handlerInfo =  apps.get(_mapping);
		if(handlerInfo != null) return handlerInfo;
		
		//解析 [demo/{user}] URL路径, 返回map[user->${user}]
		for(RequestMapping mapping: fuz.keySet()){
			HashMap<String, String> urlMap = new HashMap<String, String>();
			if(matcher.doMatch(mapping.getPath(), _mapping.getPath(), true, urlMap)){
				context.setUrlMap(urlMap);
				handlerInfo = fuz.get(mapping);
				_mapping.setPath(mapping.getPath());
				_mapping.setMethod(mapping.getMethod());
				return handlerInfo;
			}
		}
		
		return null;
	}
	
	public HandlerInfo match2(RequestMapping _mapping, RequestContext context) {
		//直接匹配到 path
		HandlerInfo handlerInfo =  apps.get(_mapping);
		if(handlerInfo != null) return handlerInfo;
		
		//解析 [demo/{user}] URL路径, 返回map[user->${user}]
		for(RequestMapping mapping: apps.keySet()){
			HashMap<String, String> urlMap = new HashMap<String, String>();
			if(matcher.doMatch(mapping.getPath(), _mapping.getPath(), true, urlMap)){
				context.setUrlMap(urlMap);
				handlerInfo = apps.get(mapping);
				_mapping.setPath(mapping.getPath());
				_mapping.setMethod(mapping.getMethod());
				return handlerInfo;
			}
		}
		
		return null;
	}
	
	
}
