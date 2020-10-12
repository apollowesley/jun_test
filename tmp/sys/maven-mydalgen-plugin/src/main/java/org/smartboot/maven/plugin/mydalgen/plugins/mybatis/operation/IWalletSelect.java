/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.operation;

import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.smartboot.maven.plugin.mydalgen.Table;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.IWalletOperation;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.IWalletTable;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletOperationConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.DalUtil;

import Zql.ZQuery;
import Zql.ZSelectItem;

/**
 * @author Seer
 * @version IWalletSelect.java, v 0.1 2015年7月25日 上午10:25:21 Seer Exp.
 */
public class IWalletSelect extends IWalletOperation {

	/** default java type to return the result of multiple records */
	public static final String DEFAULT_MANY_RETURN_TYPE_NO_PAGING = "java.util.List";

	/** default java type to return the result of multiple records with paging */
	public static final String OP_TYPE = "select";
	private String parsedSqlForCount = null;

	/**
	 * Constructor for IWalletSelect.
	 */
	public IWalletSelect(IWalletOperationConfig conf) {
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

		if (MULTIPLICITY_MANY.equals(conf.getMultiplicity())) {
			multiplicity = MULTIPLICITY_MANY;
		} else {
			// default
			multiplicity = MULTIPLICITY_ONE;
		}
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
		// 获得select语句中 from之前的内容
		int indexFrom = StringUtils.indexOfAny(sql, new String[] { " from", " FROM" });
		// 截取From之前的子串
		String ret = StringUtils.substring(sql, 0, indexFrom);
		// 考虑select count(*)的情况
		int indexCount = StringUtils.indexOfAny(ret, new String[] { "count(*)", "COUNT(*)" });

		if (indexCount > 0) {
			return;
		}

		// 判断是否是select *这种情况
		int allStart = StringUtils.indexOfAny(ret, "*");
		if (allStart > 0) {
			opConfig.setSql(sql);
		} else {
			// 截取From之后的子串
			String finalRet = StringUtils.substring(sql, indexFrom + 5, sql.length());

			StringBuffer sb = new StringBuffer();

			sb.append(ret);

			if (opConfig.getTableConfig().getConfidentiality() != null) {
				sb.append(",").append(opConfig.getTableConfig().getConfidentiality()).append("_confidentiality");
			}
			if (opConfig.getTableConfig().getIntegrity() != null) {
				sb.append(",").append(opConfig.getTableConfig().getIntegrity()).append("_integrity");
			}
			sb.append(" from ").append(finalRet);

			String finalSql = sb.toString();

			opConfig.setSql(finalSql);
		}
	}

	protected String getReturnTypeOne() {
		if (opConfig.getResultMap() != null) {
			return ((IWalletTable) getTable()).getResultMap(opConfig.getResultMap()).getClassAttr();
		} else if (opConfig.getResultType() != null) {
			return opConfig.getResultType();
		} else {
			return getColumnType();
		}
	}

