package com.jake.util;

public class StringUtil {
	//判断不为空
	public static boolean isEmpty(String str){
		if(str == null || str.length() < 1){
			return false;
		}else{
			return true;
		}
	}
	
	//转换字符
	public static String trans(String input){
		if(input == null){
			return input;
		}
		if(input.equals("")){
			return "";
		}
		input = input.replaceAll("&", "&amp;");
		input = input.replaceAll("<", "&lt;");
		input = input.replaceAll(">", "&gt;");
		input = input.replaceAll(" ", "&nbsp;");
		input = input.replaceAll("'", "&#39;");
		input = input.replaceAll("\"", "&quot;");
		return input.replaceAll("\n", "<br>");
	}
}
