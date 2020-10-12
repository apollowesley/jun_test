
package com.lijian.devutils.utils;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class StringUtils {
	
	/**
	 * 格式化id集合 转化为sql的id集合
	 * 
	 * @param str
	 *            如：1,2,3
	 * @return
	 */
	public static String formateIdsToSql(String str) {
		if (str.indexOf(",") > 0) {
			String id = ""; 
			if (str.endsWith(","))
				str = str.substring(0, str.length() - 1);
			String[] ids = str.split(",");
			for (int i = 0; i < ids.length - 1; i++) {
				id += "'" + ids[i] + "',";
			}
			id += "'" + ids[ids.length - 1] + "'";
			return id;
		}
		return "'" + str + "'";
	} 
	
	public static String[] formateToArray(String str){
		if(null!=str&&!("".equals(str))){
			if(str.indexOf(",")==0&&str.length()>1){
				str=str.substring(1, str.length());
			}else{
				if(str.length()<=1){
					return new String[]{};
				}
			}
			String[]splits=str.split(",");
			return splits;
		}else{
			return new String[]{};
		}
	}
	
	public static String arrayToString(String[] values) {
        if (null == values) return "";
        
        StringBuffer buffer = new StringBuffer(values.length);
        for (int i = 0; i < values.length; i++) {
            buffer.append(values[i]).append(",");
        }
        if (buffer.length() > 0) {
            return buffer.toString().substring(0, buffer.length() - 1);
        }
        return "";
    }
    /**
     * 字符串 判空
     * @param s 字符串
     * @return null ->true
     * 			"" ->true 
     */
    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isTrimedEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }
    
    public static boolean isLineBroken(String s) {
		if ( null == s ) {
			return false;
		}
		if  (s.contains("\n")) {
			return true;
		}
		if (s.contains("\r\n")) {
			return true;
		}
		return false;
	}
    
    /**
	 * 数字的金额表达式
	 * @param num 数字
	 * @param locale 本地化， 如果为 null 则为 Locale.CHINA
	 * @return
	 */
	public static String convertNumToMoney(int num,Locale locale){
		if(locale==null){
			locale=Locale.CHINA;
		}
		NumberFormat formatc = NumberFormat.getCurrencyInstance(locale);
		String strcurr = formatc.format(num);
		//System.out.println(strcurr);
		//num = NumberFormat.getInstance().setParseIntegerOnly(true));
		return strcurr;
	}
	
	/*
	 *
	 */
	private static String delSQlString(String sql){
	    String delSql = "in(";
	    StringTokenizer Tokenizer = new StringTokenizer(sql,"|");

	    // 标记本身等于分隔符的特殊情况
	    delSql += Tokenizer.nextToken().toString();
	    while (Tokenizer.hasMoreTokens()) {
	    	delSql += Tokenizer.nextToken() + ",";
	    }
	    delSql = delSql.substring(0,delSql.length()-1) + ")";
	    return delSql;
	}

	/*
	 * format selectedIDs to sql language
	 * in (...)
	 * second of methods bt own idea
	 */
	private String delNewSQlString(String sql){
		return "in (" + sql.replace('|',',') + ")";
	}
	
	/**
	 * 字符串替换操作
	 * @param originString 原字符串
	 * @param oldString 被替换字符串
	 * @param newString 替换字符串
	 * @return 替换操作后的字符串
	 */
	public static String replace(String originString,String oldString,String newString){
		String getstr = originString;
		while(getstr.indexOf(oldString)>0){
			getstr = getstr.substring(0,getstr.indexOf(oldString)) + newString + getstr.substring(getstr.indexOf(oldString)+oldString.length(),getstr.length());
		}
		return getstr;
	}
	
	/**
	 * html 转换
	 *  < -- &lt  > -- &gt ' ' -- &nbsp \\ -- &acute
	 * @param input
	 * @return
	 */
	public static String convertHtml(String input){
		StringBuffer returnString = new StringBuffer(input.length());

	    char ch = ' ';
	    for (int i = 0;i<input.length();i++){

	    	ch = input.charAt( i);

	    	if (ch == '<'){
	    		returnString = returnString.append("&lt");
	    	}else if (ch == '>'){
	    		returnString = returnString.append("&gt");
	    	}else if (ch == ' '){
	    		returnString = returnString.append("&nbsp");
	    	}else if (ch == '\\'){
	    		returnString = returnString.append("&acute");
	    	}else{
	    		returnString = returnString.append(ch);
	    	}
	    }
	    return returnString.toString();
	}
	
	/**
	 * * 获取指定日期是星期几 参数为null时表示获取当前日期是星期几
	 * 
	 * @param date 日期
	 * @param flag
	 *            zh/en
	 * @return
	 */
	public static String getWeekOfDate(Date date, String flag) {
		String[] weekOfDays=null;
		if ("en".equals(flag)) {
			weekOfDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		} else {
			weekOfDays = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		}
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekOfDays[w];
	}
	
	/**
	 * 将字符串转换成ASCII码
	 * @param cnStr
	 * @return String
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		// 将字符串转换成字节序列
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			// System.out.println(Integer.toHexString(bGBK[i] & 0xff));
			// 将每个字符转换成ASCII码
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}
	
	/**
	 * 将实体list转换为前端封装的list,要求 K 中 有个方法  fromEntity(entity, except)
	 * 
	 * @param entityList
	 *            需要封装的实体list
	 * @param c
	 *            需要转换的facade的class
	 * @param except
	 *            需要除去的屬性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	//public static <T extends BaseEntity, K extends BaseFacade<T>> List<K> changeEntity2Facade(List<T> entityList, Class<?> c, String[] except) {
//	public static <T, K> List<K> changeEntity2Facade(List<T> entityList, Class<?> c, String[] except) {
//		List<K> facadeList = new ArrayList<K>();
//		if (entityList != null && entityList.size() > 0) {
//			for (int i = 0; i < entityList.size(); i++) {
//				try {
//					K e = (K) c.newInstance();
//					e.fromEntity(entityList.get(i), except);
//					facadeList.add(e);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return facadeList;
//	}
	
	
	
	
	public static void main(String[] args) {
		//String[]s=formateToArray(",a,c,b,2132312,");
//		for(int i=0;i<s.length;i++){
//			
//			System.out.println(s[i]);
//		}
//		
		//System.out.println(arrayToString(s));
		
		//System.out.println(getWeekOfDate(null, null));
		
	}

}
