/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.smartboot.maven.plugin.mydalgen.Column;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletColumnConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletResultMapConfig;

/**
 * @author Seer
 * @version IWalletResultMap.java, v 0.1 2015年7月25日 上午10:22:51 Seer Exp.
 */
public class IWalletResultMap implements ResultMap {
	/** the table to which the result map belongs */
	protected IWalletTable table;

	protected String idAttr;

	protected String classAttr;

	protected List<ResultMapResult> results = new ArrayList<ResultMapResult>();

	public IWalletResultMap(IWalletTable table, IWalletResultMapConfig config) {
		if (config == null) {
			// construct the default result map
			idAttr = table.getResultMapId();
			classAttr = table.getQualifiedDOClassName();

			Iterator<Column> i = table.getColumns().iterator();
			while (i.hasNext()) {
				results.add(new IWalletResultMapResult((IWalletColumn) i.next()));
			}
		} else {
			idAttr = config.getName();
			/*
			 * TODO: make the class attribute customizable
			 */
			classAttr = table.getQualifiedDOClassName();

			Iterator<IWalletColumnConfig> i = config.getColumns().iterator();
			while (i.hasNext()) {
				results.add(new IWalletResultMapResult((IWalletColumn) table.getColumn(i.next().getName())));
			}
		}
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.ResultMap#getIdAttr()
	 */
	public String getIdAttr() {
		return idAttr;
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.ResultMap#getClassAttr()
	 */
	public String getClassAttr() {
		return classAttr;
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.ResultMap#getResults()
	 */
	public List<ResultMapResult> getResults() {
		return results;
	}

}
