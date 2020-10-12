package com.ilvyou.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GuanYuCai on 2016/9/28 0028.
 */
public class DateUtil {

    public static String toStr(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    public static Date offset(String dateStr, String format, int field, int offset) throws ParseException {
        if (StringUtil.isEmpty(dateStr)){
            return null;
        }

        return _offset(dateStr, format, field, offset);
    }

    public static Date offset(String format, int field, int offset) throws ParseException {
        return _offset("", format, field, offset);
    }

    private static Date _offset(String dateStr, String format, int field, int offset) throws ParseException {
        if (StringUtil.isEmpty(format)){
            format = "yyyy-MM-dd";
        }

        SimpleDateFormat df = new SimpleDateFormat(format);
        Date date = StringUtil.isEmpty(dateStr) ? new Date() : df.parse(dateStr);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, offset);
        return cal.getTime();
    }
}
