package site.zhouinfo.datetime;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author:      zhou
 * Create Date：2016-03-05 18:46
 */
public class DateUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 毫秒
	 */
	public final static long MS = 1;
	/**
	 * 每秒钟的毫秒数
	 */
	public final static long SECOND_MS = MS * 1000;
	/**
	 * 每分钟的毫秒数
	 */
	public final static long MINUTE_MS = SECOND_MS * 60;
	/**
	 * 每小时的毫秒数
	 */
	public final static long HOUR_MS = MINUTE_MS * 60;
	/**
	 * 每天的毫秒数
	 */
	public final static long DAY_MS = HOUR_MS * 24;

	private static final String NORM_DATE_FORMAT = "yyyy-MM-dd";

	private static ThreadLocal<SimpleDateFormat> normalDateFormatLocal = new ThreadLocal<SimpleDateFormat>() {

		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(NORM_DATE_FORMAT);
		}

	};

	private static final String NORM_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static ThreadLocal<SimpleDateFormat> normalDateTimeFormatLocal = new ThreadLocal<SimpleDateFormat>() {

		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(NORM_DATETIME_FORMAT);
		}

	};

	private static final String HTTP_DATETIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

	private static ThreadLocal<SimpleDateFormat> httpDateTimeFormatLocal = new ThreadLocal<SimpleDateFormat>() {

		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(HTTP_DATETIME_FORMAT);
		}

	};

	public static DateFormat getNormalDateFormat() {
		return normalDateFormatLocal.get();
	}

	public static DateFormat getNormalDateTimeFormat() {
		return normalDateTimeFormatLocal.get();
	}

	public static DateFormat getHttpDateTimeFormat() {
		return httpDateTimeFormatLocal.get();
	}

	/**
	 * 当前时间，格式 yyyy-MM-dd HH:mm:ss
	 *
	 * @return 当前时间的标准形式字符串
	 */
	public static String now() {
		return formatDateTime(new Date());
	}

	/**
	 * 当前日期，格式 yyyy-MM-dd
	 *
	 * @return 当前日期的标准形式字符串
	 */
	public static String today() {
		return formatDate(new Date());
	}

	// ------------------------------------ Format start
	// ----------------------------------------------

	/**
	 * 根据特定格式格式化日期
	 *
	 * @param date   被格式化的日期
	 * @param format 格式
	 * @return 格式化后的字符串
	 */
	public static String format(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 格式 yyyy-MM-dd HH:mm:ss
	 *
	 * @param date 被格式化的日期
	 * @return 格式化后的日期
	 */
	public static String formatDateTime(Date date) {
		return getNormalDateTimeFormat().format(date);
	}

	/**
	 * 格式化为Http的标准日期格式
	 *
	 * @param date 被格式化的日期
	 * @return HTTP标准形式日期字符串
	 */
	public static String formatHttpDate(Date date) {
		return getHttpDateTimeFormat().format(date);
	}

	/**
	 * 格式 yyyy-MM-dd
	 *
	 * @param date 被格式化的日期
	 * @return 格式化后的字符串
	 */
	public static String formatDate(Date date) {
		return getNormalDateFormat().format(date);
	}

	/**
	 * 将特定格式的日期转换为Date对象
	 *
	 * @param dateString 特定格式的日期
	 * @param format     格式，例如yyyy-MM-dd
	 * @return 日期对象
	 */
	public static Date parse(String dateString, String format) {
		try {
			return (new SimpleDateFormat(format)).parse(dateString);
		} catch (ParseException e) {
			LOGGER.error("Parse " + dateString + " with format " + format
					+ " error!", e);
		}
		return null;
	}

	/**
	 * 格式yyyy-MM-dd HH:mm:ss
	 *
	 * @param dateString 标准形式的时间字符串
	 * @return 日期对象
	 */
	public static Date parseDateTime(String dateString) {

		try {
			return getNormalDateTimeFormat().parse(dateString);
		} catch (ParseException e) {
			LOGGER.error("Parse " + dateString + " with format "
					+ NORM_DATETIME_FORMAT + " error!", e);
		}
		return null;
	}

	/**
	 * 格式yyyy-MM-dd HH:mm:ss
	 *
	 * @param dateString 标准形式的时间字符串
	 * @return 日期对象
	 */
	public static Date parseDate(String dateString) {

		try {
			return getNormalDateFormat().parse(dateString);
		} catch (ParseException e) {
			LOGGER.error("Parse " + dateString + " with format "
					+ NORM_DATE_FORMAT + " error!", e);
		}
		return null;
	}

	/**
	 * 获取指定日期偏移指定时间后的时间
	 *
	 * @param date          基准日期
	 * @param calendarField 偏移的粒度大小（小时、天、月等）使用Calendar中的常数
	 * @param offsite       偏移量，正数为向后偏移，负数为向前偏移
	 * @return 偏移后的日期
	 */
	public static Date getOffsiteDate(Date date, int calendarField, int offsite) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(calendarField, offsite);
		return cal.getTime();
	}

	/**
	 * 判断两个日期相差的时长<br/>
	 * 返回 minuend - subtrahend 的差
	 *
	 * @param subtrahend 减数日期
	 * @param minuend    被减数日期
	 * @param diffField  相差的选项：相差的天、小时
	 * @return 日期差
	 */
	public static long dateDiff(Date subtrahend, Date minuend, long diffField) {
		long diff = minuend.getTime() - subtrahend.getTime();
		return diff / diffField;
	}

}
