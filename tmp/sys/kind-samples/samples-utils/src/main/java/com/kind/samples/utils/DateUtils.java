package com.kind.samples.utils;

import java.text.SimpleDateFormat;


public class DateUtils {

    /**
     * 时间戳转换为日期
     *
     * @param time
     * @return
     */
    public static String time2Date(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (time != null) {
            String date = format.format(time);
            return date;
        }
        return null;

    }

    public static void main(String[] args) throws Exception {
        System.out.println(DateUtils.time2Date(1447948800000L));
    }
}
