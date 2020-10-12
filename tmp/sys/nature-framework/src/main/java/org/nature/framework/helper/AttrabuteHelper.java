package org.nature.framework.helper;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.nature.framework.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AttrabuteHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(AttrabuteHelper.class);
	public static void putInAttrabute(HttpServletRequest request, Object ctrlObj, Class<?> ctrlCls) {
		Field[] fields = ctrlCls.getFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Inject.class)) {
				continue;
			}
			String key = field.getName();
			try {
				Object obj = field.get(ctrlObj);
				if(obj!=null){
						request.setAttribute(key, obj);
				}
			} catch (IllegalArgumentException e) {
				LOGGER.error(e.toString());
			} catch (IllegalAccessException e) {
				LOGGER.error(e.toString());
			}
		}
		
	}
	
public static Map<String, Object> putInRootMap(HttpServletRequest request,Object ctrlObj, Class<?> ctrlCls) {
		ServletContext servletContext = request.getServletContext();
		Enumeration<String> attributeNames = servletContext.getAttributeNames();
		HashMap<String, Object> rootMap = new HashMap<String, Object>();
		while (attributeNames.hasMoreElements()) {
			String name = attributeNames.nextElement();
			Object value = servletContext.getAttribute(name);
			rootMap.put(name, value);
		}
		
		Enumeration<String> sessionNames = request.getSession().getAttributeNames();
		while (sessionNames.hasMoreElements()) {
			String name = sessionNames.nextElement();
			Object value = request.getSession().getAttribute(name);
			rootMap.put(name, value);
		}
		
		Field[] fields = ctrlCls.getFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Inject.class)) {
				continue;
			}
			String key = field.getName();
			try {
				Object obj = field.get(ctrlObj);
				if(obj!=null){
						rootMap.put(key, obj);
				}
			} catch (IllegalArgumentException e) {
				LOGGER.error(e.toString());
			} catch (IllegalAccessException e) {
				LOGGER.error(e.toString());
			}
		}
		return rootMap;
	}

}
