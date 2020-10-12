package com.luoqy.speedy.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.luoqy.speedy.data.MySqldbUtil;
/**
 *  抛出异常信息记录
 * */
public class AllException {
	/**
	 * 记录异常信息
	 * @param e  异常
	 */
	public static void recordException(Exception e){
		//e.printStackTrace();
		StackTraceElement stackTraceElement= e.getStackTrace()[e.getStackTrace().length-1];
		String errordesc=e.toString();//错误描述
		String methods=stackTraceElement.getClassName()+"."+stackTraceElement.getMethodName();//错误方法
		String files=stackTraceElement.getFileName();//错误文件名
		int lines=stackTraceElement.getLineNumber();//错误行数
		String select="select * from speedy_errorlog where errordesc='"+errordesc+"' and line='"+lines+"'";
		Object result=MySqldbUtil.mysqlSelect(select, "none",null);
		if(null!=result){
			System.out.println("已存在的错误");
		}else{
			System.out.println("新的错误");
			String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String sqlStr="insert into speedy_errorlog set errordesc='"+errordesc+"',methods='"+methods+"',files='"+files+"',line='"+lines+"',time='"+time+"'";
			MySqldbUtil.mysqlExecute(sqlStr,null);
		}
	}
}
