package cn.backflow.generator.provider.db.table.model.util;

import cn.backflow.generator.util.typemapping.DatabaseDataTypesUtils;
import cn.backflow.generator.provider.db.table.model.Column;

public class ColumnHelper {

	public static String[] removeHibernateValidatorSpecialTags(String str) {
		if (str == null || str.trim().length() == 0)
			return new String[] {};
		return str.trim().replaceAll("@", "").replaceAll("\\(.*?\\)", "").trim().split("\\s+");
	}

	/** 得到JSR303 bean validation的验证表达式 */
	public static String getHibernateValidatorExpression(Column c) {
		if (!c.isPk() && !c.isNullable()) {
			if (DatabaseDataTypesUtils.isString(c.getJavaType())) {
				return "@NotBlank " + getNotRequiredHibernateValidatorExpression(c);
			} else {
				return "@NotNull " + getNotRequiredHibernateValidatorExpression(c);
			}
		} else {
			return getNotRequiredHibernateValidatorExpression(c);
		}
	}

	public static String getNotRequiredHibernateValidatorExpression(Column c) {
		String result = "";
		if (c.getSqlName().indexOf("mail") >= 0) {
			result += "@Email ";
		}
		if (DatabaseDataTypesUtils.isString(c.getJavaType())) {
			result += String.format("@Length(max=%s)", c.getSize());
		}
		if (DatabaseDataTypesUtils.isIntegerNumber(c.getJavaType())) {
			String javaType = DatabaseDataTypesUtils.getPreferredJavaType(c.getSqlType(), c.getSize(), c.getDecimalDigits());
			if (javaType.toLowerCase().indexOf("short") >= 0) {
				result += " @Max(" + Short.MAX_VALUE + ")";
			} else if (javaType.toLowerCase().indexOf("byte") >= 0) {
				result += " @Max(" + Byte.MAX_VALUE + ")";
			}
		}
		return result.trim();
	}

	/** 得到jquery validation的验证表达式 */
	public static String getJQueryValidation(Column c) {
		StringBuilder builder = new StringBuilder();
		if (c.getSqlName().indexOf("mail") >= 0) {
			builder.append("email: true, ");
		}
		if (c.getSqlName().indexOf("url") >= 0) {
			builder.append("url: true, ");
		}
		if (DatabaseDataTypesUtils.isFloatNumber(c.getJavaType())) {
			builder.append("number: true, ");
		}
		if (DatabaseDataTypesUtils.isIntegerNumber(c.getJavaType())) {
			builder.append("digits: true,");
			if (c.getJavaType().toLowerCase().indexOf("short") >= 0) {
				builder.append("max: " + Short.MAX_VALUE + ", ");
			} else if (c.getJavaType().toLowerCase().indexOf("integer") >= 0) {
				builder.append("max: " + Integer.MAX_VALUE + ", ");
			} else if (c.getJavaType().toLowerCase().indexOf("byte") >= 0) {
				builder.append("max: " + Byte.MAX_VALUE + ", ");
			}
		}
		if (builder.length() == 0)
			return "";
		String result = builder.toString();
		if (result.endsWith(", ")) {
			result = result.replaceAll(",\\s$", "");
		}
		return "{" + result + "}";
	}
}
