package org.nature.framework.helper;

import java.lang.reflect.Field;

import org.nature.framework.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IocHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);
	public static void injectService(Object targetObject, Class<?> targetClass) {
		Field[] fields = targetClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if(field.isAnnotationPresent(Inject.class)){
				Class<?> cls = field.getType();
				Object value = ServiceHelper.getService(cls);
				try {
					field.set(targetObject, value);
				} catch (IllegalArgumentException e) {
					LOGGER.error(e.toString());
				} catch (IllegalAccessException e) {
					LOGGER.error(e.toString());
				}
			}
		}
		
	}

}
