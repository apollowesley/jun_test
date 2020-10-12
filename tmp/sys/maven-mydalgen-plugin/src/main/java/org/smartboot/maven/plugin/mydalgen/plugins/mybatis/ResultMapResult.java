/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis;

/**
 * @author Seer
 * @version ResultMapResult.java, v 0.1 2015年7月25日 上午10:21:59 Seer Exp.
 */
public interface ResultMapResult {
	/**
	 *
	 * @return
	 */
	public String getProperty();

	/**
	 *
	 * @return
	 */
	public String getColumn();

	/**
	 *
	 * @return
	 */
	public int getColumnIndex();

	/**
	 *
	 * @return
	 */
	public String getJavaType();

	/**
	 *
	 * @return
	 */
	public String getJdbcType();

	/**
	 *
	 * @return
	 */
	public String getNullValue();

	/**
	 *
	 * @return
	 */
	public boolean isHasNullValue();

	/**
	 *
	 * @return
	 */
	public String getSelect();
}
