package com.dtdream.rdic.saas.utils;

import sun.util.resources.CalendarData_ar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    public static String stamp (boolean millisecond) {
        StringBuilder sFmt = new StringBuilder("yyyy-MM-dd HH:mm:ss");
        if (millisecond)
            sFmt.append(".SSS");
        SimpleDateFormat objFmt = new SimpleDateFormat(sFmt.toString());
        return objFmt.format(new Date());
    }
    public static String stamp (Date dt, boolean millisecond) {
        StringBuilder sFmt = new StringBuilder("yyyy-MM-dd HH:mm:ss");
        if (millisecond)
            sFmt.append(".SSS");
        SimpleDateFormat objFmt = new SimpleDateFormat(sFmt.toString());
        return objFmt.format(dt);
    }

    public static String stamp (Integer minutes) {
        return String.format("%02d:%02d", minutes/60, minutes%60);
    }

    public static Date start () {
        return start(null);
    }

    public static Date start (Date date) {
        Calendar day = Calendar.getInstance();
        if (null != date)
            day.setTime(date);
        day.set(Calendar.AM_PM, Calendar.AM);
        day.set(Calendar.HOUR, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return day.getTime();
    }

    public static Date over () {
        return over(null);
    }

    public static Date over (Date date) {
        Calendar day = Calendar.getInstance();
        if (null != date)
            day.setTime(date);
        day.set(Calendar.AM_PM, Calendar.PM);
        day.set(Calendar.HOUR, 11);
        day.set(Calendar.MINUTE, 59);
        day.set(Calendar.SECOND, 59);
        day.set(Calendar.MILLISECOND, 999);
        return day.getTime();
    }

    public static Date offset (Date dt, int specifier, Integer seconds) {
        Calendar now = Calendar.getInstance();
        if (null != dt)
            now.setTime(dt);
        now.add(specifier, seconds);
        return now.getTime();
    }

    /**
     * 获取时间在当天的偏移，按分钟计算，date为空时获取现在的分钟偏移数值
     * @param date
     * @return
     */
    public static Integer min (Date date) {
        Calendar day = Calendar.getInstance();
        if (null == date)
            day.setTime(new Date());
        else
            day.setTime(date);
        int am = day.get(Calendar.AM_PM);
        int h = day.get(Calendar.HOUR);
        int m = day.get(Calendar.MINUTE);
        Integer minutes = 0;
        if (am == Calendar.PM)
            minutes += 12 * 60;
        minutes += h * 60;
        minutes += m;
        return minutes;
    }

    public static int dayofweek (Date date) {
        Calendar day = Calendar.getInstance();
        if (null == date)
            day.setTime(new Date());
        else
            day.setTime(date);
        return day.get(Calendar.DAY_OF_WEEK);
    }
}
