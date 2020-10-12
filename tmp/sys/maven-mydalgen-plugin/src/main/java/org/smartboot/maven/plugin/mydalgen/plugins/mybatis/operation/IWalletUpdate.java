/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.operation;

import org.apache.commons.lang.StringUtils;
import org.smartboot.maven.plugin.mydalgen.Table;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.IWalletOperation;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletOperationConfig;

/**
 * @author Seer
 * @version IWalletUpdate.java, v 0.1 2015年7月25日 上午10:24:49 Seer Exp.
 */
public class IWalletUpdate extends IWalletOperation {

	public static final String OP_TYPE = "update";

	/**
	 * Constructor for IWalletUpdate.
	 */
	public IWalletUpdate(IWalletOperationConfig conf) {
		super(conf);

		// 向下兼容，当没有配置机密性及完整性时，不进行SQL拼接
		if (opConfig.getTableConfig().getConfidentiality() != null || opConfig.getTableConfig().getIntegrity() != null) {
			getFinalSql(opConfig);
		}

		if (PARAM_TYPE_OBJECT.equals(conf.getParamType())) {
			paramType = PARAM_TYPE_OBJECT;
		} else {
			// default
			paramType = PARAM_TYPE_PRIMITIVE;
		}

		// default
		multiplicity = MULTIPLICITY_ONE;
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getReturnType()
	 */
	public String getReturnType() {
		return "int";
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getSimpleReturnType()
	 */
	public String getSimpleReturnType() {
		return "int";
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getTemplateSuffix()
	 */
	public String getTemplateSuffix() {
		return OP_TYPE;
	}

	/**
	 * @param t
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.IWalletOperation#setTable(org.smartboot.maven.plugin.mydalgen.Table)
	 */
	@Override
	public void setTable(Table t) {
		super.setTable(t);
	}

	/**
	 * @return
	 */
	public String getMappedStatementType() {
		return OP_TYPE;
	}

	/**
	 * 机密性和完整性方案，获得配置后的SQL语句
	 * 
	 * @param opConfig
	 */
	private void getFinalSql(IWalletOperationConfig opConfig) {
		// add by yuanxiao -------------
		// 获得传入的SQL,并转为小写
		String sql = opConfig.getSqlParser().getSql().toLowerCase();

		// 获得update语句中 where之前的内容
		int indexFrom = StringUtils.indexOfAny(sql, new String[] { "where" });
		// 截取where之前的子串
		String ret = StringUtils.substring(sql, 0, indexFrom);

		// 判断where之前的字段中是否有做机密性及完整性的部分
		String confidentiality = opConfig.getTableConfig().getConfidentiality().toLowerCase();
		String integrity = opConfig.getTableConfig().getIntegrity().toLowerCase();
		// 截取From之后的子串
		String finalRet = StringUtils.substring(sql, indexFrom + 6, sql.length());

		StringBuffer sb = new StringBuffer();

		sb.append(ret);

		if (ret.contains(confidentiality)) {
			sb.append(",").append(confidentiality).append("_confidentiality = ?");
		}
		if (ret.contains(integrity)) {
			sb.append(",").append(integrity).append("_integrity = ?");
		}
		sb.append(" where ").append(finalRet);

		String finalSql = sb.toString();

		opConfig.setSql(finalSql);

	}

}
