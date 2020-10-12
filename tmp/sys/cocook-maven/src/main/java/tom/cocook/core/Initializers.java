package tom.cocook.core;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tom.cocook.annotation.Handler.Controller;
import tom.cocook.annotation.Handler.Initialize;
import tom.cocook.annotation.Handler.Interceptor;
import tom.cocook.config.Constants;
import tom.cocook.ext.BeanFactory;
import tom.cocook.ext.Scan;
import tom.cocook.handler.DefaultAnnotationHandler;
import tom.cocook.handler.DefaultNameHandler;
import tom.cocook.handler.Handler;
import tom.kit.StringUtil;
public class Initializers {
	private Logger logger = LoggerFactory.getLogger(Initializers.class);
	private String scan_package;
	
	private String handlers;
	
	public Initializers() throws ClassNotFoundException {
		this.scan_package = Constants.getScanPackage();
		this.handlers = Constants.getHandler();
		initHandler();
	}
	

	public void scan() throws IOException, ClassNotFoundException {
		for (String _package : scan_package.split(",")) {
			Set<Class<?>> set = Scan.scanpackage(_package);
			logger.info(_package + "::" + set );
			for(Class<?> clazz : set){
				if(bindController(clazz)) continue;	
				if(bindInterceptor(clazz)) continue;
				bindInitialize(clazz);
			}
		}
	}
	
	private void initHandler(){
		for(String handler: handlers.split(",")){
			Handler.handlers.put(handler,(Handler) BeanFactory.newInstance(handler));
		}
		if(Handler.handlers.size() == 0){
			Handler.handlers.put("tom.cocook.handler.DefaultAnnotationHandler", new DefaultAnnotationHandler());
		}
	}
	
	private boolean bindController(Class<?> clazz){
		Controller controller =  clazz.getAnnotation(Controller.class);
		if(controller == null) return false;
		String pattern = clazz.getSimpleName();
		if(pattern.indexOf("Controller")==-1){
			throw new CocookException("Controller class must be endWith Controller");
		}
		pattern = StringUtil.substringBefore(pattern, "Controller").toLowerCase();
		
		ControllerModel app = (ControllerModel)BeanFactory.newInstance(clazz);
		
		/*优先绑定 @path, 配置的DefaultAnnotationHandler,URL地址固定具体 
		 再绑定 DefaultNameHandler, URL 地址不固定 
		 后修改为根据 配置加载顺序, 如果未配置, 默认DefaultAnnotationHandler*/
		for(Handler handler: Handler.handlers.values()){
			if(handler instanceof DefaultAnnotationHandler){
				handler.collectApps(null, app);
				continue;
			}
			if(handler instanceof DefaultNameHandler){
				handler.collectApps("/"+pattern+"/*", app);
				continue;
			}
		}
		return true;
	}
	
	private boolean bindInterceptor(Class<?> clazz){
		Interceptor interceptor =  clazz.getAnnotation(Interceptor.class);
		if(interceptor == null) return false;
		
		Class<? extends Handler>[] interceptors = interceptor.value();
		for(Class<? extends Handler> Handlerclazz: interceptors){
			Handler handler = Handler.handlers.get(Handlerclazz.getName());
			handler.collectInterceptor(clazz);
		}
		return true;
	}
	
	private void bindInitialize(Class<?> clazz){
		Initialize initialize =  clazz.getAnnotation(Initialize.class);
		if(initialize != null) {
			BeanFactory.newInstance(clazz);
		}
	}

}
