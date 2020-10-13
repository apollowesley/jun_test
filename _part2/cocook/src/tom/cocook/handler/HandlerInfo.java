package tom.cocook.handler;

import java.lang.reflect.Method;
import java.util.HashMap;

import tom.cocook.annotation.Handler.Path;
import tom.cocook.core.ControllerModel;
import tom.kit.clazz.ReflectionUtils;
/**
 * @author panmg
 *	HandlerInfo 
 */
public class HandlerInfo {

	private final ControllerModel controller;

	private final HashMap<String, Method> methods = new HashMap<String, Method>();
	
	/*存储URL参数 /demo/{user} ==> {user->panmg}*/
	private HashMap<String, String> urlParams  = new HashMap<String, String>(); 


	public HandlerInfo(ControllerModel controller, boolean isCoc) {
		this.controller = controller;
		if(isCoc) return;
		searchMethod(controller);
	}

	/**
	 * @path 获取method
	 * @param handler
	 * @return
	 */
	private HashMap<String, Method> searchMethod(ControllerModel handler) {
		Class<?> clazz = handler.getClass();
		/**
		 * 获取 @Path
		 */
		while (clazz != Object.class) {
			Method[] meths = clazz.getDeclaredMethods();
			for (Method me : meths) {
				Path path = me.getAnnotation(Path.class);
				if (path != null) {
					methods.put(path.value(), me);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return methods;
	}
	
	/**
	 * Coc 根据methodName 匹配URL
	 * key--> className.methodName
	 * @param name
	 */
	public String searchMechodByName(String name){
		String key = controller.getClass().getSimpleName()+"."+name;
		if(methods.containsKey(key)) return key;
		Method me = ReflectionUtils.findMethod(controller.getClass(), name);
		if(me != null) methods.put(key, me);
		return key;
	}

	public ControllerModel getController() {
		return controller;
	}

	public HashMap<String, Method> getMethods() {
		return methods;
	}

	public void setUrlParams(HashMap<String, String> urlParams) {
		this.urlParams = urlParams;
	}

	public HashMap<String, String> getUrlParams() {
		return urlParams;
	}

	
	
}
