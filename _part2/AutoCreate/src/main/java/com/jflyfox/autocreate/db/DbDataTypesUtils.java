package com.jflyfox.autocreate.db;

import java.sql.Types;
import java.util.HashMap;

/**
 * 
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class DbDataTypesUtils {

	private final static HashMap<Integer, Object> TYPE_MAP = new HashMap<Integer, Object>();

	public static boolean isFloatNumber(String javaType) {
		if (javaType.endsWith("Float") || javaType.endsWith("Double") || javaType.endsWith("BigDecimal")
				|| javaType.endsWith("BigInteger")) {
			return true;
		}
		if (javaType.endsWith("float") || javaType.endsWith("double") || javaType.endsWith("BigDecimal")
				|| javaType.endsWith("BigInteger")) {
			return true;
		}
		return false;
	}

	public static boolean isIntegerNumber(String javaType) {
		if (javaType.endsWith("Long") || javaType.endsWith("Integer") || javaType.endsWith("Short")
				|| javaType.endsWith("Byte")) {
			return true;
		}
		if (javaType.endsWith("long") || javaType.endsWith("int") || javaType.endsWith("short")
				|| javaType.endsWith("byte")) {
			return true;
		}
		return false;
	}

	public static boolean isDate(String javaType) {
		if (javaType.endsWith("Date") || javaType.endsWith("Timestamp") || javaType.endsWith("Time")) {
			return true;
		}
		return false;
	}

	public static boolean isTimeStamp(String javaType) {
		if (javaType.endsWith("Timestamp")) {
			return true;
		}
		return false;
	}

	public static boolean isString(String javaType) {
		if (javaType.endsWith("String")) {
			return true;
		}
		return false;
	}

	public static String getPreferredJavaType(int sqlType, int size, int decimalDigits) {
		if ((sqlType == Types.DECIMAL || sqlType == Types.NUMERIC) && decimalDigits == 0) {
			if (size == 1) {
				// https://sourceforge.net/tracker/?func=detail&atid=415993&aid=662953&group_id=36044
				return "java.lang.Boolean";
			} else if (size < 3) {
				return "java.lang.Byte";
			} else if (size < 5) {
				return "java.lang.Short";
			} else if (size < 10) {
				return "java.lang.Integer";
			} else if (size < 19) {
				return "java.lang.Long";
			} else {
				return "java.math.BigDecimal";
			}
		}
		String result = (String) TYPE_MAP.get(sqlType);
		if (result == null) {
			result = "java.lang.Object";
		}
		return result;
	}

	static {
		TYPE_MAP.put(Types.TINYINT, "java.lang.Byte");
		TYPE_MAP.put(Types.SMALLINT, "java.lang.Short");
		TYPE_MAP.put(Types.INTEGER, "java.lang.Integer");
		TYPE_MAP.put(Types.BIGINT, "java.lang.Long");
		TYPE_MAP.put(Types.REAL, "java.lang.Float");
		TYPE_MAP.put(Types.FLOAT, "java.lang.Double");
		TYPE_MAP.put(Types.DOUBLE, "java.lang.Double");
		TYPE_MAP.put(Types.DECIMAL, "java.math.BigDecimal");
		TYPE_MAP.put(Types.NUMERIC, "java.math.BigDecimal");
		TYPE_MAP.put(Types.BIT, "java.lang.Boolean");
		TYPE_MAP.put(Types.BOOLEAN, "java.lang.Boolean");
		TYPE_MAP.put(Types.CHAR, "java.lang.String");
		TYPE_MAP.put(Types.VARCHAR, "java.lang.String");
		TYPE_MAP.put(Types.LONGVARCHAR, "java.lang.String");
		TYPE_MAP.put(Types.BINARY, "byte[]");
		TYPE_MAP.put(Types.VARBINARY, "byte[]");
		TYPE_MAP.put(Types.LONGVARBINARY, "byte[]");
		TYPE_MAP.put(Types.DATE, "java.sql.Date");
		TYPE_MAP.put(Types.TIME, "java.sql.Time");
		TYPE_MAP.put(Types.TIMESTAMP, "java.sql.Timestamp");
		TYPE_MAP.put(Types.CLOB, "java.sql.Clob");
		TYPE_MAP.put(Types.BLOB, "java.sql.Blob");
		TYPE_MAP.put(Types.ARRAY, "java.sql.Array");
		TYPE_MAP.put(Types.REF, "java.sql.Ref");
		TYPE_MAP.put(Types.STRUCT, "java.lang.Object");
		TYPE_MAP.put(Types.JAVA_OBJECT, "java.lang.Object");
	}

}