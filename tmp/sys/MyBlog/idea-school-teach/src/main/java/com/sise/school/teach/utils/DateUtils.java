package com.sise.school.teach.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化工具
 *
 * @author idea
 * @data 2018/10/14
 */
public class DateUtils {

    /**
     * 日期格式转换为字符串格式
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
