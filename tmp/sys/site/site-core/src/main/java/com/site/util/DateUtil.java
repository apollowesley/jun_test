package com.site.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期工具类
 * 
 */
public final class DateUtil {

    private final static String DATE_FORMAT = "yyyy-MM-dd";

    private final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // ** yyyy-MM-dd HH:mm:ss.SSS
    public static final String fm_yyyy_MM_dd_HHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";

    private DateUtil() {

    }

    /**
     * timeStamp 字符串转成日期格式字符串
     * 
     * @param millSec
     *            timeStamp 字符串
     * @return 日期字符串
     */
    public static String transferLongToString(Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * timeStamp 字符串转成日期
     * 
     * @return 日期
     */
    public static Date transferLongToDate(Long millSec) {
        return new Date(millSec);
    }

    /**
     * 生成日期解析对象
     * 
     * @param pattern
     *            转换格式
     * @return DateFormat 日期解析对象
     */
    public static DateFormat doDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    private static Date getNewTtime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.getTime();
    }

    /**
     * 日期转换到字符串
     * 
     * @param paramDate
     *            要转换的日期
     * @param pattern
     *            转换格式：例：yyyy-MM-dd
     * @return String 日期字符串
     */
    public static String dateToString(Date paramDate, String pattern) {
        return doDateFormat(pattern).format(paramDate);
    }

    /**
     * @return String yyyy-MM-dd
     */
    public static String getSystemDateStr() {
        return dateToString(getNewTtime(), DATE_FORMAT);
    }

    /**
     * @return String yyyy-MM-dd HH:mm:ss
     */
    public static String getSystemDateTimeStr() {
        return dateToString(getNewTtime(), DATE_TIME_FORMAT);
    }

    /**
     * 日期转换到字符串
     * 
     * @param paramDate
     *            要转换的日期
     * @return String 日期字符串 yyyy-mm-dd
     */
    public static String formatDateToString(Date paramDate) {
        return doDateFormat(DATE_FORMAT).format(paramDate);
    }

    /**
     * 日期转换到字符串
     * 
     * @param paramDate
     *            要转换的日期
     * @return String 日期字符串 yyyy-mm-dd hh:mm:ss
     */
    public static String formatDateTimeToString(Date paramDate) {
        return doDateFormat(DATE_TIME_FORMAT).format(paramDate);
    }

    /**
     * @return Date yyyy-MM-dd
     */
    public static Date getSystemDate() {
        return dateFormat(getNewTtime(), DATE_FORMAT);
    }

    /**
     * @return Date yyyy-MM-dd HH:mm:ss
     */
    public static Date getSystemDateTime() {
        return dateFormat(getNewTtime(), DATE_TIME_FORMAT);
    }

    /**
     * 
     * 日期格式转换
     * 
     * @param paramDate
     * @param pattern
     * @return
     */
    public static Date dateFormat(Date paramDate, String pattern) {
        String dateStr = doDateFormat(pattern).format(paramDate);
        return stringToDate(dateStr, pattern);
    }

