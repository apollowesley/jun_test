package com.xbd.generator.database;

import com.xbd.generator.util.StringUtils;

import java.util.Map;


public class Column {
	private String name;

	private String type;

	private String not_null;

	private Integer length;

	private Integer digits;

	private String comment;

	public Column(Map columnMap) {
		this.name = StringUtils.safeToString(columnMap.get("COLUMN_NAME"));
		this.type = this.getType(StringUtils.safeToString(columnMap.get("TYPE_NAME")), StringUtils.intValue(columnMap.get("COLUMN_SIZE")), StringUtils.intValue(columnMap.get("DECIMAL_DIGITS")));
		this.length = StringUtils.intValue(columnMap.get("COLUMN_SIZE"));
		String isNullable = StringUtils.safeToString(columnMap.get("IS_NULLABLE"));
		this.comment = StringUtils.safeToString(columnMap.get("REMARKS"));
		this.digits = StringUtils.intValue(columnMap.get("DECIMAL_DIGITS"));
		if (isNullable.equals("NO")) {
			this.not_null = "true";
		} else {
			this.not_null = "";
		}
	}

	public Integer getLength() {
		return this.length;
	}

	public String getName() {
		return this.name;
	}

	public String getNot_null() {
		return this.not_null;
	}

	public String getType() {
		return this.type;
	}

	private String getType(String type_name, int column_size, int decimal_digits) {
		if (type_name.contains("CHAR")) {
			return "string";
		} else if ("NUMBER".equals(type_name) || "DECIMAL".equals(type_name)) {
			if (decimal_digits == 0) {
				if (column_size <= 8) {
					return "int";
				} else {
					return "long";
				}
			} else {
				if (column_size < 14) {
					return "double";
				} else {
					return "big_decimal";
				}
			}
		} else if ("DATE".equals(type_name) || "DATETIME".equals(type_name) || type_name.startsWith("TIMESTAMP")) {
			return "date";
		} else if ("INT".equals(type_name)) {
			return "int";
		} else if ("Long".equals(type_name) || "BIGINT".equals(type_name)) {
			return "long";
		} else if ("FLOAT".equals(type_name)) {
			return "float";
		} else if ("SMALLINT".equals(type_name)) {
			return "int";
		} else if ("TINYINT".equals(type_name)) {
			return "byte";
		} else if ("DOUBLE".equals(type_name)) {
			return "double";
		} else if ("CLOB".equals(type_name) || "TEXT".equals(type_name) || "MEDIUMTEXT".equals(type_name) || "LONGTEXT".equals(type_name)) {
			return "materialized_clob";
		} else if (type_name.contains("BLOB")) {
			return "materialized_blob";
		} else {
			throw new RuntimeException("类型 " + type_name + " 不支持! ");
		}

	}

	public String getComment() {
		return this.comment;
	}

	public Integer getDigits() {
		return digits;
	}

	public void setDigits(Integer digits) {
		this.digits = digits;
	}
}
