package com.omniselling.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateTimeUtil
{

    final public static String DateTimeFormatString = "yyyy-MM-dd HH:mm:ss";
    final public static SimpleDateFormat DateTimeFormat = new SimpleDateFormat(DateTimeFormatString);

    final public static String DateTimeFormatString2 = "yyyy年MM月dd日hh时mm分ss秒";
    final public static SimpleDateFormat DateTimeFormat2 = new SimpleDateFormat(DateTimeFormatString2);

    final public static String DateFormatString = "yyyy-MM-dd";
    final public static SimpleDateFormat DateFormat = new SimpleDateFormat(DateFormatString);

    final public static String DateFormatString2 = "yyyy/MM/dd";
    final public static SimpleDateFormat DateFormat2 = new SimpleDateFormat(DateFormatString2);

    final public static String DateFormatString3 = "yyyy年MM月dd日";
    final public static SimpleDateFormat DateFormat3 = new SimpleDateFormat(DateFormatString3);

    public static Calendar calendar = Calendar.getInstance();

    /**
     * 返回格式：yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static Date convertToDateTime(String date)
    {
        Date res = null;
        try
        {
            res = (date == null || date.length() == 0) ? null : DateTimeFormat.parse(date);
        }
        catch (ParseException e)
        {
            return null;
        }
        return res;
    }

    /**
     * 返回格式：yyyy年MM月dd日hh时mm分ss秒
     * 
     * @param date
     * @return
     */
    public static Date convertToDateTime2(String date)
    {
        Date res = null;
        try
        {
            res = (date == null || date.length() == 0) ? null : DateTimeFormat2.parse(date);
        }
        catch (ParseException e)
        {
            return null;
        }
        return res;
    }

    /**
     * 返回格式：yyyy-MM-dd
     * 
     * @param date
     * @return
     */
    public static Date convertToDate(String date)
    {
        Date res = null;
        try
        {
            res = (date == null || date.length() == 0) ? null : DateFormat.parse(date);
        }
        catch (Exception e)
        {
            return null;
        }
        return res;
    }

    /**
     * 返回格式：yyyy/MM/dd
     * 
     * @param date
     * @return
     */
    public static Date convertToDate2(String date)
    {
        Date res = null;
        try
        {
            res = (date == null || date.length() == 0) ? null : DateFormat2.parse(date);
        }
        catch (Exception e)
        {
            return null;
        }
        return res;
    }

    /**
     * 返回格式：yyyy年MM月dd日
     * 
     * @param date
     * @return
     */
    public static Date convertToDate3(String date)
    {
        Date res = null;
        try
        {
            res = (date == null || date.length() == 0) ? null : DateFormat3.parse(date);
        }
        catch (Exception e)
        {
            return null;
        }
        return res;
    }

    /**
     * 返回格式：yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static String convertToString(Date date)
    {
        String reportDate = DateTimeFormat.format(date);
        return reportDate;
    }

    /**
     * 返回格式：yyyy年MM月dd日hh时mm分ss秒
     * 
     * @param date
     * @return
     */
    public static String convertToString2(Date date)
    {
        String reportDate = DateTimeFormat2.format(date);
        return reportDate;
    }

    /**
     * 返回格式：yyyy-MM-dd
     * 
     * @param date
     * @return
     */
    public static String convertToDateYMDString(Date date)
    {
        if (date == null)
        {
            return null;
        }
        String reportDate = DateFormat.format(date);
        return reportDate;
    }

    /**
     * 返回格式：yyyy/MM/dd
     * 
     * @param date
     * @return
     */
    public static String convertToDateYMDString2(Date date)
    {
        if (date == null)
        {
            return null;
        }
        String reportDate = DateFormat2.format(date);
        return reportDate;
    }

    /**
     * 返回格式：yyyy年MM月dd日
     * 
     * @param date
     * @return
     */
    public static String convertToDateYMDString3(Date date)
    {
        if (date == null)
        {
            return null;
        }
        String reportDate = DateFormat3.format(date);
        return reportDate;
    }

    /**
     * 获得data + hour小时后的时间
     * 
     * @param date
     * @param minute
     * @return
     */
    public static Date addHour(Date date, int hour)
    {
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    /**
     * 获得data + minute分钟后的时间
     * 
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute)
    {
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获得年份
     * 
     * @param date
     * @return
     */
    public static int getYear(Date date)
    {
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获得月份
     * 
     * @param date
     * @return
     */
    public static int getMonth(Date date)
    {
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH + 1);
    }

}
