/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.operation;

import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.IWalletOperation;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletOperationConfig;

/**
 * @author Seer
 * @version IWalletUnknown.java, v 0.1 2015年7月25日 上午10:25:13 Seer Exp.
 */
public class IWalletUnknown extends IWalletOperation {
	/**
	 * Constructor for IWalletUnknown.
	 */
	public IWalletUnknown(IWalletOperationConfig opConfig) {
		super(opConfig);
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getReturnTypeName()
	 */
	public String getReturnType() {
		return null;
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getTemplateSuffix()
	 */
	public String getTemplateSuffix() {
		return "unknown";
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getSimpleReturnType()
	 */
	public String getSimpleReturnType() {
		return null;
	}

	/**
	 * @return
	 */
	public String getMappedStatementType() {
		return "unknown";
	}
}
