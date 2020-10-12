package org.nature.framework.helper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nature.framework.annotation.Ask;
import org.nature.framework.annotation.Ctrl;
import org.nature.framework.annotation.Interceptors;
import org.nature.framework.bean.CtrlBean;
import org.nature.framework.interceptor.NatureInterceptor;
import org.nature.framework.interceptor.TokenInterceptor;
import org.nature.framework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CtrlHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(CtrlHelper.class);
	
	public static Map<String, CtrlBean> ctrlMap = new HashMap<String,CtrlBean>();
	public static void initCtrls(){
		Set<Class<?>> ctrlClassSet = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage(), Ctrl.class);
		CtrlBean ctrlBean = null ;
		for (Class<?> cls : ctrlClassSet) {
			String namespace = "";
			Ctrl ctrl = cls.getAnnotation(Ctrl.class);
			namespace = ctrl.namespace();
			Method[] methods = cls.getMethods();
			for (Method method : methods) {
				method.setAccessible(true);
				String url = "";
				if(method.isAnnotationPresent(Ask.class)){
					Ask action = method.getAnnotation(Ask.class);
					url = action.url();
					if("".equals(url)){
						url = "/"+method.getName();
					}
					List<NatureInterceptor> inters = setInterceptorIntoList(cls, method);
					ctrlBean = new CtrlBean(cls, method,namespace,url,inters);
					String key = namespace+url;
					key = key.replaceAll("//+", "/");
					ctrlMap.put(key, ctrlBean);
				}else {
					continue;
				}
				LOGGER.debug(ctrlBean.toString());
			}
		}
	}
	private static List<NatureInterceptor> setInterceptorIntoList(Class<?> cls, Method method) {
		List<NatureInterceptor> inters = new ArrayList<NatureInterceptor>();
		inters.add(new TokenInterceptor());
		Map<Class<?>, NatureInterceptor> globleInterceptorMap = InterceptorHelper.getGlobleInterceptorMap();
		Set<Class<?>> keySet = globleInterceptorMap.keySet();
		for (Class<?> globleInterceptorClass : keySet) {
			NatureInterceptor globleInterceptor = globleInterceptorMap.get(globleInterceptorClass);
			inters.add(globleInterceptor);
		}
		if(cls.isAnnotationPresent(Interceptors.class)){
			Interceptors interceptorAnnotation = cls.getAnnotation(Interceptors.class);
			Class<? extends NatureInterceptor>[] classInterceptor = interceptorAnnotation.value();
			for (Class<? extends NatureInterceptor> clsInterceptor : classInterceptor) {
				NatureInterceptor inter = InterceptorHelper.getInterceptor(clsInterceptor);
				inters.add(inter);
			}
		}
		if(method.isAnnotationPresent(Interceptors.class)){
			Interceptors interceptorAnnotation = method.getAnnotation(Interceptors.class);
			Class<? extends NatureInterceptor>[] classInterceptor = interceptorAnnotation.value();
			for (Class<? extends NatureInterceptor> clsInterceptor : classInterceptor) {
				NatureInterceptor inter = InterceptorHelper.getInterceptor(clsInterceptor);
				inters.add(inter);
			}
		}
		return inters;
	}
	
}
