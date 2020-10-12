package com.szfore.util.basic;

import java.io.UnsupportedEncodingException;

public class StringUtil {
	
	public static final String ERROR_INFO = "输入的文件路径不合法";
	
	/**
	 * 截取文件路径中文件名，不带后缀名
	 * 例如：
	 * 输入：    f://dota/tk.txt
	 * 返回：    tk  
	 * @param filePath
	 * @return
	 */
	public static String subFileName(String filePath)
	{
		if(null == filePath || "".equals(filePath) || !filePath.contains("."))
		{
			return ERROR_INFO;
		}
		
		String slash = filePath.contains("\\") ? "\\" : "/";
		
		if(filePath.contains(slash))
		{
			int slashIndex = filePath.lastIndexOf(slash);
			filePath = filePath.substring(slashIndex + 1, filePath.length());
		}
		
		int pointIndex = filePath.lastIndexOf(".");
		filePath = filePath.substring(0, pointIndex);
		
		return filePath;
	}
	
	/**
	 * 截取文件路径后缀名，不返回点
	 * 例如：
	 * 输入：    f://dota/tk.txt
	 * 返回：    txt
	 * @param filePath
	 * @return
	 */
	public static String subFileExt(String filePath)
	{
		if(null == filePath || "".equals(filePath) || !filePath.contains("."))
		{
			return ERROR_INFO;
		}
		
		int index = filePath.lastIndexOf(".");
		
		return filePath.substring(index + 1);
	}
	
	/**
	 * 返回字符串中英文字符的个数
	 * @param str
	 * @return
	 */
	public static int calCharNum(String str)
	{
		return (str.length() * 3 - str.getBytes().length) / 2;
	}
	
	
	/**
	 * 返回字符串中大写字母的个数
	 * @param str
	 * @return
	 */
	public static int calUpperCaseNum(String str)
	{
		char[] chars = str.toCharArray();
		int charNum = 0;
		for (char c : chars) 
		{
			if(Character.isUpperCase(c))
			{
				charNum++;
			}
		}
		return charNum;
	}
	
	/**
	 * 获取字符串的16进制值
	 * @param str
	 * @return
	 */
	public static String getHexcode(String str)
	{
		char[] chars = str.toCharArray();
		StringBuilder builder = new StringBuilder();
		for (char c : chars) 
		{
			builder.append(Integer.toHexString(c));
		}
		return builder.toString();
	}
	
	/**
	 * 替换字符
	 * @param srcStr
	 * @param replacement
	 * @param regexs
	 * @return
	 */
	public static String replaceAll(String srcStr, String replacement, String... regexs)
	{
		for (String regex : regexs) 
		{
			System.out.println("regex: " + regex);
			srcStr = srcStr.replaceAll(regex, replacement);
		}
		return srcStr;
	}
	
	/**
	 * 将字符串转化为gb2312编码的字符串
	 * @param str
	 * @return
	 */
	public static String getGbStr(String str)
	{
		try {
			return new String(str.getBytes(), "gb2312");
		} catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		return str;
	}

	public static int calVirtualChineseNum(String mixedStr)
	{
		int charNum = calCharNum(mixedStr);
		int upperCaseNum = calUpperCaseNum(mixedStr);
		
		//两个英文半角小写字符相当于一个汉字, 三个大写字符相当于两个汉字
		double chineseNumOfChar = Double.valueOf((charNum - upperCaseNum) + ".0") * 0.5;
		double chineseNumOfUpperCase = Double.valueOf(upperCaseNum + ".0") / 3 * 2;
		
		int virtualChineseNum = (int) Math.round(chineseNumOfChar + chineseNumOfUpperCase);
		int actualChineseNum = mixedStr.length() - charNum; 
		
		return virtualChineseNum + actualChineseNum;
	}

	
	/**
	 * 在chars内每个字符用dilimer隔开
	 * @param chars
	 * @param dilimer
	 * @return
	 */
	public static String seperateCharsWith(String chars, String dilimer) 
	{
		StringBuilder str = new StringBuilder();
		if(chars != null)
		{
			for (char c : chars.toCharArray()) 
			{
				str.append(c).append(dilimer);
			}
		}
		return str.lastIndexOf(dilimer) > 0 ? str.substring(0, str.length() - dilimer.length()) : str.toString();
	}
	
}
