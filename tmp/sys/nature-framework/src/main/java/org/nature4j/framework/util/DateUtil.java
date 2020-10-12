package org.nature4j.framework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

public class DateUtil {
	
	/**yyyy-MM-dd HH:mm:ss:SSS*/
	public static String dateTimes(){
		return dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss:SSS");
	}
	
	/**yyyy-MM-dd HH:mm:ss*/
	public static String dateTime(){
		return dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
	}
	
	/**yyyy-MM-dd*/
	public static String date(){
		return dateToStr(new Date(),"yyyy-MM-dd");
	}
	
    public static String dateToStr(Date date,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Date operDate(Date date,int day){
        Calendar calendar=Calendar.getInstance();
        long time = date.getTime();
        calendar.setTimeInMillis(time + (1000 * 60 * 60 * 24) * day);
        return calendar.getTime();
    }
    
    
    
    /**
     * 格式化时间段
     * @param times
     * @return
     */
    public static String formatTimespan(Object times) { 
		long  span = CastUtil.castLong(times);
        long minseconds = span % 1000; 
        span = span / 1000; 
        long seconds = span % 60; 
        span = span / 60; 
        long mins = span % 60; 
        span = span / 60; 
        long hours = span % 24; 
        span = span / 24; 
        long days = span;
        Formatter f = new Formatter();
        String spanStr = f.format("%1$d天%2$d时%3$d分%4$d秒", days, hours, mins, seconds, minseconds).toString();
        f.close();
        return spanStr; 
    } 

}
