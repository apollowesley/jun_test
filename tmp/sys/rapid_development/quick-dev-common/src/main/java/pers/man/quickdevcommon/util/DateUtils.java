package pers.man.quickdevcommon.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author MAN
 * @version 1.0
 * @date 2020-04-01 20:01
 * @project quick-dev
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    /**
     * 根据指定格式返回日期格式
     *
     * @param pattern
     * @return
     */
    public static String getCurrentTimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 默认以 yyyy-MM-dd hh:mm:ss 形式返回当前时间的String
     *
     * @return
     */
    public static String getCurrentTimeStr() {
        return getCurrentTimeStr("yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 获取当前时间 Date
     * 不建议使用Date类型
     *
     * @return
     */
    @Deprecated
    public static Date getCurrentDate() {
        return Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前时间 Timestamp
     *
     * @return
     */
    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * 根据指定格式格式化传入的Date
     *
     * @param date
     * @return
     */
    @Deprecated
    public static String dateFormat(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    private DateUtils(){

    }
    /**
     * 格式化传入的Date
     *
     * @param date
     * @return
     */
    @Deprecated
    public static String dateFormat(Date date) {
        return dateFormat(date);
    }
}
