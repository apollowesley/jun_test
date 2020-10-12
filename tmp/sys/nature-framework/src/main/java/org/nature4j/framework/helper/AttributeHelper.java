package org.nature4j.framework.helper;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.nature4j.framework.annotation.Inject;
import org.nature4j.framework.core.NatureCtrl;
import org.nature4j.framework.core.NatureMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributeHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(AttributeHelper.class);

	public static void putInAttrabute(HttpServletRequest request,NatureMap responseParams) {
		Set<Entry<String, Object>> entrySet = responseParams.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			request.setAttribute(entry.getKey(), entry.getValue());
		}

	}

	public static Map<String, Object> putInRootMap(HttpServletRequest request,NatureMap responseParams) {
		Enumeration<String> attributeNames = request.getAttributeNames();
		HashMap<String, Object> rootMap = new HashMap<String, Object>();
		while (attributeNames.hasMoreElements()) {
			String name = attributeNames.nextElement();
			Object value = request.getAttribute(name);
			rootMap.put(name, value);
		}
		Enumeration<String> sessionNames = request.getSession().getAttributeNames();
		while (sessionNames.hasMoreElements()) {
			String name = sessionNames.nextElement();
			Object value = request.getSession().getAttribute(name);
			rootMap.put(name, value);
		}
		rootMap.putAll(responseParams);
		rootMap.remove("token");
		return rootMap;
	}

	public static void putInRequestParams(NatureMap responseParams, NatureCtrl ctrlObj, Class<?> ctrlCls) {
		Field[] fields = ctrlCls.getFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Inject.class)) {
				continue;
			}
			String key = field.getName();
			try {
				Object obj = field.get(ctrlObj);
				if (obj != null) {
					responseParams.put(key, obj);
				}
			} catch (IllegalArgumentException e) {
				LOGGER.error(e.toString());
			} catch (IllegalAccessException e) {
				LOGGER.error(e.toString());
			}
		}
	}

}
