/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-3-8 下午12:43:09
 */
package com.absir.core.util;

/**
 * @author absir
 * 
 */
@SuppressWarnings("rawtypes")
public class UtilEnum {

	/**
	 * @author absir 枚举对象
	 * 
	 */
	public static interface EnumObject<T> {

		/**
		 * @author absir 枚举文字
		 */
		public static interface EnumString extends EnumObject<String> {

		}

		/**
		 * @author absir 枚举整数
		 */
		public static interface EnumInteger extends EnumObject<Integer> {

		}

		/**
		 * @author absir 枚举长整数
		 */
		public static interface EnumLong extends EnumObject<Long> {

		}

		/**
		 * @author absir 枚举类型
		 */
		public static interface EnumClass extends EnumObject<Class> {

		}

		/**
		 * @return
		 */
		public T getValue();
	}

}
