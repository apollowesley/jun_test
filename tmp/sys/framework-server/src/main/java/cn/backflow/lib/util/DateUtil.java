package cn.backflow.lib.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    public static final String[] FALL_BACK_FORMATS = new String[]{DATE_FORMAT, DATE_TIME_FORMAT, TIMESTAMP_FORMAT};

    // time unit in milliseconds
    public static final long SECEND_IN_MILLIS = 1000L;
    public static final long MINITE_IN_MILLIS = SECEND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINITE_IN_MILLIS * 60;
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;
    public static final long WEEK_IN_MILLIS = DAY_IN_MILLIS * 7;
    public static final long MONTH_IN_MILLIS = DAY_IN_MILLIS * 30;
    public static final long YEAR_IN_MILLIS = DAY_IN_MILLIS * 365;

    public static final String[] zodiacArray = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};

    public static final String[] constellationArray = {"水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};

    private static final int[] constellationEdgeDay = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};

    public static Date parseDate(String strDate, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(strDate);
        } catch (ParseException ignored) {
        }
        return null;
    }

    /**
     * 解析成对的日期字符串
     *
     * @param text       日期对字符串
     * @param dateFormat 格式字符串
     * @param separator  分隔符
     * @return 日期数组
     */
    public static Date[] parseDatePair(String text, String dateFormat, String separator) {
        Date[] arr = new Date[2];

        if (StringUtils.isBlank(text)) return arr;

        if (dateFormat == null)
            dateFormat = DEFAULT_DATE_FORMAT;

        if (separator == null)
            separator = "至|到|to|,|\\\\-";

        String[] pair = text.split(separator);

        if (pair.length > 0)
            arr[0] = DateUtil.parseDate(pair[0], dateFormat);

        if (pair.length > 1)
            arr[1] = DateUtil.parseDate(pair[1], dateFormat);

        return arr;
    }

    /**
     * 格式化成对的日期字符串
     *
     * @param from       起始日期
     * @param to         结束日期
     * @param dateFormat 格式字符串
     * @param separator  分隔符
     * @return 日期对字符串
     */
    public static String formatDatePair(Date from, Date to, String dateFormat, String separator) {
        String result = "";
        if (dateFormat == null)
            dateFormat = DEFAULT_DATE_FORMAT;
        if (separator == null)
            separator = "到";
        if (from != null)
            result += formatDate(from, dateFormat);
        if (to != null)
            result += " " + separator + " " + formatDate(to, dateFormat);
        return result;
    }

    public static boolean isWeekend(Date date) {
        Calendar c = Calendar.getInstance();
        if (date != null)
            c.setTime(date);
        int weekDay = c.get(Calendar.DAY_OF_WEEK);
        return (weekDay == 1) || (weekDay == 7);
    }


    public static boolean inPeriod(Date start, Date end, Date date) {
        return ((end.after(date)) || (end.equals(date))) && ((start.before(date)) || (start.equals(date)));
    }

    public static String date2Zodica(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        return year2Zodica(c.get(Calendar.YEAR));
    }

    public static String year2Zodica(int year) {
        return zodiacArray[(year % 12)];
    }

    public static String date2Constellation(Date time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month--;
        }
        if (month >= 0) {
            return constellationArray[month];
        }

        return constellationArray[11];
    }

    /**
     * 判断是否闰月，用于计算当前时间加上分钟后的时间
     *
     * @param year 年份
     */
    public static boolean isLeapYear(int year) {
        // 能被100整除, 不能被400整除的年份, 不是闰年.
        // 能被100整除, 也能被400整除的年份, 是闰年.
        if (year % 100 == 0)
            return year % 400 == 0;
        else
            return year % 4 == 0; // 不能被100整除, 能被4整除的年份是闰年.
    }

    /**
     * 计算距离现在的日期字符串 (总是取最大的时间单位)
     *
     * @param date 要计算的时候
     * @return 三钟前, 两天前, 一年前 等
     */
    public static String dateBeforeNow(Date date) {
        if (date == null) return "";
        long distance = System.currentTimeMillis() - date.getTime();
        int years = (int) (distance / YEAR_IN_MILLIS);
        if (years > 0) return years + "年前";
        int months = (int) (distance / MONTH_IN_MILLIS);
        if (months > 0) return months + "个月前";
        int weeks = (int) (distance / WEEK_IN_MILLIS);
        if (weeks > 0) return weeks + "周前";
        int days = (int) (distance / DAY_IN_MILLIS);
        if (days > 0) return days + "天前";
        int hours = (int) (distance / HOUR_IN_MILLIS);
        if (hours > 0) return hours + "小时前";
        int minites = (int) (distance / MINITE_IN_MILLIS);
        if (minites > 0) return minites + "分钟前";
        int secends = (int) (distance / SECEND_IN_MILLIS);
        if (secends > 0) return secends + "秒前";
        return "刚才";
    }

    public static String parseTime(long milsec, String format) {
        milsec = milsec / 1000;
        long sec = milsec % 60;
        long min = milsec / 60 % 60;
        long hour = milsec / 3600;
        long day = milsec / 86400;
        String result = "";
        if (format.contains("d"))
            result += (day == 0 ? "" : day + "天");
        if (format.contains("h"))
            result += hour + "小时";
        if (format.contains("m"))
            result += (min < 10 ? "0" + min : "" + min) + "分钟";
        if (format.contains("s"))
            result += (sec < 10 ? "0" + sec : "" + sec) + "秒";
        return result;
    }

    @SuppressWarnings("deprecation")
    public static String toString(Date date) {
        if (date == null) return "";
        try {
            return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
        } catch (Exception e) {
            return date.toLocaleString();
        }
    }

    public static String formatDate(Date date, String... dateFormat) {
        if (date == null) return null;
        if (dateFormat == null || dateFormat.length == 0)
            return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
        for (String f : dateFormat) {
            try {
                return new SimpleDateFormat(f).format(date);
            } catch (Exception ignored) {

            }
        }
        return null;
    }

    public static Date parseDate(String source) {
        if (StringUtils.isBlank(source))
            return null;
        try {
            return new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(source);
        } catch (ParseException e) {
            for (String pattern : FALL_BACK_FORMATS) {
                try {
                    return new SimpleDateFormat(pattern).parse(source);
                } catch (ParseException ignored) {
                }
            }
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Date> T parseDate(String dateString, String dateFormat, Class<T> targetType) {
        if (StringUtils.isBlank(dateString))
            return null;
        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            long time = df.parse(dateString).getTime();
            Date t = targetType.getConstructor(long.class).newInstance(time);
            return (T) t;
        } catch (ParseException e) {
            String errorInfo = "cannot use dateformat:" + dateFormat + " parse datestring:" + dateString;
            throw new IllegalArgumentException(errorInfo, e);
        } catch (Exception e) {
            throw new IllegalArgumentException("error targetResultType:" + targetType.getName(), e);
        }
    }

    /**
     * 时间偏移
     *
     * @param date  要偏移的时间
     * @param field 偏移属性(Calendar类的常量, 如Calendar.DAY_OF_YEAR
     * @param i     偏移量
     * @return 偏移后的时间
     */
    public static Date shiftTime(Date date, int field, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(field, calendar.get(field) + i);
        return calendar.getTime();
    }


    public static long[] getDistanceTimes(Date one, Date two) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        return new long[]{day, hour, min, sec};
    }
}