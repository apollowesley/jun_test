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

public class DefaultNameHandler implements Handler{
	private Logger logger = LoggerFactory.getLogger(DefaultNameHandler.class);
	
	/*xxxController[xxx/*] --> HandlerInfo, value只有一个HandlerInfo
	 *所有的controller的Methods*/
	private final HashMap<RequestMapping, HandlerInfo> apps = new HashMap<RequestMapping, HandlerInfo>();

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
		return (Controller)proxy.bind(handlerController, interceptors);
	
	}

	@Override
	public void collectApps(String _pattern,ControllerModel _app){
		HandlerInfo handlerInfo = new HandlerInfo(_app, true);
		/*一个controller 只对应 一个handlerMapping*/
		Handler handler = handlers.get(DefaultAnnotationHandler.class.getName());
		if(handler != null){
			for(HandlerInfo handleriinfo : handler.getApps().values()){
				if(handleriinfo.getController() == _app){
					return;
				}
			}
		}
		
		apps.put(new RequestMapping(_pattern, null), handlerInfo);  // classname_methodname 只有同一个requestmapping
		logger.info("Mapped  URL path ["+ _pattern +"] onto Controller "+ _app.getClass().getSimpleName());
	}

	@Override
	public void collectInterceptor(Class<?> clazz) {
		Interceptor interceptor =  (Interceptor) BeanFactory.newInstance(clazz);
		interceptors.add(interceptor);
		logger.info( clazz.getSimpleName() +" Binding onto "+ this.getClass().getName());
		
	}

	/*解析cocNameHandler, 第一次需要解析methodName的名字*/
	@Override
	public HandlerInfo match(RequestMapping _mapping, RequestContext context) {
		// /user/add --> /user/*  
		String pattern =  _mapping.getPath().substring(0, _mapping.getPath().lastIndexOf("/")+1)+"*";
		RequestMapping mapping = new RequestMapping(pattern, null);
		/*COC namehandler 没有区分 get与post, 因为没有@path 注解*/
		HandlerInfo handlerInfo = apps.get(mapping);
		if(handlerInfo == null) return null;
		String methodName =  matcher.extractPathWithinPattern(pattern, _mapping.getPath());
		/*搜索method by name 返回 [className.methodname]*/
		mapping  = handlerInfo.searchMechodByName(methodName);
		_mapping.setMethod(mapping.getMethod());
		_mapping.setPath(mapping.getPath());
		return handlerInfo;
	}
	
	
	public HandlerInfo match2(RequestMapping _mapping, RequestContext context) {
		for(RequestMapping mapping: apps.keySet()){
			String pattern = mapping.getPath();
			if(matcher.match(pattern, _mapping.getPath())){
				/*COC namehandler 没有区分 get与post, 因为没有@path 注解*/
				HandlerInfo handlerInfo = apps.get(mapping);
				String methodName =  matcher.extractPathWithinPattern(pattern, _mapping.getPath());
				/*搜索method by name 返回 [className.methodname]*/
				mapping  = handlerInfo.searchMechodByName(methodName);
				_mapping.setMethod(mapping.getMethod());
				_mapping.setPath(mapping.getPath());
				return handlerInfo;
			}
		}
		return null;
		
	}
	
	

}
