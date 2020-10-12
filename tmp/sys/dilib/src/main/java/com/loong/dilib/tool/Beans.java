package com.loong.dilib.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Bean工具类
 *
 * @author 张成轩
 */
public class Beans {

	/**
	 * 反射转换Bean对象
	 * 
	 * @param bean Bean对象
	 * @return Map集合
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convert(Object bean) {

		if (bean instanceof Map)
			return (Map<String, Object>) bean;
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (bean == null)
			return map;
		// 反射获取类
		Class<?> clas = bean.getClass();
		// 遍历父节点
		while (clas != null && !Object.class.equals(clas)) {
			// 获取变量
			Field[] fields = clas.getDeclaredFields();
			for (Field f : fields) {
				// 判断变量类型
				int modif = f.getModifiers();
				if (Modifier.isStatic(modif))
					continue;
				if (Modifier.isFinal(modif))
					continue;
				f.setAccessible(true);
				Object value = null;
				try {
					// 获取变量值
					value = f.get(bean);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				// 添加到集合中
				map.put(f.getName(), value == null ? null : value.toString());
			}
			clas = clas.getSuperclass();
		}
		return map;
	}
}
