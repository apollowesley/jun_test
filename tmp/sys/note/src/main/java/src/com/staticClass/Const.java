package com.staticClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Const {
	public static String TAG = "Const";
	/**
	 * 正则表达式： 用户名，3-25位中文英文数字和_-
	 */
	public static String regexp_username = "^[\u4e00-\u9fa5a-z0-9_-]{3,25}$";
	/**
	 * 正则表达式：双字节
	 */
	public static String regexp_doublebyte = "[^\\x00-\\xff]";
	/**
	 * 正则表达式：email
	 */
	public static String regexp_email = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	/**
	 * 正则表达式：手机号码
	 */
	public static String regexp_telephone = "^(1[3|4|5|8]{1}[0-9]{9})$";
	/**
	 * sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	 */
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm")
	 */
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/**
	 * sdf2 = new SimpleDateFormat("yyyy-MM-dd")
	 */
	public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * yyyy-MM-dd 转 date
	 * 
	 * @param str
	 * @param format
	 * @return
	 * @throws ParseException
	 * @createTime 2014-9-13 下午9:40:08
	 */
	public static Date getDate(String str, SimpleDateFormat format) throws ParseException {
		return format.parse(str);
	}

	public static String getDateString() {
		return Const.sdf1.format(new Date());
	}

	/**
	 * 参数类型为时间戳
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 * @createTime 2014-9-13 下午9:36:25
	 */
	public static String getDateString(String time) throws ParseException {
		return Const.sdf1.format(Long.valueOf(time, 10));
	}

	/**
	 * return true on string is null or empty replaced by stringIsEmpty(String
	 * s)
	 * 
	 * @return
	 */
	@Deprecated
	public static boolean StringIsNull(String s) {
		return s == null ? true : s.equals("") ? true : false;
	}

	/**
	 * return true on string is null or empty
	 * 
	 * @return
	 */
	public static boolean stringIsEmpty(String s) {
		return s == null ? true : s.equals("") ? true : false;
	}

	/**
	 * return true on string is null or empty or blank
	 * 
	 * @return
	 */
	public static boolean stringIsBlank(String s) {
		return s == null ? true : s.trim().equals("") ? true : false;
	}

	/**
	 * 过滤掉字符串中的HTML标签
	 * 
	 * @author 刘一波
	 * @date 2014年8月28日下午1:35:32
	 * @param string
	 * @return
	 */
	public static String filterHTML(String string) {
		// 过滤掉html标签
		Pattern p_html = Pattern.compile("<[^>]*?>", Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(string);
		string = m_html.replaceAll(""); // 过滤html标签
		return string;
	}

	/**
	 * 删除sql语句中多余的空格
	 * 
	 * @author 刘一波
	 * @date 2014年9月4日下午4:54:11
	 * @param string
	 * @return
	 */
	public static String RemoveExtraSpaces(String string) {
		Pattern p_space = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(string);
		string = m_space.replaceAll(" ");
		return string;
	}

	public static boolean ListIsNotBlank(List<?> list) {
		return list != null && list.size() > 0 && list.get(0) != null;
	}

	/**
	 * 字符串为null则返回""，否则返回原字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String StringToString(Object s) {
		return (s == null ? "" : s).toString();
	}

	/**
	 * 截取一个字符串的前length个字符
	 * 
	 * @param str
	 * @param length
	 * @return
	 * @createTime 2014-12-15 下午10:05:15
	 */
	public static String subString(String str, int length) {
		return subString(str, length, "...");
	}

	/**
	 * 
	 * @param str
	 * @param length
	 * @param suffix
	 *            截取字符串后的后缀 比如 ...
	 * @return
	 */
	public static String subString(String str, int length, String suffix) {
		str = StringToString(str);
		if (length < 0 || str.length() < length) {
			return str;
		} else {
			return str.substring(0, length) + suffix;
		}
	}

	public static String ArraysToString(Object[] a, String split) {
		if (Const.stringIsBlank(split)) {
			split = ",";
		}
		if (a == null) {
			return "";
		}
		int iMax = a.length - 1;
		if (iMax == -1) {
			return "";
		}

		StringBuilder b = new StringBuilder();

		for (int i = 0;; i++) {
			b.append(String.valueOf(a[i]));
			if (i == iMax) {
				return b.toString();
			}
			b.append(split);
		}
	}

	/**
	 * 
	 * @param ids
	 * @return 'id1','id2','id3','id4'
	 * @createTime 2015-4-22 上午12:42:28
	 */
	public static String IdsToIn(String ids) {
		if (!Const.stringIsBlank(ids)) {
			StringBuilder sb = new StringBuilder();
			String[] strs = ids.split(",");
			for (int i = 0; i < strs.length; i++) {
				sb.append("'");
				sb.append(strs[i]);
				sb.append("',");
			}
			sb.setLength(sb.length() - 1);
			return sb.toString();
		}
		return "''";
	}
}
