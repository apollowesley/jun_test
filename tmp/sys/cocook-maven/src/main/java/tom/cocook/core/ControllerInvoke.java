package tom.cocook.core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Map;

import tom.cocook.annotation.Handler.Resource;
import tom.cocook.annotation.ContentType;
import tom.cocook.annotation.Response.Html;
import tom.cocook.annotation.Response.Json;
import tom.cocook.annotation.Response.Stream;
import tom.cocook.ext.BeanFactory;
import tom.cocook.ext.HTTPConverter;
import tom.cocook.ext.JavassistUtil;
import tom.cocook.interceptor.ActionInvocation;
import tom.kit.clazz.Converter;
import tom.kit.clazz.ReflectUtil;
import tom.kit.io.FileUtil;

/**
 * 一条URL 请求 的 处理过程  field,annotation,action,method
 */
public class ControllerInvoke {
	private ActionInvocation actionInvocation;
	private ControllerModel control;
	
	public ControllerInvoke(ActionInvocation _actionInvocation) {
		actionInvocation = _actionInvocation;
		control = _actionInvocation.getAppControl();
	}

	/* action通用处理,是继承,非拦截 */
	public boolean beforeControl() throws Exception {
		Method m = control.getClass().getMethod("before", RequestContext.class, Map.class);
		return (Boolean) m.invoke(control, actionInvocation.getRequestContext(), actionInvocation.getParaMap());
	}

	/* 得到所有的属性 */
	private Field[] loadField(Class<?> c) {
		return c.getDeclaredFields();
	}

	/*
	 * 解析属性, 根据annation 赋值 
	 */
	public void paserFieldAnotation() throws IllegalArgumentException, IllegalAccessException {
		if(control.isPaserField()) return;
		Field[] fields = loadField(control.getClass());
		for (Field field : fields) {
			//if(field.get(control) != null) continue; // 如果已经初始化
			Annotation[] anos = loadAnnotation(field);
			if (anos.length > 0) {
				Resource resource = field.getAnnotation(Resource.class);
				if(resource != null){
					try{
						field.setAccessible(true);  //无视 修饰符, 设置可见
						/* 设置属性的值 参数1-设置的实例化对象,参数2-设置的值 */
						field.set(control, BeanFactory.getInstance(resource.value()));
					}catch(Exception e){
						throw new CocookException(e);
					}
				}
			}
		}
		control.setPaserField(true);
	}

	/* 得到指定的annotation数组 */
	private <T> Annotation[] loadAnnotation(T obj) {
		return ((AnnotatedElement) obj).getAnnotations();
	}

	public Object parserControllerMethod() throws Exception {
		/* 得到对应methodName的方法 */
		String methodUrl=  actionInvocation.getMapping().getPath();
		Method me = actionInvocation.getMethod();
		Object obj = null;
		if(me==null) {
			actionInvocation.getRequestContext().error(404, 
				methodUrl+" not found on action["+actionInvocation.getAppControl().getClass().getSimpleName()+"]");
			return null;
		}
		
		if (beforeControl()) { // 预先处理
			Annotation[] anos = loadAnnotation(me);
			if (anos.length == 0) {
				obj = invoke(me);
			} else {
				obj = paserMethodAnnotation(me);
			}
		}
		
		return obj;
	}

	/**
	 * 对方法annotation进行解析
	 * 
	 * @param me
	 * @param control
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private Object paserMethodAnnotation(Method _me) throws IOException{

		/* passer HttpMethod annotation */
		Object obj = invoke(_me);

		/* passer ex annotation */
		obj = control.extAnnnotation(obj, _me, actionInvocation.getParaMap());

		if (obj == null) {
			return null;
		}
		
		/* passer Json annotation */
		if (_me.isAnnotationPresent(Json.class)) {
			actionInvocation.getRequestContext().printJSON(obj);
			return null;
		}
		
		/* passer Html annotation */
		if (_me.isAnnotationPresent(Html.class)) {
			actionInvocation.getRequestContext().printHTML(obj);
			return null;
		}

		/* passer Stream annotation */
		if (_me.isAnnotationPresent(Stream.class)) {
			paserStream(_me, obj);
			return null;
		}

		return obj;
	}

	private void paserStream(Method _me, Object _obj) throws IOException {
		if (_obj instanceof InputStream) {
			Stream in = _me.getAnnotation(Stream.class);
			if(in.value()==ContentType.Image){
				actionInvocation.getResponse().setContentType("image/jpeg");;
				
				FileUtil.copy((InputStream)_obj, actionInvocation.getResponse().getOutputStream());
				return;
			}
			
			FileUtil.copy((InputStream) _obj, actionInvocation.getResponse().getOutputStream());
		} else {
			throw new IllegalArgumentException("Reflect.paserMethodAnnotation: error return type,no inputStream found");
		}
	}

	/**
	 * 返回方法执行后的返回值
	 * 
	 * @param me
	 * @param control
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private Object invoke(Method _me) {
		String paramNames[] = null;
		Class<?>[] _class = _me.getParameterTypes();
		Object[] params = new Object[_class.length];
		for (int i = 0; i < _class.length; i++) {
			Class<?> clazz = _class[i];
			if (HTTPConverter.canConvertRequestContext(clazz)) {
				params[i] = HTTPConverter.coverterclass2RequestContext(clazz, actionInvocation);
			} else if (Converter.canConvertValue(clazz)) {
				if (paramNames == null) {
					paramNames = JavassistUtil.getMethodParamNames(control.getClass(), _me);
				}
				params[i] = Converter.coverterClass2Value(clazz, null, actionInvocation.getParaMap().get(paramNames[i]));

			} else {
				params[i] = ReflectUtil.mapToBean(actionInvocation.getParaMap(), clazz);
			}
		}

		return ReflectUtil.invokeMethod(control, _me, params);
	}

}
