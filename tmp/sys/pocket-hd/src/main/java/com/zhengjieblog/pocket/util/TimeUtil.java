package com.zhengjieblog.pocket.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 郑杰
 * @date 2018-7-20
 */
public class TimeUtil {

    //返回当前年份
    public static int  getYear(Date date)
    {
        String year = new SimpleDateFormat("yyyy").format(date);
        return Integer.parseInt(year);
    }

    //返回当前月份
    public static int getMonth(Date date)
    {
        String month = new SimpleDateFormat("MM").format(date);
        return Integer.parseInt(month);
    }

    //返回当前天数
    public static int getDay(Date date)
    {
        String day = new SimpleDateFormat("dd").format(date);
        return Integer.parseInt(day);
    }

    //返回当前小时分种
    public static String gethouse(Date date)
    {
        String house = new SimpleDateFormat("HH:mm").format(date);
        return house;
    }

    //返回当前星期
    public static String getweekDay(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekDays[w];
    }

}
