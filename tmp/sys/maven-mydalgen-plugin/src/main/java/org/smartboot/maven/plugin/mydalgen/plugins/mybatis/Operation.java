/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis;

import java.util.List;

import org.smartboot.maven.plugin.mydalgen.Table;

/**
 * @author Seer
 * @version Operation.java, v 0.1 2015年7月25日 上午10:22:24 Seer Exp.
 */
public interface Operation {
	/**
	 * Gets the table attribute of the Operation object
	 *
	 * @return The Table value
	 */
	public Table getTable();

	/**
	 * Gets the returnType attribute of the Operation object.
	 *
	 * @return The returnType value
	 */
	public String getReturnType();

	/**
	 * Gets the simpleReturnType attribute of the Operation object.
	 *
	 * @return The simpleReturnType value
	 */
	public String getSimpleReturnType();

	/**
	 * Get the name attribute of the operation.
	 *
	 * @return
	 */
	public String getName();

	/**
	 * Get the suffix for the template that render the operation.
	 *
	 * <p>
	 *
	 * @return
	 */
	public String getTemplateSuffix();

	/**
	 * Get the parsed sql statement for the operation.
	 *
	 * @return
	 */
	public String getParsedSql();

	/**
	 * Get the default return value, used to generate method stub.
	 *
	 * @return
	 */
	public String getDefaultReturnValue();

	/**
	 * Get all throwed exception.
	 *
	 * @return
	 */
	public List<String> getExceptions();

	/**
	 * Get all throwed exception types, in simple form.
	 *
	 * @return
	 */
	public List<String> getSimpleExceptions();

	/**
	 * @return
	 */
	public String getMappedStatementType();
}
