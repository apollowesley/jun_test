package org.apache.center.web.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.center.model.StatusCode;
import org.apache.playframework.util.PackageUtils;

public class CodeUtils {

	public static Map<String, TreeMap<String, String>> codeMap = new HashMap<String, TreeMap<String, String>>();
	
	static {
		Set<Class<?>> set = PackageUtils.getClasses("org.apache.center.model.enums");
		for (Class<?> class1 : set) {
			Method m;
			try {
				m = class1.getDeclaredMethod("values", null);
				Object[] result = (Object[]) m.invoke(class1, null);
				 for (Object object : result) {
					Method mapMethod = object.getClass().getDeclaredMethod("getData", null);
					mapMethod.setAccessible(true);
					Object mapObject = mapMethod.invoke(object, null);
					
					Method keyMethod = object.getClass().getDeclaredMethod("getKey", null);
					keyMethod.setAccessible(true);
					Object keyObject = keyMethod.invoke(object, null);
					codeMap.put(keyObject.toString(), (TreeMap<String, String>)mapObject);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}  
		}
	}
	
	public static List<StatusCode> getData(String key) {
		List<StatusCode> codes = new ArrayList<StatusCode>();
		TreeMap<String, String> map = codeMap.get(key);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			StatusCode code = new StatusCode();
			code.setNodeKey(entry.getKey());
			code.setNodeValue(entry.getValue());
			codes.add(code);
		}
		return codes;
	}
	
}
