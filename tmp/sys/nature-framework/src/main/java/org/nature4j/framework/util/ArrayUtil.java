package org.nature4j.framework.util;

/**
 * 数组工具类 Created by Ocean on 2016/3/9.
 */
public final class ArrayUtil {

	/**
	 * 判断数组为空
	 */
	public static boolean isEmpty(Object[] objects) {
		return objects == null || objects.length == 0;
	}

	/**
	 * 判断数组为空
	 */
	public static boolean isNotEmpty(Object[] objects) {
		return !isEmpty(objects);
	}

	/**
	 * 判断数组是否存在某个字符串
	 */
	public static boolean isExsit(String[] strs, String str) {
		boolean exsit = false;
		if (strs != null) {
			for (String s : strs) {
				if (s.equals(str)) {
					exsit = true;
				}
			}
		}
		return exsit;
	}
}
