/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis;

/**
 * @author Seer
 * @version Parameter.java, v 0.1 2015年7月25日 上午10:22:17 Seer Exp.
 */
public interface Parameter {
	/**
	 *
	 * @return
	 */
	public String getQualifiedJavaType();

	/**
	 * @return
	 */
	public String getSimpleJavaType();

	/**
	 *
	 * @return
	 */
	public String getName();
}
