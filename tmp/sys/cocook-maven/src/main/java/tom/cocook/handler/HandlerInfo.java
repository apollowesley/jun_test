package tom.cocook.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import tom.cocook.annotation.Handler.ExecMethod;
import tom.cocook.annotation.Handler.Path;
import tom.cocook.annotation.Handler.Resource;
import tom.cocook.core.CocookException;
import tom.cocook.core.ControllerModel;
import tom.cocook.ext.BeanFactory;
import tom.kit.clazz.ReflectUtil;
/**
 * @author panmg
 *	HandlerInfo 
 */
public class HandlerInfo {

	private final ControllerModel controller;

	private final ConcurrentHashMap<RequestMapping, Method> methods = new ConcurrentHashMap<RequestMapping, Method>();
	
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
	private ConcurrentHashMap<RequestMapping, Method> searchMethod(ControllerModel handler) {
		
		Class<?> clazz = handler.getClass();
		/**
		 * 获取 @Path
		 */
		while (clazz != Object.class) {
			Method[] meths = clazz.getDeclaredMethods();
			for (Method me : meths) {
				Path path = me.getAnnotation(Path.class);
				if (path != null) {
					RequestMapping maping = new RequestMapping(path.value(), path.method());
					methods.put(maping, me);
				}
				ExecMethod exec = me.getAnnotation(ExecMethod.class);
				if(exec != null){
					paserFieldAnotation(handler);
					BeanFactory.invoke(me, handler);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return methods;
	}
	
	
	public void paserFieldAnotation(ControllerModel handler) {
		Field[] fields = handler.getClass().getDeclaredFields();
		for (Field field : fields) {
			Annotation[] anos = loadAnnotation(field);
			if (anos.length > 0) {
					Resource resource = field.getAnnotation(Resource.class);
					field.setAccessible(true);  //无视 修饰符, 设置可见
					if(resource != null){
						try{
							/* 设置属性的值 参数1-设置的实例化对象,参数2-设置的值 */
							field.set(handler, BeanFactory.getInstance(resource.value()));
						}catch(Exception e){
							throw new CocookException(e);
						}
					}
			}
		}
	}
	
	
	/* 得到指定的annotation数组 */
	private <T> Annotation[] loadAnnotation(T obj) {
		return ((AnnotatedElement) obj).getAnnotations();
	}
	
	/**
	 * Coc 根据methodName 匹配URL
	 * key--> className.methodName
	 * @param name
	 */
	public RequestMapping searchMechodByName(String name){
		String key = controller.getClass().getSimpleName()+"."+name;
		RequestMapping mapping =  new RequestMapping(key, null);
		if(methods.containsKey(mapping)) return mapping;
		
		Method me = ReflectUtil.findMethod(controller.getClass(), name);
		if(me != null) methods.put(mapping, me);
		return mapping;
	}

	public ControllerModel getController() {
		return controller;
	}

	public ConcurrentHashMap<RequestMapping, Method> getMethods() {
		return methods;
	}
	
}
