package com.freedom.storage.hdfs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class MyDateFormat {
	private SimpleDateFormat simpleDateFormat = null;
	private HashMap<String, Integer> keys = new HashMap<String, Integer>();
	
	public MyDateFormat(String str) {
		if (null != str) {
			String[] strArray = str.split("&");
			int length = strArray.length;
			if (length >= 1) {
				// 至少有1个
				simpleDateFormat = new SimpleDateFormat(strArray[0]);
			}
			// 如果有其它的
			for (int index = 1; index < length; index++) {
				String currentStr = strArray[index];
				String[] array = currentStr.split("/");
				if (null != array && array.length == 2) {
					String key = array[0];
					int value = Integer.parseInt(array[1]);
					keys.put(key, value);
				}
			}
		}
	}

	public String format(Date date) {
		// 根据需要对date进行改造
		Set<String> sets = keys.keySet();
		for (String k : sets) {
			// 对定制化的部分进行修改
			if (k.equals("mm")) {// 对分钟进行定制
				int minutes = date.getMinutes();
				int v = keys.get(k);
				minutes /= v;
				minutes *= v;
				date.setMinutes(minutes);
			} else if (k.equals("HH")) {// 对小时进行定制
				int hours = date.getHours();
				int v = keys.get(k);
				hours /= v;
				hours *= v;
				date.setHours(hours);
			} else {
				// 其它的暂时不支持
			}
		}
		return this.simpleDateFormat.format(date);
	}

	private static String  MINUTES_DIVIDED_BY_10= "/yyyy-MM-dd/HH/mm/&mm/10";
	private static String  COMPLICATED="/yyyy-MM-dd/HH/mm/&mm/10&HH/3";
	public static void main(String[] args) {
		//MyDateFormat mdf = new MyDateFormat(MINUTES_DIVIDED_BY_10);
		
		System.out.println(MyInformation.TIME_FORMAT.get().get("/yyyy/MM/dd/HH/").format(new Date()));
		System.out.println(MyInformation.TIME_FORMAT.get().get(MINUTES_DIVIDED_BY_10).format(new Date()));
		System.out.println(MyInformation.TIME_FORMAT.get().get(COMPLICATED).format(new Date()));
		//System.out.println(mdf.format(new Date(2015 - 1900, 12 - 1, 14, 13, 0, 20)));
		//System.out.println(mdf.format(new Date(2015 - 1900, 12 - 1, 14, 13, 1, 20)));
		//System.out.println(mdf.format(new Date(2015 - 1900, 12 - 1, 14, 13, 12, 20)));
		//System.out.println(mdf.format(new Date(2015 - 1900, 12 - 1, 14, 13, 22, 20)));
		//System.out.println(mdf.format(new Date(2015 - 1900, 12 - 1, 14, 13, 32, 20)));
		//System.out.println(mdf.format(new Date(2015 - 1900, 12 - 1, 14, 13, 39, 20)));
		//System.out.println(mdf.format(new Date(2015 - 1900, 12 - 1, 14, 13, 42, 20)));
		//System.out.println(mdf.format(new Date(2015 - 1900, 12 - 1, 14, 13, 59, 20)));
	}

}
