/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-11-14 下午1:31:09
 */
package com.absir.core.util;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author absir
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UtilAbsir {

	/**
	 * @param id
	 * @param tokenMap
	 */
	public static Object getToken(Object id, Map<?, ?> tokenMap) {
		Object token = tokenMap.get(id);
		if (token == null) {
			synchronized (tokenMap) {
				token = tokenMap.get(id);
				if (token == null) {
					token = new Object();
					((Map) tokenMap).put(id, token);
				}
			}
		}

		return token;
	}

	/**
	 * @author absir
	 * 
	 * @param <T>
	 */
	public static class AtomicObject<T> {

		/** locking */
		AtomicBoolean locking = new AtomicBoolean(false);

		/** value */
		private T value;

		/**
		 * @param value
		 */
		public AtomicObject(T value) {
			this.value = value;
		}

		/**
		 * @return the locking
		 */
		public AtomicBoolean getLocking() {
			return locking;
		}

		/**
		 * @return the value
		 */
		public T getValue() {
			return value;
		}
	}
}
