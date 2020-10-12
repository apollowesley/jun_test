/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-10-21 下午2:10:45
 */
package com.absir.appserv.system.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.absir.appserv.system.context.value.CaFilter;
import com.absir.core.util.UtilAccessor;
import com.absir.core.util.UtilAccessor.Accessor;

/**
 * @author absir
 * 
 */
public class HelperAccessor {

	/** TRANSIENT_MODIFIER */
	public static final int TRANSIENT_MODIFIER = Modifier.STATIC | Modifier.FINAL | Modifier.TRANSIENT;

	/**
	 * @param field
	 * @return
	 */
	public static boolean isAccessor(Field field) {
		return (field.getModifiers() & TRANSIENT_MODIFIER) == 0 && field.getAnnotation(CaFilter.class) == null;
	}

	/**
	 * @param cls
	 * @return
	 */
	public static List<Field> getFields(Class<?> cls) {
		List<Field> fields = new ArrayList<Field>();
		List<Field> fieldSopes = new ArrayList<Field>();
		while (cls != null) {
			for (Field field : cls.getDeclaredFields()) {
				if (isAccessor(field)) {
					field.setAccessible(true);
					fieldSopes.add(field);
				}
			}

			fields.addAll(0, fieldSopes);
			fieldSopes.clear();
			cls = cls.getSuperclass();
		}

		return fields;
	}

	/**
	 * @param cls
	 * @return
	 */
	public static List<Accessor> getXlsAccessors(Class<?> cls) {
		List<Accessor> accessors = new ArrayList<Accessor>();
		for (Field field : getFields(cls)) {
			accessors.add(UtilAccessor.getAccessor(cls, field));
		}

		return accessors;
	}
}
