package com.ly.utils.string;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 各种id生成策略
 * 
 * @version 1.0
 */
public class StringID {

	/**
	 * 取当前时间的长整形值包含纳秒，加4喂随机数
	 */
	public static String getNano() {
		return System.nanoTime() + getRandom(4);
	}
	/**
	 * 取当前时间的长整形值包含毫秒秒，加3喂随机数
	 */
	public static String getMillis() {
		return System.currentTimeMillis() + getRandom(3);
	}
	/**
	 * 取当前时间精确到毫秒
	 */
	public static String getTime() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}
	/**
	 * 取当前时间精确到毫秒、加4位随机数
	 */
	public static String getTimes() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + getRandom(4);
	}
	
	/**
	 * 随机获取place位数
	 */
	public static String getRandom(int place){
		return String.format("%0"+place+"d", (long)(Math.random()*Math.pow(10, place)));
	} 
}
