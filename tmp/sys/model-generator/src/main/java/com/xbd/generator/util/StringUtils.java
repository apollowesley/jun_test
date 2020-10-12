package com.xbd.generator.util;

public class StringUtils {
	public static int intValue(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		} else {
			return Integer.parseInt(obj.toString());
		}
	}

	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}

	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	public static String join(String[] src, String separator, String quot) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < src.length; i++) {
			if (i > 0 && separator != null) {
				sb.append(separator);
			}
			if (quot != null) {
				sb.append(quot);
			}
			sb.append(src[i]);
			if (quot != null) {
				sb.append(quot);
			}
		}
		return sb.toString();
	}

	public static String safeToString(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	public static String les(Object obj, int len) {
		if (obj == null || obj.toString() == null) {
			return null;
		} else {
			if (obj.toString().length() > len) {
				return obj.toString().substring(0, len);
			} else {
				return obj.toString();
			}
		}
	}

	public static String toLowerCaseFirst(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		char[] chs = str.toCharArray();
		chs[0] = Character.toLowerCase(chs[0]);
		return String.valueOf(chs);
	}

	public static String toUpperCaseFirst(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		char[] chs = str.toCharArray();
		chs[0] = Character.toUpperCase(chs[0]);
		return String.valueOf(chs);
	}

	public static String getJavaType(String strTemp) {
		String strJavaType = "";
		strTemp = strTemp.toLowerCase();
		if (strTemp.indexOf("int") >= 0) {
			strJavaType = "Integer";
		} else if (strTemp.indexOf("long") >= 0) {
			strJavaType = "Long";
		} else if (strTemp.indexOf("float") >= 0) {
			strJavaType = "Float";
		} else if (strTemp.indexOf("double") >= 0) {
			strJavaType = "Double";
		} else if (strTemp.indexOf("boolean") >= 0) {
			strJavaType = "Boolean";
		} else if (strTemp.indexOf("string") >= 0) {
			strJavaType = "String";
		} else if (strTemp.indexOf("date") >= 0) {
			strJavaType = "java.util.Date";
		} else if (strTemp.indexOf("materialized_clob") >= 0) {
			strJavaType = "String";
		} else if (strTemp.indexOf("materialized_blob") >= 0) {
			strJavaType = "byte[]";
		} else if (strTemp.indexOf("big_decimal") >= 0) {
			strJavaType = "java.math.BigDecimal";
		} else {
			throw new RuntimeException(strTemp + "没有对应的java类型！");
		}
		return strJavaType;
	}

}