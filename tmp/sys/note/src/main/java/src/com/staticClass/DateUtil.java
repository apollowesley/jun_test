package com.staticClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 用于格式化时间字符串，支持多种格式，但是不是全格式
 * 
 * @author peaches
 * @date 2015-3-14上午2:08:40
 */
public class DateUtil {
	private static String[] dateFormat = { "yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd", "yyyy-MM", "yyyy", "MM-dd", "HH:mm",
			"HH:mm:ss" };

	private static SimpleDateFormat sdf = null;

	/**
	 * 
	 * @param dateString
	 * @param index
	 *            索引，从第几个开始
	 * @return
	 * @createTime 2015-2-14 下午8:58:06
	 */
	public static Date parse(String dateString, int index) {
		for (int i = index; i < dateFormat.length; i++) {
			try {
				sdf = new SimpleDateFormat(dateFormat[i]);
				return sdf.parse(dateString);
			} catch (Exception e) {
				continue;
			}
		}
		return null;
	}

	public static Date parse(String dateString) {
		return parse(dateString, 0);
	}

	/**
	 * 解析long类型为字符串
	 * 
	 * @param longDate
	 * @return
	 * @createTime 2015-2-14 下午9:54:20
	 */
	public static Date parse(long longDate) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(longDate);
		return c.getTime();
	}

	/**
	 * 
	 * @param date
	 * @param index
	 *            索引，从第几个开始
	 * @return
	 * @createTime 2015-2-14 下午9:02:14
	 */
	public static String format(Date date, int index) {
		for (int i = index; i < dateFormat.length; i++) {
			try {
				sdf = new SimpleDateFormat(dateFormat[i]);
				return sdf.format(date);
			} catch (Exception e) {
				continue;
			}
		}
		return null;
	}

	public static String format(Date date) {
		return format(date, 0);
	}

	public static void main(String[] args) {
		System.out.println(new Date().getTime());
		System.out.println(DateUtil.format(new Date(), 3));
		System.out.println(DateUtil.parse(DateUtil.format(new Date())));
	}
}
