package org.nature4j.framework.helper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nature4j.framework.annotation.Ask;
import org.nature4j.framework.annotation.Ctrl;
import org.nature4j.framework.annotation.Interceptors;
import org.nature4j.framework.annotation.Validator;
import org.nature4j.framework.bean.CtrlBean;
import org.nature4j.framework.enums.AskType;
import org.nature4j.framework.interceptor.NatureInterceptor;
import org.nature4j.framework.interceptor.ShowLogInterceptor;
import org.nature4j.framework.interceptor.TokenInterceptor;
import org.nature4j.framework.util.ClassUtil;
import org.nature4j.framework.validator.NatureValidator;
import org.nature4j.framework.validator.ValidatorInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CtrlHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(CtrlHelper.class);

	private static Map<String, CtrlBean> ctrlMap = new HashMap<String, CtrlBean>();

	public static void initCtrls() {
		Set<Class<?>> ctrlClassSet = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage(), Ctrl.class);
		for (Class<?> cls : ctrlClassSet) {
			analyze(cls);
		}
	}

	public static void analyze(Class<?> cls) {
		CtrlBean ctrlBean = null;
		String namespace = "";
		Ctrl ctrl = cls.getAnnotation(Ctrl.class);
		namespace = ctrl.namespace();
		Method[] methods = cls.getMethods();
		for (Method method : methods) {
            method.setAccessible(true);
            String url = "";
            if (method.isAnnotationPresent(Ask.class)) {
                Ask action = method.getAnnotation(Ask.class);
                url = action.url();
                if ("".equals(url)) {
                    url = "/" + method.getName();
                }
                ctrlBean = new CtrlBean(cls, method, namespace, url, setInterceptorIntoList(cls, method),setValidatorIntoList(method),action.type());
                String key = namespace + url;
                key = key.replaceAll("//+", "/");
                ctrlMap.put(key, ctrlBean);
            } else {
                continue;
            }
            LOGGER.debug(ctrlBean.toString());
        }
	}

	private static List<NatureValidator> setValidatorIntoList(Method method) {
		List<NatureValidator> inters = new ArrayList<NatureValidator>();
		if (method.isAnnotationPresent(Validator.class)) {
			Validator validator = method.getAnnotation(Validator.class);
			Class<? extends NatureValidator>[] values = validator.value();
			for (Class<? extends NatureValidator> value : values) {
				NatureValidator natureValidator = ValidatorHelper.getValidator(value);
				inters.add(natureValidator);
			}
		}
		return inters;
	}

	private static List<NatureInterceptor> setInterceptorIntoList(Class<?> cls, Method method) {
		List<NatureInterceptor> inters = new ArrayList<NatureInterceptor>();
		// 内部拦截器
		inters.add(new ShowLogInterceptor());
		inters.add(new TokenInterceptor());
		inters.add(new ValidatorInterceptor());
		Map<Class<?>, NatureInterceptor> globleInterceptorMap = InterceptorHelper.getGlobleInterceptorMap();
		Set<Class<?>> keySet = globleInterceptorMap.keySet();
		for (Class<?> globleInterceptorClass : keySet) {
			NatureInterceptor globleInterceptor = globleInterceptorMap.get(globleInterceptorClass);
			inters.add(globleInterceptor);
		}
		if (cls.isAnnotationPresent(Interceptors.class)) {
			Interceptors interceptorAnnotation = cls.getAnnotation(Interceptors.class);
			Class<? extends NatureInterceptor>[] classInterceptor = interceptorAnnotation.value();
			for (Class<? extends NatureInterceptor> clsInterceptor : classInterceptor) {
				NatureInterceptor inter = InterceptorHelper.getInterceptor(clsInterceptor);
				inters.add(inter);
			}
		}
		if (method.isAnnotationPresent(Interceptors.class)) {
			Interceptors interceptorAnnotation = method.getAnnotation(Interceptors.class);
			Class<? extends NatureInterceptor>[] classInterceptor = interceptorAnnotation.value();
			for (Class<? extends NatureInterceptor> clsInterceptor : classInterceptor) {
				NatureInterceptor inter = InterceptorHelper.getInterceptor(clsInterceptor);
				inters.add(inter);
			}
		}
		Collections.sort(inters, new Comparator<NatureInterceptor>() {
			public int compare(NatureInterceptor o1, NatureInterceptor o2) {
				return o2.level() - o1.level();
			}
		});
		return inters;
	}

	public static void clear() {
		ctrlMap.clear();
	}

	public static CtrlBean getCtrlBean(String targetKey, String askType){
		CtrlBean ctrlBean = ctrlMap.get(targetKey);
		if (ctrlBean!=null) {
			if (ctrlBean.getAskType()!=AskType.ALL&&!ctrlBean.getAskType().toString().equals(askType)) {
				ctrlBean = null;
			}
		}
		return ctrlBean;
	}
	
	public static Map<String, CtrlBean> getCtrlMap() {
		return ctrlMap;
	}

	

}
