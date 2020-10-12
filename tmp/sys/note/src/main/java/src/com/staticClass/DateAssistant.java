package com.staticClass;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * 用于人性化显示时间，比如 昨天，前天，星期三，几月几号，某年几月几号
 * 
 * @author peaches
 * @date 2015-3-14上午2:06:18
 */
public class DateAssistant {

	public static HashMap<Integer, String> DateHuman = new HashMap<Integer, String>();
	static {
		DateHuman.put(1, "星期日 ");
		DateHuman.put(2, "星期一 ");
		DateHuman.put(3, "星期二 ");
		DateHuman.put(4, "星期三 ");
		DateHuman.put(5, "星期四 ");
		DateHuman.put(6, "星期五 ");
		DateHuman.put(7, "星期六 ");
		DateHuman.put(8, "前天 ");
		DateHuman.put(9, "昨天 ");
	}

	public static String parse(String date) {
		Date d = DateUtil.parse(date);
		Date nowD = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(d);
		Calendar nowC = Calendar.getInstance();
		nowC.setTime(nowD);
		int nowDayOfYear = nowC.get(Calendar.DAY_OF_YEAR);
		int dayOfYear = c.get(Calendar.DAY_OF_YEAR);

		if (nowC.get(Calendar.YEAR) == c.get(Calendar.YEAR) && nowDayOfYear >= dayOfYear) {
			if (nowDayOfYear == dayOfYear) {
				return DateUtil.format(d, 8);
			} else if (nowDayOfYear - dayOfYear == 1) {
				return DateHuman.get(9) + DateUtil.format(d, 8);
			} else if (nowDayOfYear - dayOfYear == 2) {
				return DateHuman.get(8) + DateUtil.format(d, 8);
			} else {
				int nowDayOfWeek = nowC.get(Calendar.DAY_OF_WEEK);
				int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				if (dayOfWeek < nowDayOfWeek && nowDayOfYear - dayOfYear < nowDayOfWeek) {
					return DateHuman.get(dayOfWeek) + DateUtil.format(d, 8);
				} else {
					return date;
				}
			}
		} else {
			return date;
		}

	}

	public static void main(String[] args) {
		System.out.println(DateAssistant.parse("2015-01-02 13:56"));
		System.out.println(DateAssistant.parse("2015-01-01 13:56"));
		System.out.println(DateAssistant.parse("2014-12-31 13:56"));
		System.out.println(DateAssistant.parse("2015-12-30 13:56"));
		System.out.println(DateAssistant.parse("2015-03-12 13:56"));
		System.out.println(DateAssistant.parse("2015-03-11 13:56"));
		System.out.println(DateAssistant.parse("2015-03-10 13:56"));
		System.out.println(DateAssistant.parse("2015-03-09 13:56"));
		System.out.println(DateAssistant.parse("2015-03-8 13:56"));
		System.out.println(DateAssistant.parse("2015-03-7 13:56"));
		System.out.println(DateAssistant.parse("2015-03-6 13:56"));
		System.out.println(DateAssistant.parse("2015-03-5 13:56"));
		System.out.println(DateAssistant.parse("2015-03-4 13:56"));
		System.out.println(DateAssistant.parse("2015-03-3 13:56"));
		System.out.println(DateAssistant.parse("2015-03-2 13:56"));
		System.out.println(DateAssistant.parse("2015-03-1 13:56"));
		System.out.println(DateAssistant.parse("2015-02-30 13:56"));
		System.out.println(DateAssistant.parse("2015-02-29 13:56"));
		System.out.println(DateAssistant.parse("2015-02-28 13:56"));
		System.out.println(DateAssistant.parse("2015-02-27 13:56"));
		System.out.println(DateAssistant.parse("2015-02-26 13:56"));
	}
}
