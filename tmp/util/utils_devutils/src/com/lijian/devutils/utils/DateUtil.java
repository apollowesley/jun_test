package com.lijian.devutils.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
	private static final SimpleDateFormat dt14 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat dtlong14 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat dt8 = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat dt6_ = new SimpleDateFormat("yyyy-MM");
	private static final SimpleDateFormat dt10 = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat dt10Ch = new SimpleDateFormat("yyyy年MM月dd日");
	private static final SimpleDateFormat dt8Ch = new SimpleDateFormat("yyyy年MM月");
	private static final SimpleDateFormat dt6 = new SimpleDateFormat("yyyyMM");
	private static final SimpleDateFormat time8 = new SimpleDateFormat("HHmmss");
	private static final SimpleDateFormat dtDay = new SimpleDateFormat("MM-dd");
	private static final SimpleDateFormat shortDay = new SimpleDateFormat("MMdd");
	private static final SimpleDateFormat dtfen14 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	private static final SimpleDateFormat day = new SimpleDateFormat("dd");
	
	public static String dt14LongFormat(Date date) {
		return dtlong14.format(date);
	}
	
	public static String dtDayFormat(Date date) {
		return dtDay.format(date);
	}

	public static String dt8ChFromDate(Date date) {
		return dt8Ch.format(date);
	}
	
	public static String time8Format(Date date) {
		return time8.format(date);
	}
	
	public static String dt10ChFromDate(Date date) {
		return dt10Ch.format(date);
	}
	
	public static String dt6FromDate(Date date) {
		return dt6.format(date);
	}
	public static String dt6_FromDate(Date date) {
		return dt6_.format(date);
	}
	
	public static String dt10FromDate(Date date) {
		return dt10.format(date);
	}
	
	public static Date dt10FromStr(String date) {
		try {
			return dt10.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	public static String dtfen14FromDate(Date date) {
		return dtfen14.format(date);
	}
	
	public static Date dtfen14FromStr(String date) {
		try {
			return dtfen14.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String dt14FromDate(Date date) {
		return dt14.format(date);
	}
	
	public static Date dtlong14FromStr(String date){
		try {
			return dtlong14.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date dt14ShortFromStrS(String time) {
		try {
			return dt14.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date dt14FromStr(String time){
		try {
			return dtlong14.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date dt8StrToDate(String time){
		try {
			return dt8.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String dt8FormDate(Date date) {
		return dt8.format(date);
	}
	
	public static String dtlong14FromDate(Date date) {
		if (date == null) {
			return "";
		}
		return dtlong14.format(date);
	}
	
	public static Date getBeginDateTime(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	public static Date getEndDateTime(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}
	
	public static Date getThisMonthFirstDay(Date date)  throws ParseException {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	public static Date getThisMonthEndDay(Date date)  throws ParseException {
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH,1);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.add(Calendar.DAY_OF_YEAR,-1);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}
	
	public static long getNextDay(int hour){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR,1);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}
	
	public static long getMonthDayLong(int hour, int day){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH,1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}
	
	public static Date getMonthDay(int hour, int day){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH,1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	public static Date getWeekStart(Date date){
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		
		return c.getTime();
	}
	
	public static Date getWeekEnd(Date date){
		if (date == null) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND,59);
		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		return c.getTime();
	}
	
	 public static  int[]  getDateLength(String  fromDate, String  toDate)  { 
	      Calendar  c1  =  getCal(fromDate); 
	      Calendar  c2  =  getCal(toDate); 
	      int[]  p1  =  {  c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)  }; 
	      int[]  p2  =  {  c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH)  }; 
	      return  new  int[]  {  p2[0]  -  p1[0], p2[0]  *  12  +  p2[1]  -  p1[0]  *  12  -  p1[1], (int)  ((c2.getTimeInMillis()  -  c1.getTimeInMillis())  /  (24  *  3600  *  1000))  }; 
	   } 
	 
	   static  Calendar  getCal(String  date)  { 
	      Calendar  cal  =  Calendar.getInstance(); 
	      cal.clear(); 
	      cal.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7))  -  1, Integer.parseInt(date.substring(8, 10))); 
	      return  cal; 
	   } 
	   
	   public static Date getThreeMonthAgo(Date date){
		   if (date == null) {
				date = new Date();
			}
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, -3);
//			c.setFirstDayOfWeek(Calendar.MONDAY);
//			c.set(Calendar.HOUR_OF_DAY,23);
//			c.set(Calendar.MINUTE,59);
//			c.set(Calendar.SECOND,59);
//			c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
			return c.getTime();
	   }
	
	public static void main(String[] arg) throws ParseException {
		
		System.out.println((getThreeMonthAgo(null)));
		System.out.println(dt6_FromDate(getThreeMonthAgo(null)));
		
//		System.out.println(DateUtil.dt10ChFromDate(DateUtil.dtfen14FromStr("2016-06-24")));
		System.out.println(DateUtil.dt14LongFormat(DateUtil.getOneHourLater(null)));
		//System.out.println(DateUtil.dtfen14FromStr("2016-06-24 14:30:25"));
		 /*int  ret[]  =  getDateLength(dt14LongFormat(new Date()),"2016-07-25");  
	     System.out.println(ret[0]  +  ": "  +  ret[1]  +  ": "  +  ret[2]); */
		//System.out.println(DigestUtils.md5Hex("20160419"));
		//System.out.println("京".substring(0,2));
		//System.out.println(dt10FromDate(getThisMonthFirstDay(dt10FromStr("2016-02-22"))));
		//System.out.println(dt10FromDate(getThisMonthEndDay(dt10FromStr("2016-02-22"))));
		//System.out.println("2016-04-03 23:59:59".substring(0,10));
	}    
	
	public static long calculateDayGap(Date startDate, Date endDate) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		return (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24) + 1;
	}
	public static String shortDayFormat(Date date) {
		return shortDay.format(date);
	}
	
	public static int calculateDateYearGap(String startDate, String endDate) throws ParseException {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(dt10FromStr(startDate));
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(dt10FromStr(endDate));
		int extYear = 0;
		//如果开始日期的日月<=结束日期的日月则extyear=1
		if (shortDayFormat(cal2.getTime()).compareTo(shortDayFormat(cal1.getTime())) >=0 ) {
			extYear = 1;
		}
		return cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + extYear;
	}
	
	public static Date getNextYearDateFromStr(String date) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(dt10FromStr(date));
		c.add(Calendar.YEAR, 1);
		c.add(Calendar.DAY_OF_YEAR, -1);
		return c.getTime();
	}
	public static Date getOneHourLater(Date date) {
		if(date==null){
			date=new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, 1);
		return c.getTime();
	}
	 
	public static Date extractTimeFromStr(String date) {
		Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
	    Matcher matcher = pattern.matcher(date);
	    String dateStr = null;
	    if (matcher.find()) {
	    	dateStr = matcher.group(0);
	    }
	    if (dateStr != null) {
	    	return dt14FromStr(dateStr);
	    } else {
	    	pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{2}");
	    	matcher = pattern.matcher(date);
	    	if (matcher.find()) {
		    	dateStr = matcher.group(0);
		    }
	    	if (dateStr != null) {
	    		return dt10FromStr(dateStr);
	    	} else {
	    		return null;
	    	}
	    }
	}
	
}
