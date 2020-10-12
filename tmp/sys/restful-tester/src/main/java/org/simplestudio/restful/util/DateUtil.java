package org.simplestudio.restful.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static String format(Date date) {
        return DEFAULT_DATE_FORMAT.format(date);
    }
}
