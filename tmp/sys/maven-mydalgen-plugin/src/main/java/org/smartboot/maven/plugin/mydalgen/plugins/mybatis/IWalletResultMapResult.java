/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.smartboot.maven.plugin.mydalgen.javax.Sql2Java;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.DalUtil;

/**
 * @author Seer
 * @version IWalletResultMapResult.java, v 0.1 2015年7月25日 上午10:22:44 Seer Exp.
 */
public class IWalletResultMapResult implements ResultMapResult {
	/** the column associated with the result map */
	protected IWalletColumn column;

	private Map<String, String> mysql_mybatis_jdbcTypeMap = new HashMap<String, String>();
	{
		mysql_mybatis_jdbcTypeMap.put("BIGINT UNSIGNED", "BIGINT");
		mysql_mybatis_jdbcTypeMap.put("DATETIME", "TIMESTAMP");
		mysql_mybatis_jdbcTypeMap.put("INT", "INTEGER");
		mysql_mybatis_jdbcTypeMap.put("TEXT", "VARCHAR");
		mysql_mybatis_jdbcTypeMap.put("LONGTEXT", "VARCHAR");
		mysql_mybatis_jdbcTypeMap.put("INT UNSIGNED", "INTEGER");
	}

	/**
	 * Constructor for IWalletResultMapResult.
	 */
	public IWalletResultMapResult(IWalletColumn c) {
		column = c;
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.ResultMapResult#getProperty()
	 */
	public String getProperty() {
		if (column != null) {
			return column.getVariableName();
		} else {
			return null;
		}
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.ResultMapResult#getColumn()
	 */
	public String getColumn() {
		if (column != null) {
			return column.getSqlName();
		} else {
			return null;
		}
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.ResultMapResult#getColumnIndex()
	 */
	public int getColumnIndex() {
		// not used yet
		return -1;
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.ResultMapResult#getJavaType()
	 */
	public String getJavaType() {
		if (column != null) {
			return column.getJavaType();
		} else {
			return null;
		}
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.ResultMapResult#getJdbcType()
	 */
	public String getJdbcType() {
		if (column != null) {
			String origJdbcType = column.getSqlTypeName();
			String jdbcType = mysql_mybatis_jdbcTypeMap.get(origJdbcType
					.toUpperCase());
			if (StringUtils.isEmpty(jdbcType)) {
				return origJdbcType;
			} else {
				return jdbcType;
			}
		} else {
			return null;
		}
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.ResultMapResult#getNullValue()
	 */
	public String getNullValue() {
		// if (column != null) {
		// return column.getResultMapNullValue();
		// } else {
		// return null;
		// }
		return DalUtil.getDefaultValue(getJavaType());
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.ResultMapResult#isHasNullValue()
	 */
	public boolean isHasNullValue() {
		return Sql2Java.isPrimitive(getJavaType());
		// if (column != null) {
		// return column.isNeedResultMapNullValue();
		// } else {
		// return false;
		// }
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.ResultMapResult#getSelect()
	 */
	public String getSelect() {
		// not supported yet
		return "";
	}
}
