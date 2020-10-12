/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-9-10 上午11:06:13
 */
package com.absir.appserv.dyna;

import java.lang.reflect.Type;
import java.util.Date;

import com.absir.core.dyna.DynaBinder;
import com.absir.core.kernel.KernelClass;

/**
 * @author absir
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DynaBinderUtils extends DynaBinder {

	/**
	 * @param cls
	 * @return
	 */
	public static boolean is(Class cls) {
		return KernelClass.isBasicClass(cls) || Date.class.isAssignableFrom(cls) || Enum.class.isAssignableFrom(cls);
	}

	/**
	 * @param obj
	 * @param toClass
	 * @return
	 */
	public static <T> T to(Object obj, Class<T> toClass) {
		if (obj == null || toClass.isAssignableFrom(obj.getClass())) {
			return (T) obj;
		}

		/*
		 * if (toClass.isAssignableFrom(String.class)) { if
		 * (!is(obj.getClass())) { return (T) HelperJson.encodeNull(obj); }
		 * 
		 * } else { if (obj instanceof String && !is(toClass)) { return
		 * HelperJson.decodeNull((String) obj, toClass); } }
		 */

		return DynaBinder.to(obj, toClass);
	}

	/**
	 * @param obj
	 * @param toType
	 */
	public static Object to(Object obj, Type toType) {
		if (obj == null) {
			return null;
		}

		if (toType instanceof Class) {
			return to(obj, (Class) toType);
		}

		if (obj instanceof String) {
			// return HelperJson.decodeNull((String) obj, toType);
		}

		return DynaBinder.INSTANCE.binder(obj, null, toType);
	}
}
