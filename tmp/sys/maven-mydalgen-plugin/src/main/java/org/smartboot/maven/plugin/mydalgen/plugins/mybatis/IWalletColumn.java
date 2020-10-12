/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis;

import org.apache.commons.lang.StringUtils;
import org.smartboot.maven.plugin.mydalgen.Column;
import org.smartboot.maven.plugin.mydalgen.javax.JavaColumn;
import org.smartboot.maven.plugin.mydalgen.javax.Sql2Java;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletColumnConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletConfigException;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.DalUtil;

/**
 * @author Seer
 * @version IWalletColumn.java, v 0.1 2015年7月25日 上午10:23:45 Seer Exp.
 */
public class IWalletColumn extends JavaColumn {

	/**
	 * Constructor for IWalletColumnDecorator.
	 */
	public IWalletColumn(Column subject) {
		super(subject);
	}

	/**
	 *
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.Column#getDefaultValue()
	 */
	public String getResultMapNullValue() {
		if (StringUtils.isNotEmpty(getDefaultValue())) {
			return getDefaultValue();
		} else {
			return DalUtil.getDefaultValue(getJavaType());
		}
	}

	/**
	 *
	 * @return
	 */
	public boolean isNeedResultMapNullValue() {
		return Sql2Java.isPrimitive(getJavaType());
	}

	/**
	 * Get java type without package name.
	 *
	 * @return
	 */
	public String getSimpleJavaType() {
		return DalUtil.getSimpleJavaType(getJavaType());
	}

	/**
	 *
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.PreferenceAware#init()
	 */
	@Override
	protected void init() {
		super.init();
	}

	protected IWalletColumnConfig getColumnConfig() {
		return ((IWalletTable) getTable()).getTableConfig().getColumn(getSqlName());
	}

	/**
	 * @see org.smartboot.maven.plugin.mydalgen.javax.JavaColumn#getVariableName()
	 */
	@Override
	public String getVariableName() {
		if (StringUtils.equals(super.getVariableName(), "return")) {
			return "returnValue";
		}

		return super.getVariableName();
	}

	/**
	 *
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.javax.JavaColumn#setJavaType()
	 */
	@Override
	public void setJavaType() {
		super.setJavaType();

		String temp = null;

		IWalletColumnConfig columnConfig = getColumnConfig();

		if (columnConfig != null && StringUtils.isNotBlank(columnConfig.getJavaType())) {
			temp = columnConfig.getJavaType();
		} else {
			try {
				temp = IWalletConfig.getInstance().getMappedJavaType(super.getJavaType());
			} catch (IWalletConfigException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(temp)) {
			super.setJavaType(temp);

			if (Character.isUpperCase(DalUtil.getSimpleJavaType(temp).charAt(0))) {
				((IWalletTable) getTable()).addDoImport(temp);
			}
		}
	}
}
