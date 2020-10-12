package com.luoqy.speedy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author qf
 *	获取一周数据 格式yyyy-MM-dd
 */
public class GetTimeUtil {
	static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * @param date 今天日期 new Date()
	 * @return 根据当前日期获得所在周的日期区间（周一和周日日期）
	 */
	public static String getTimeInterval(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		// System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		// System.out.println("所在周星期日的日期：" + imptimeEnd);
		return imptimeBegin + "," + imptimeEnd;
	}
	
	/**
	 * @return 根据当前日期获得上周的日期区间（上周周一和周日日期）
	 */
	public static String getLastTimeInterval() {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset1 = 1 - dayOfWeek;
		int offset2 = 7 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		calendar2.add(Calendar.DATE, offset2 - 7);
		// System.out.println(sdf.format(calendar1.getTime()));// last Monday
		String lastBeginDate = sdf.format(calendar1.getTime());
		// System.out.println(sdf.format(calendar2.getTime()));// last Sunday
		String lastEndDate = sdf.format(calendar2.getTime());
		return lastBeginDate + "," + lastEndDate;
	}
	/**
	 * @param dBegin 开始时间
	 * @param dEnd	结束时间
	 * @return 获取开始到结束的list集合
	 */
	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}
	
	/**
	 * 	是否在上一月区间
	 * @param date 时间格式
	 * @return
	 * @throws ParseException 
	 */
	public static boolean betweenTheLastMonthOrNot(Date date) throws ParseException{
		 //获取前一个月第一天
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH,1);
        String firstDay = sdf.format(calendar1.getTime());
        //获取前一个月最后一天
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        String lastDay = sdf.format(calendar2.getTime());
        System.out.println(firstDay+"===="+lastDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date dBegin = sdf.parse(firstDay);  
        Date dEnd = sdf.parse(lastDay); 
        //List<Date> lDate =findDates(dBegin,dEnd);
        return dBegin.getTime()<=date.getTime()&&date.getTime()<=dEnd.getTime();
	}
	public static void main(String[] args) throws ParseException {
		 /* String yz_time=getLastTimeInterval();//获取本周时间
	        String array[]=yz_time.split(",");
	        String start_time=array[0];//本周第一天
	        String end_time=array[1];  //本周最后一天 
	          //格式化日期     
	          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	          Date dBegin = sdf.parse(start_time);  
	          Date dEnd = sdf.parse(end_time);  
	          List<Date> lDate = findDates(dBegin, dEnd);//获取这周所有date
	          for (Date date : lDate)  
	          {  
	           System.out.println(sdf.format(date));  
	          }*/
		
        System.out.println();
        /*for (Date date : lDate)  
        {  
         System.out.println(sdf.format(date));  
        }*/
	}
}
