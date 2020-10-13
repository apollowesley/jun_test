package cn.jeeweb.beetl.tags.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 
 * All rights Reserved, Designed By www.gzst.gov.cn
 * 
 * @title: ObjectUtils.java
 * @package cn.gov.gzst.common.utils
 * @description: 对象工具类
 * @author: 王存见
 * @date: 2017年5月7日 下午10:57:53
 * @version V1.0
 * @copyright: 2017 www.gzst.gov.cn Inc. All rights reserved.
 *
 */

@SuppressWarnings("rawtypes")
public final class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

	/**
	 * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map) obj).isEmpty();

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}

}
