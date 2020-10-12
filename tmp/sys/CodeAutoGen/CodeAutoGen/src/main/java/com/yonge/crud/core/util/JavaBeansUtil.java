package com.yonge.crud.core.util;

import java.sql.Types;
import java.util.regex.Pattern;

import com.yonge.crud.core.db.model.Column;

public class JavaBeansUtil {

	private static final Pattern allowedClassNames = Pattern
			.compile("[a-zA-Z]+");

	public static String fromJdbcToJava(Column column) {
		String answer = null;
		switch (column.getDataType()) {
		case Types.ARRAY:
		case Types.DATALINK:
		case Types.DISTINCT:
		case Types.JAVA_OBJECT:
		case Types.NULL:
		case Types.OTHER:
		case Types.REF:
		case Types.STRUCT:
			answer = "Object";
			break;

		case Types.BIGINT:
			answer = "long";
			break;

		case Types.BINARY:
		case Types.BLOB:
		case Types.LONGVARBINARY:
		case Types.VARBINARY:
			answer = "byte[]"; //$NON-NLS-1$
			break;

		case Types.BIT:
		case Types.BOOLEAN:
			answer = "boolean";
			break;
		case Types.CHAR:
		case Types.CLOB:
		case Types.LONGVARCHAR:
		case Types.VARCHAR:
			answer = "String";
			break;

		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
			answer = "java.util.Date";
			break;

		case Types.DECIMAL:
			if (column.getScale() > 0 || column.getLength() > 18) {
				answer = "java.math.BigDecimal";
			} else if (column.getLength() > 9) {
				answer = "long";
			} else if (column.getLength() > 4) {
				answer = "int";
			} else {
				answer = "short";
			}
			break;

		case Types.DOUBLE:
			answer = "double";
			break;

		case Types.FLOAT:
		case Types.REAL:
			answer = "float";
			break;

		case Types.INTEGER:
			answer = "int";
			break;

		case Types.NUMERIC:
			if (column.getScale() > 0 || column.getLength() > 18) {
				answer = "java.math.BigDecimal";
			} else if (column.getLength() > 9) {
				answer = "long";
			} else if (column.getLength() > 4) {
				answer = "int";
			} else {
				answer = "short";
			}
			break;

		case Types.SMALLINT:
			answer = "short";
			break;

		case Types.TINYINT:
			answer = "byte";
			break;

		default:
			answer = null;
			break;
		}

		return answer;
	}

	public static String getClassName(String tableName) {
		String className = tableName;
		if (allowedClassNames.matcher(className).matches()) {
			className = tableName;
		} else {
			for (String chunk : allowedClassNames.split(tableName)) {
				className = className.replace(chunk, "");
			}
		}
		if (Character.isLowerCase(className.charAt(0))) {
			className = className.substring(0, 1).toUpperCase()
					+ className.substring(1);
		}
		return className;
	}

	public static String getCamelCaseString(String inputString,
			boolean firstCharacterUppercase) {
		StringBuilder sb = new StringBuilder();

		boolean nextUpperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);

			switch (c) {
			case '_':
			case '-':
			case '@':
			case '$':
			case '#':
			case ' ':
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
				break;

			default:
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
				break;
			}
		}

		if (firstCharacterUppercase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}

		return sb.toString();
	}

}