    /**
     * 字符串转换到日期
     * 
     * @param dateStr
     *            日期字符串
     * @param pattern
     *            转换格式：例：yyyy-MM-dd
     * @return Date 转换后的日期
     */
    public static Date stringToDate(String dateStr, String pattern) {
        try {
            return doDateFormat(pattern).parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 将符合默认格式的字符串转换成Date
     * 
     * @param dateValue
     *            样例:2012-03-29 14:32
     * @return
     * @throws ParseException
     */
    public static Date parseTime(String dateValue) throws ParseException {
        SimpleDateFormat formatTool = new SimpleDateFormat();
        formatTool.applyPattern("yyyy-mm-dd hh:mm:ss");
        return formatTool.parse(dateValue);
    }

    /**
     * 获取当前日期(字符串格式)
     * 
     * @param pattern
     *            转换格式：例：yyyy-MM-dd
     * @return String 日期字符串
     */
    public static String getCurrDate(String pattern) {
        return dateToString(new Date(), pattern);
    }

    /**
     * 获取当前日期(日期格式)
     * 
     * @param pattern
     *            转换格式：例：yyyy-MM-dd
     * @return Date 日期
     */
    public static Date getCurrDateOfDate(String pattern) {
        return stringToDate(dateToString(new Date(), pattern), pattern);
    }

    /**
     * 获取日期是星期几
     * 
     * @param paramDate
     *            参数日期
     * @param retFormat
     *            返回格式：0、表示返回数字格式 1、表示返回中文格式
     * @return String 星期几
     */
    public static String getDayOfWeek(Date paramDate, int retFormat) {
        Calendar c = Calendar.getInstance();
        c.setTime(paramDate);
        int dayOfWeek = (c.get(Calendar.DAY_OF_WEEK) == 1) ? 7 : c.get(Calendar.DAY_OF_WEEK) - 1;
        String dayOfWeekStr = null;
        switch (dayOfWeek) {
        case 1:
            dayOfWeekStr = (0 == retFormat) ? "1" : "一";
            break;
        case 2:
            dayOfWeekStr = (0 == retFormat) ? "2" : "二";
            break;
        case 3:
            dayOfWeekStr = (0 == retFormat) ? "3" : "三";
            break;
        case 4:
            dayOfWeekStr = (0 == retFormat) ? "4" : "四";
            break;
        case 5:
            dayOfWeekStr = (0 == retFormat) ? "5" : "五";
            break;
        case 6:
            dayOfWeekStr = (0 == retFormat) ? "6" : "六";
            break;
        case 7:
            dayOfWeekStr = (0 == retFormat) ? "7" : "日";
            break;
        }
        return dayOfWeekStr;
    }

    /**
     * 指定日期几天后或者几天前的日期
     * 
     * @param paramDate
     *            指定日期
     * @param days
     *            天数
     * @return Date 几天后或者几天前的日期
     */
    public static Date addDate(Date paramDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 指定日期几月后或者几月前的日期
     * 
     * @param paramDate
     *            指定日期
     * @param months
     *            月数
     * @return Date 几月后或者几月前的日期
     */
    public static Date addDateOfMonth(Date paramDate, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 根据指定日期获取指定日期所在周的开始日期和结束日期(星期一、星期天)
     * 
     * @param paramDate
     *            指定日期
     * @return String[] 开始日期和结束日期数组
     */
    public static String[] getWeekStartAndEndDate(Date paramDate) {
        String[] retAry = new String[2];

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        // 以周一为一周的开始
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        retAry[0] = dateToString(calendar.getTime(), "yyyy-MM-dd");
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        retAry[1] = dateToString(calendar.getTime(), "yyyy-MM-dd");

        return retAry;
    }

    /**
     * 根据指定日期获取指定日期所在月的第一天和最后一天
     * 
     * @param paramDate
     *            指定日期
     * @return String[] 第一天和最后一天数组
     */
    public static String[] getMonthStartAndEndDate(Date paramDate) {
        String[] retAry = new String[2];

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paramDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        retAry[0] = dateToString(calendar.getTime(), "yyyy-MM-dd");
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        retAry[1] = dateToString(calendar.getTime(), "yyyy-MM-dd");

        return retAry;
    }

    /**
     * 获取指定两个日期相差的天数
     * 
     * @param paramDate1
     *            指定日期1
     * @param paramDate2
     *            指定日期2
     * @return int 相差天数
     */
    public static int getDiffDaysOfTwoDate(String paramDate1, String paramDate2) {
        Date date1 = stringToDate(paramDate1, "yyyy-MM-dd");
        Date date2 = stringToDate(paramDate2, "yyyy-MM-dd");

        Long diffTimes = date1.getTime() - date2.getTime();
        Long diffDays = diffTimes / (3600 * 1000 * 24);

        return Math.abs(diffDays.intValue());
    }

    /**
     * 获取指定日期相差月份数
     * 
     * @param paramDate1
     *            指定日期1
     * @param paramDate2
     *            指定日期2
     * @return int 相差月份数 注：日期所在月都算一月
     */
    public static int getDiffMonthsOfTwoDate(String paramDate1, String paramDate2) {
        // 指定日期1的年份、月份
        int tempYear1 = Integer.parseInt(paramDate1.substring(0, 4));
        int tempMonth1 = Integer.parseInt(paramDate1.substring(5, 7));

        // 指定日期2的年份、月份
        int tempYear2 = Integer.parseInt(paramDate2.substring(0, 4));
        int tempMonth2 = Integer.parseInt(paramDate2.substring(5, 7));

        return Math.abs((tempYear1 * 12 + tempMonth1) - (tempYear2 * 12 + tempMonth2)) + 1;
    }

    /**
     * 获取指定日期所在月有多少天
     * 
     * @param paramDate
     *            指定日期(yyyy-MM格式)
     * @return int 指定日期所在月有多少天
     */
    public static int getDaysOfMonths(String paramDate) {
        int days = 0;
        try {
            Date date = doDateFormat("yyyy-MM").parse(paramDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return days;
    }

    /**
     * 比较两个日期是否相等(精确到天)
     * 
     * @param dateOne
     * @param dateOther
     * @return
     */
    public static boolean isEqualDates(Date dateOne, Date dateOther) {
        if (null == dateOne || null == dateOther) {
            return false;
        }
        Calendar calOne = Calendar.getInstance();
        Calendar calOther = Calendar.getInstance();

        calOne.setTime(dateOne);
        calOther.setTime(dateOther);

        int subYear = calOne.get(Calendar.YEAR) - calOther.get(Calendar.YEAR);
        int subMonth = calOne.get(Calendar.MONTH) - calOther.get(Calendar.MONTH);
        int subDay = calOne.get(Calendar.DAY_OF_MONTH) - calOther.get(Calendar.DAY_OF_MONTH);

        if (subYear == 0 && subMonth == 0 && subDay == 0) {
            return true;
        }

        return false;
    }

    /**
     * 
     * 判断date1是否在date2之后
     * 
     * @param date1
     *            日期1
     * @param date2
     *            日期2
     * @return
     */
    public static boolean isAfter(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        if (StringUtils.isEmpty(date1)) {
            return true;
        }
        if (StringUtils.isEmpty(date2)) {
            return false;
        }
        Date d1;
        try {
            d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            return d1.after(d2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 去除时分秒
     * 
     * @param cur
     *            待处理日期
     * @return 只包含年月日的日期
     */
    public static Date clearTime(Date cur) {
        if (cur == null) {
            return cur;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(cur);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
