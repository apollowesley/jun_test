package tom.cocook.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import tom.cocook.annotation.Handler.Controller;
import tom.cocook.annotation.Handler.Initialize;
import tom.cocook.annotation.Handler.Interceptor;
import tom.cocook.config.Constants;
import tom.cocook.ext.BeanFactory;
import tom.cocook.handler.DefaultAnnotationHandler;
import tom.cocook.handler.DefaultNameHandler;
import tom.cocook.handler.Handler;
import tom.kit.StringUtil;
public class Initializers {

	private String scan_package;
	
	private String handlers;
	
	public Initializers() throws ClassNotFoundException {
		this.scan_package = Constants.getScanPackage();
		this.handlers = Constants.getHandler();
		initHandler();
	}
	

	public void scan() throws IOException, ClassNotFoundException {
		for (String _package : scan_package.split(",")) {
			//getResources 之后返回一条记录, 看源码
			//Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(_package.replace(".", "/"));
			URL url = Thread.currentThread().getContextClassLoader().getResource(_package.replace(".", "/"));
			String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
			scanpackage(_package, filePath);
		}
	}

	private void scanpackage(String _packagename, String _filepath) throws ClassNotFoundException {
		File dir = new File(_filepath);
		if (!dir.exists() || !dir.isDirectory())	return;
		File[] dirs = dir.listFiles(new java.io.FileFilter() {
			@Override
			public boolean accept(File file) {
				return (file.isDirectory() || file.getName().endsWith(".class"));
			}
		});

		for (File file : dirs) {
			if (file.isDirectory()) {
				scanpackage(_packagename + "." + file.getName(), file.getAbsolutePath());
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(_packagename + "." + className);
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
		
		/*优先绑定 @path, 配置的DefaultAnnotationHandler,URL地址固定具体*/ 
		for(Handler handler: Handler.handlers.values()){
			if(handler instanceof DefaultAnnotationHandler){
				handler.collectApps(null, app);
				break;
			}
		}
		/*再绑定 DefaultNameHandler, URL 地址不固定 */
		for(Handler handler: Handler.handlers.values()){
			if(handler instanceof DefaultNameHandler){
				handler.collectApps("/"+pattern+"/*", app);
				break;
			}
		}
		
		return true;
	}
	
	private boolean bindInterceptor(Class<?> clazz){
		Interceptor interceptor =  clazz.getAnnotation(Interceptor.class);
		if(interceptor == null) return false;
		
		Class<? extends Handler>[] interceptors = interceptor.handler();
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
