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

public class DefaultAnnotationHandler implements tom.cocook.handler.Handler {
	private Logger logger = LoggerFactory.getLog(DefaultAnnotationHandler.class);

	/*@path-->handlerInfo, 每个@path都会对于一个handlerInfo(value重复多个handlerinfo)
	 * handlerInfo中有所有controller 的方法*/
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
		return (HandlerController)proxy.bind(handlerController,interceptors);
	}

	@Override
	public void collectApps(String _pattern,ControllerModel _app) {
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
		
		for(String key : handlerInfo.getMethods().keySet()){
			apps.put(key, handlerInfo);
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
	public HandlerInfo match(String _url, CurrentMethodUrl _methodurl) {
		for(String pattern: apps.keySet()){
			//解析 [demo/{user}] URL路径, 返回map[user->${user}]
			HashMap<String, String> urlParma = new HashMap<String, String>();
			if(matcher.doMatch(pattern, _url, true, urlParma)){
				HandlerInfo handlerInfo =  apps.get(pattern);
				handlerInfo.setUrlParams(urlParma);
				_methodurl.setMethodUrl(pattern);
				return handlerInfo;
			}
		}
		return null;
	
	}
	
	
}
