package com.lijian.devutils.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtils<T> {
	
	public Class<T> getCommonClass() {
		Class<T> entityClass = null;
		Type t = getClass().getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			entityClass = (Class<T>) p[0];
		}
		return entityClass;
	}
}
