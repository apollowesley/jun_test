/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oschina.durcframework.easymybatis.util;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tanghc
 *
 */
public class EntityUtils {
	private static final String PREFIX_GET = "get";
	
	/**
	 * 将对象中的属性以键值对的形式放入map中
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> convertObj2Map(Object obj) {
		if (obj == null) {
			return Collections.emptyMap();
		}
		Method[] methods = obj.getClass().getDeclaredMethods();

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			for (Method method : methods) {
				String methodName = method.getName();

				if (methodName.startsWith(PREFIX_GET)) {
					String fieldName = buildFieldName(methodName);
					Object value = method.invoke(obj, new Object[] {});
					map.put(fieldName, value);
				}
			}
		} catch (Exception e) {
			return Collections.emptyMap();
		}

		return map;
	}
	
	// 构建列名
	public static String buildFieldName(String methodName) {
		return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);

	}

}
