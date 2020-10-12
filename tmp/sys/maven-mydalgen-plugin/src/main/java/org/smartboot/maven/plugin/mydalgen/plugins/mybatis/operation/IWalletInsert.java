/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.operation;

import org.apache.commons.lang.StringUtils;
import org.smartboot.maven.plugin.mydalgen.Column;
import org.smartboot.maven.plugin.mydalgen.Table;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.IWalletColumn;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.IWalletOperation;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletOperationConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.DalUtil;

/**
 * @author Seer
 * @version IWalletInsert.java, v 0.1 2015年7月25日 上午10:25:27 Seer Exp.
 */
public class IWalletInsert extends IWalletOperation {

	public static final String OP_TYPE = "insert";

	/**
	 * Constructor for IWalletInsert.
	 */

	public IWalletInsert(IWalletOperationConfig opConfig) {

		super(opConfig);

		// 向下兼容，当没有配置机密性及完整性时，不进行SQL拼接
		if (opConfig.getTableConfig().getConfidentiality() != null
			|| opConfig.getTableConfig().getIntegrity() != null) {
			getFinalSql(opConfig);
		}

		paramType = PARAM_TYPE_OBJECT;

		multiplicity = MULTIPLICITY_ONE;
	}

	/**
	 * 机密性和完整性方案，获得配置后的SQL语句
	 *
	 * @param opConfig
	 */
	private void getFinalSql(IWalletOperationConfig opConfig) {
		// add by yuanxiao -------------
		// 获得传入的SQL
		String sql = opConfig.getSqlParser().getSql();

		// 获得insert语句中第一个右括号
		int indexFParenthesisStart = StringUtils.indexOfAny(sql, ")");

		StringBuffer sb = new StringBuffer();

		// 取第一个括号前的子串
		String firstString = StringUtils.substring(sql, 0, indexFParenthesisStart);

		// 截取第一个括号到第二个括号前的
		String secondString = StringUtils.substring(sql, indexFParenthesisStart, sql.length() - 2);

		sb.append(firstString);

		if (opConfig.getTableConfig().getConfidentiality() != null) {
			sb.append(",").append(opConfig.getTableConfig().getConfidentiality()).append("_confidentiality");
		}
		if (opConfig.getTableConfig().getIntegrity() != null) {
			sb.append(",").append(opConfig.getTableConfig().getIntegrity()).append("_integrity");
		}
		sb.append(secondString);

		sb.append(",").append("?").append(",").append("?").append(");");

		String finalSql = sb.toString();

		opConfig.setSql(finalSql);
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getReturnTypeName()
	 */
	public String getSimpleReturnType() {
		String returnType = DalUtil.getSimpleJavaType(getReturnType());//自定义的主键设置为void
		return "String".equals(returnType) ? "void" : returnType;
	}

	public String getReturnType() {
		if (getTable().getPkColumn() == null) {
			// add by zhaoxu 2007-10-26
			// 当无主键或多主键时，使用虚拟主键
			String dummyPk = opConfig.getTableConfig().getDummyPk();
			if (StringUtils.isNotBlank(dummyPk)) {
				Column dummyPkColumn = getTable().getColumn(dummyPk);
				if (dummyPkColumn != null) {
					return ((IWalletColumn) dummyPkColumn).getJavaType();
				}
			}
			throw new IllegalStateException(getTable().getSqlName() + "无主键或多主键，可在table属性中指定虚拟主键dummypk。");
		} else {
			return ((IWalletColumn) getTable().getPkColumn()).getJavaType();
		}
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
}
