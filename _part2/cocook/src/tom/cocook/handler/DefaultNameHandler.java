package tom.cocook.handler;

import java.util.ArrayList;
import java.util.HashMap;


import tom.cocook.core.ControllerModel;
import tom.cocook.ext.BeanFactory;
import tom.cocook.handler.CocookServlet.CurrentMethodUrl;
import tom.cocook.interceptor.Interceptor;
import tom.cocook.interceptor.ProxyHandler;
import tom.cocook.log.Logger;
import tom.cocook.log.LoggerFactory;

public class DefaultNameHandler implements Handler{
	private Logger logger = LoggerFactory.getLog(DefaultNameHandler.class);
	
	/*xxxController[xxx/*] --> HandlerInfo, value只有一个HandlerInfo
	 *所有的controller的Methods*/
	private final HashMap<String, HandlerInfo> apps = new HashMap<String, HandlerInfo>();

	private final ArrayList<Interceptor> interceptors = new ArrayList<Interceptor>();
	
	@Override
	public HashMap<String, HandlerInfo> getApps() {
		return apps;
	}

	@Override
	public ArrayList<Interceptor> getInterceptors() {
		return interceptors;
	}

	@Override
	public HandlerController proxyApp() {
		HandlerController handlerController = new DefaultHandlerController();
		if(interceptors.isEmpty()) return handlerController;
		ProxyHandler proxy = new ProxyHandler();
		return (HandlerController)proxy.bind(handlerController, interceptors);
	
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
		
		apps.put(_pattern, handlerInfo);
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
	public HandlerInfo match(String _url, CurrentMethodUrl _methodUrl) {

		for(String pattern: apps.keySet()){
			if(matcher.match(pattern, _url)){
				HandlerInfo handlerInfo = apps.get(pattern);
				String methodName =  matcher.extractPathWithinPattern(pattern, _url);
				/*搜索method by name 返回 [className.methodname]*/
				methodName = handlerInfo.searchMechodByName(methodName);
				_methodUrl.setMethodUrl(methodName);
				return handlerInfo;
			}
		}
		return null;
	
	}
	
	

}
