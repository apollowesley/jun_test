package org.nature.framework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ocean on 2016/3/24.
 */
public class DateUtil {
    public static String dateToStr(Date date,String formate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
        return simpleDateFormat.format(date);
    }

    public static Date operDate(Date date,int day){
        Calendar calendar=Calendar.getInstance();
        long time = date.getTime();
        calendar.setTimeInMillis(time + (1000 * 60 * 60 * 24) * day);
        return calendar.getTime();
    }

}
