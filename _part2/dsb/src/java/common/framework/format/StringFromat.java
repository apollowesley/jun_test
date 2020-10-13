package common.framework.format;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author David Yuan
 * 
 */
public class StringFromat {
	public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	// Quit flag which means connection will be terminated.

	public static final String DATE_PATTERN_S = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * Check if the given <code>String<code> s is empty.
	 * 
	 * @param s
	 * @return Boolean
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equalsIgnoreCase(s);
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int length(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	public static String fmt(Object s, int length, boolean reverse) {
		StringBuffer sb = new StringBuffer();
		if (s != null) {
			sb.append(s);
		}
		int totalAppended = length - length(sb.toString());
		for (int i = 0; i < totalAppended; i++) {
			if (reverse) {
				sb.insert(0, " ");
			} else {
				sb.append(" ");
			}

		}
		return sb.toString();
	}

	public static String fmt(int i, int length, boolean reverse) {
		StringBuffer sb = new StringBuffer();
		sb.append(i);
		int totalAppended = length - sb.length();
		for (int j = 0; j < totalAppended; j++) {
			if (reverse) {
				sb.insert(0, " ");
			} else {
				sb.append(" ");
			}

		}
		return sb.toString();
	}

	public static String fmt(long l, int length, boolean reverse) {
		StringBuffer sb = new StringBuffer();
		sb.append(l);
		int totalAppended = length - sb.length();
		for (int j = 0; j < totalAppended; j++) {
			if (reverse) {
				sb.insert(0, " ");
			} else {
				sb.append(" ");
			}

		}
		return sb.toString();
	}

	public static String fmt(Object s, int length) {
		return fmt(s, length, false);
	}

	public static String fmt(int i, int length) {
		return fmt(i, length, false);
	}

	public static String fmt(long l, int length) {
		return fmt(l, length, false);
	}

	public static String format(Object[] content, int[] widths) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < content.length; i++) {
			sb.append(fmt(content[i], widths[i]));
		}
		return sb.toString();
	}

	public static String format(String[] content, int[] widths) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < content.length; i++) {
			sb.append(fmt(content[i], widths[i]));
		}
		return sb.toString();
	}

	public static void printArray(PrintWriter out, char[] ary, int offset, int len) {
		for (int i = 0; i < len; i++) {
			out.print(ary[offset + i]);
		}
	}

	public static String getCurrentTime() {
		DateFormat df = new SimpleDateFormat(DATE_PATTERN);
		return df.format(new Date());
	}

	public static String getTime(long time) {
		DateFormat df = new SimpleDateFormat(DATE_PATTERN);
		return df.format(new Date(time));
	}

	public static String getTimes(long time) {
		DateFormat df = new SimpleDateFormat(DATE_PATTERN_S);
		return df.format(new Date(time));
	}

	public static void main(String args[]) {
		String stime = getTimes(System.currentTimeMillis());
		System.out.println(stime);
		String stime2 = getTimes(System.currentTimeMillis());
		System.out.println(stime2);
	}
}
