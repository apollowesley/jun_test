/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis;

import java.util.List;

/**
 * @author Seer
 * @version ResultMap.java, v 0.1 2015年7月25日 上午10:22:08 Seer Exp.
 */
public interface ResultMap {
	/**
	 * Get the "id" attribute of the ResultMap.
	 *
	 * @return
	 */
	public String getIdAttr();

	/**
	 * Get the "class" attribute of the ResultMap.
	 *
	 * @return
	 */
	public String getClassAttr();

	/**
	 * Get a list of "result" elements, each one is an instance of
	 * <tt>ResultMapResult</tt>.
	 *
	 * @return
	 */
	public List<ResultMapResult> getResults();
}