	@Override
	public String getColumnType() {
		/**
		 * SqlParser parser = opConfig.getSqlParser();
		 *
		 * if (parser.isSelectItemSingle()) { ZSelectItem item =
		 * parser.getSelectItem();
		 *
		 * if (item.getAggregate() != null) { // the select item is an aggregate
		 * String aggregateFunc = item.getAggregate();
		 *
		 * if (logger.isDebugEnabled()) { //
		 * logger.debug("The aggregate func is " + aggregateFunc); }
		 *
		 * if (aggregateFunc.equalsIgnoreCase("COUNT")) { return "long"; } else
		 * if (aggregateFunc.equalsIgnoreCase("SUM") ||
		 * aggregateFunc.equalsIgnoreCase("AVG") ||
		 * aggregateFunc.equalsIgnoreCase("MAX") ||
		 * aggregateFunc.equalsIgnoreCase("MIN")) { String columnName =
		 * item.getColumn(); int indexStart = columnName.indexOf("("); int
		 * indexEnd = columnName.indexOf(")", indexStart);
		 *
		 * columnName = columnName.substring(indexStart + 1, indexEnd);
		 *
		 * if (logger.isDebugEnabled()) { //
		 * logger.debug("The column to be aggregated is " + columnName + "."); }
		 *
		 * return ((IWalletColumn)
		 * (getTable().getColumn(columnName))).getJavaType(); } else { // can
		 * not happen return "void"; } } else { return ((IWalletColumn)
		 * (getTable().getColumn(item.getColumn()))).getJavaType(); } } else {
		 * return ((IWalletTable) getTable()).getQualifiedDOClassName(); }
		 */

		String resultType = opConfig.getResultType();
		if (StringUtils.isNotBlank(resultType)) {
			return resultType;
		}

		return super.getColumnType();
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getReturnTypeName()
	 */
	public String getReturnType() {
		if (MULTIPLICITY_MANY.equals(multiplicity)) {
			addImprotForGenericType();
			return DEFAULT_MANY_RETURN_TYPE_NO_PAGING;
		}

		return getReturnTypeOne();
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
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getSimpleReturnType()
	 */
	public String getSimpleReturnType() {
		String simpleReturnType = DalUtil.getSimpleJavaType(getReturnType());
		if (StringUtils.equals("List", simpleReturnType)) {
			String itemType = getColumnType();
			if (StringUtils.equals(itemType, "long")) {
				itemType = "Long";
			} else if (StringUtils.equals(itemType, "int")) {
				itemType = "Integer";
			} else if (StringUtils.equals(itemType, "map")) {
				itemType = "java.util.Map<String, Object>";
			} else {
				itemType = DalUtil.getSimpleJavaType(itemType);
			}

			simpleReturnType += "<" + itemType + ">";
		}

		if (StringUtils.equals(simpleReturnType, "map")) {
			simpleReturnType = "java.util.Map<String, Object>";
		}

		return simpleReturnType;
	}

	/**
	 * 增加泛型类的导入
	 * <p>
	 * 当方法返回类型为List时,我们将它转化为泛型,即返回List<???>类型;<br>
	 * 此方法,就是针对这种情况,而增加对???类的导入<br>
	 * 如果???类型为long,int,则不需要增加导入;
	 * </p>
	 *
	 * add by lejin,2009-7-31 下午03:14:27,
	 *
	 * @author lejin
	 */
	private void addImprotForGenericType() {
		String itemType = getColumnType();
		if (StringUtils.equals(itemType, "long") || StringUtils.equals(itemType, "int")) {
			// 类型为long,int,则不需要增加导入;
			return;
		} else {
			// add by lejin,2009-7-31 上午11:14:04,
			// 在*DAO类和ibatis*DAO类中增加itemType的improt;
			((IWalletTable) getTable()).addDaoImport(itemType);
			((IWalletTable) getTable()).addIbatisImport(itemType);
			return;
		}
	}

	/**
	 * @return
	 */
	public String getMappedStatementType() {
		return OP_TYPE;
	}

	/**
	 * @return
	 */
	public String getMappedStatementResult() {
		if (opConfig.getResultMap() != null) {
			return "resultMap=\"" + opConfig.getResultMap() + "\"";
		} else {
			String result = getReturnTypeOne();

			if (((IWalletTable) getTable()).getQualifiedDOClassName().equals(result)) {
				return "resultMap=\"" + ((IWalletTable) getTable()).getResultMapId() + "\"";
			} else {
				return "resultType=\"" + result + "\"";
			}
		}
	}

	public String getStartRowName() {
		return "startRow";
	}

	public String getEndRowName() {
		return "endRow";
	}

	public String addSqlAnnotationForCount(String orgSql) {
		String idAnnotation = " ";
		String[] searchStrs = new String[] { "select", "SELECT" };
		int startOperation = StringUtils.indexOfAny(orgSql, searchStrs);
		if (-1 != startOperation) {
			String operation = StringUtils.substring(orgSql, 0, startOperation + 6);
			String afterOperation = StringUtils.substring(orgSql, startOperation + 7, orgSql.length());
			orgSql = operation + idAnnotation + afterOperation;
		}
		return orgSql;
	}

	/**
	 * @return
	 */
	public String getParsedSqlForCount() {
		if (parsedSqlForCount == null) {
			ZQuery zst = (ZQuery) opConfig.getZst();
			ZQuery zstCount = new ZQuery();

			Vector<ZSelectItem> select = new Vector<ZSelectItem>();

			select.add(new ZSelectItem("count(*)"));
			zstCount.addSelect(select);

			zstCount.addFrom(zst.getFrom());
			zstCount.addWhere(zst.getWhere());

			// TODO: need to support more features?
			parsedSqlForCount = zstCount.toString();
		}

		return parsedSqlForCount;
	}
}
