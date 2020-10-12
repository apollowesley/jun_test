/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-3-8 下午12:43:09
 */
package com.absir.core.dyna;

import java.util.Map;

import com.absir.core.kernel.KernelLang.BreakException;

/**
 * @author absir
 * 
 */
@SuppressWarnings("rawtypes")
public interface DynaConvert<T> {

	/**
	 * @param obj
	 * @param name
	 * @param toClass
	 * @param breakException
	 * @return
	 * @throws BreakException
	 */
	public T to(Object obj, String name, Class<T> toClass, BreakException breakException) throws BreakException;

	/**
	 * @param map
	 * @param name
	 * @param toClass
	 * @param breakException
	 * @return
	 * @throws BreakException
	 */
	public T mapTo(Map map, String name, Class<T> toClass, BreakException breakException) throws BreakException;
}
