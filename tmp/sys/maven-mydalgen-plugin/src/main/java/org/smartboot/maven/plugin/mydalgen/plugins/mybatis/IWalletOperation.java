/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.smartboot.maven.plugin.mydalgen.PreferenceAware;
import org.smartboot.maven.plugin.mydalgen.Table;
import org.smartboot.maven.plugin.mydalgen.javax.Sql2Java;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletOperationConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletParamConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.DalUtil;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.SqlParser;

import Zql.ZSelectItem;

/**
 * @author Seer
 * @version IWalletOperation.java, v 0.1 2015年7月25日 上午10:23:28 Seer Exp.
 */
public abstract class IWalletOperation extends PreferenceAware implements Operation {
	public static final String MAPPED_STATEMENT_PREFIX = "";
	public static final String PARAM_TYPE_OBJECT = "object";
	public static final String PARAM_TYPE_PRIMITIVE = "primitive";
	public static final String MULTIPLICITY_ONE = "one";
	public static final String MULTIPLICITY_MANY = "many";

	/** an operation config instance holds operation configuration. */
	protected IWalletOperationConfig opConfig;

	/** the table instance */
	private Table table;

	/**
	 * a list of all method parameters, each one is an instance of
	 * IWalletParameter
	 */
	protected List<Parameter> objectParams = new ArrayList<Parameter>();

	/**
	 * a list of all method parameters, each one is an instance of
	 * IWalletParameter
	 */
	protected List<Parameter> primitiveParams = new ArrayList<Parameter>();

	/** the type of how to pass parameters to dao */
	protected String paramType;

	/** the multiplicity of results */
	protected String multiplicity;

	protected String parameterClass;

	/**
	 * Constructor for IWalletOperation.
	 */
	public IWalletOperation(IWalletOperationConfig conf) {
		super();
		opConfig = conf;
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.PreferenceAware#prefsPrefix()
	 */
	@Override
	protected String prefsPrefix() {
		return "java-method";
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getName()
	 */
	public String getName() {
		return opConfig.getName();
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getTable()
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @param table
	 */
	public void setTable(Table t) {
		table = t;

		setupParams();

		IWalletTable iwTable = (IWalletTable) t;

		if (getParams().size() > 1) {
			// because we need a map to bundle all params.
			iwTable.addIbatisImport("java.util.Map");
			iwTable.addIbatisImport("java.util.HashMap");
		}

		// add imports for return type
		iwTable.addDaoImport(getReturnType());

		// add imports for all exception type
		iwTable.addDaoImports(getExceptions());

	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getParsedSql()
	 */
	public String getParsedSql() {
		return opConfig.getZst().toString();
	}

	/**
	 * @return
	 */
	public SqlParser getSqlParser() {
		return opConfig.getSqlParser();
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getDefaultReturnValue()
	 */
	public String getDefaultReturnValue() {
		return DalUtil.getDefaultValue(getReturnType());
	}

	/**
	 * @return
	 */
	public List<Parameter> getParams() {
		if (PARAM_TYPE_OBJECT.equals(paramType)) {
			return objectParams;
		} else {
			// as default;
			return primitiveParams;
		}
	}

	/**
	 * @return
	 */
	public IWalletParameter getParam() {
		if (getParams().size() == 1) {
			return (IWalletParameter) getParams().get(0);
		} else {
			return null;
		}
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getExceptions()
	 */
	public List<String> getExceptions() {
		return new ArrayList<String>();
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getSimpleExceptions()
	 */
	public List<String> getSimpleExceptions() {
		List<String> list = getExceptions();
		List<String> newList = new ArrayList<String>();

		if (CollectionUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				String exception = list.get(i);
				newList.add(DalUtil.getSimpleJavaType(exception));
			}
		}

		return newList;
	}

	public String getMappedStatementId() {
		return getMappedStatementId(false);
	}

	/**
	 * @return
	 */
	public String getMappedStatementId(boolean needAppName) {
		// String appName = needAppName ?
		// DalUtil.toUpperCaseWithDash(CfgUtils.getAppName()) + "-" : "";
		// return MAPPED_STATEMENT_PREFIX + appName +
		// DalUtil.toUpperCaseWithDash(((IWalletTable)
		// getTable()).getBaseClassName()) + "-" +
		// DalUtil.toUpperCaseWithDash(getName());
		return getName();
	}

	/**
	 * @param origSql
	 * @return
	 */
	protected String getMappedStatementSql(String origSql) {
		if (StringUtils.isBlank(origSql)) {
			return origSql;
		}

		StringBuffer msSql = new StringBuffer();

		int startIndex = 0;
		int endIndex = origSql.indexOf("?");
		Iterator<Parameter> iParams = primitiveParams.iterator();

		while (endIndex >= 0 && iParams.hasNext()) {
			msSql.append(origSql.substring(startIndex, endIndex));

			IWalletParameter param = (IWalletParameter) iParams.next();
			String paramName;

			if (PARAM_TYPE_PRIMITIVE.equals(paramType) && primitiveParams.size() == 1) {
				paramName = "value";
			} else {
				paramName = param.getName();
			}

			// deal with Money mapping
			if ("Money".equals(param.getSimpleJavaType())) {
				paramName = paramName + ".cent";
			}

			// // add by zhaoxu 2007-04-11 -->>>
			// String sqlType = param.getSqlType();
			// if ("VARCHAR2".equals(sqlType)) {
			// sqlType = "VARCHAR";
			// } else if (sqlType.indexOf("(") > 0) {
			// sqlType = sqlType.substring(0, sqlType.indexOf("("));
			// }
			//
			// if (sqlType != null && sqlType.length() > 0) {
			// paramName = paramName + ":" + sqlType;
			// }

			// 拼装sql语句
			msSql.append("#{").append(paramName).append("}");

			startIndex = endIndex + 1;
			endIndex = origSql.indexOf("?", startIndex);
		}

		msSql.append(origSql.substring(startIndex));

		String append = opConfig.getAppend();
		if (StringUtils.isNotBlank(append)) {
			msSql.append(" ").append(append);
		}

		return msSql.toString();
	}

	/**
	 * Replace all parameter placeholders in parsedSql with their corresponding
	 * names.
	 *
	 * @return
	 */
	public String getMappedStatementSql() {
		String sql = null;
		if (opConfig.isHasSqlmap()) {
			// TODO: optimize the logic
			List<Parameter> params = getParams();
			List<String> paramNames = new ArrayList<String>();

			for (int i = 0; i < params.size(); i++) {
				paramNames.add(params.get(i).getName());
			}
			sql = opConfig.getSqlmap(paramNames);
		} else {
			sql = getMappedStatementSql(getParsedSql());
		}
		return addSqlAnnotation(sql);
	}

	// added by yangyanzhao 2009-11-11
	public String getMappedStatementSqlNoAnnotation() {
		if (opConfig.isHasSqlmap()) {
			// TODO: optimize the logic
			List<Parameter> params = getParams();
			List<String> paramNames = new ArrayList<String>();

			for (int i = 0; i < params.size(); i++) {
				paramNames.add(params.get(i).getName());
			}
			return opConfig.getSqlmap(paramNames);
		} else {
			return getMappedStatementSql(getParsedSql());
		}
	}

	// added by yangyanzhao 2009-11-11
	public String addSqlAnnotation(String orgSql) {
		String idAnnotation = " ";
		String[] searchStrs = new String[] { "select", "SELECT", "insert", "INSERT", "delete", "DELETE", "update",
				"UPDATE" };
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
	public String getParamType() {
		return paramType;
	}

	/**
	 * @return
	 */
	public String getMultiplicity() {
		return multiplicity;
	}

	public String getParameterClass() {
		return opConfig.getParameterClass();
	}

	/**
	 */
	protected void setupParams() {
		IWalletTable iwTable = (IWalletTable) getTable();

		IWalletParameter param = new IWalletParameter(this, iwTable.getSingularisedVariableName());

		param.setPlugin(getPlugin());
		objectParams.add(param);

		if (PARAM_TYPE_OBJECT.equals(paramType)) {
			iwTable.addDaoImport(param.getQualifiedJavaType());
		}

		if (opConfig.getExtraParams().isEmpty()) {
			List<String> rawParams = getSqlParser().getParams();
			Map<String, Integer> usedNames = new HashMap<String, Integer>();
			for (Iterator<String> i = rawParams.iterator(); i.hasNext();) {
				String paramName = i.next();

				if (!usedNames.containsKey(paramName)) {
					param = new IWalletParameter(this, paramName);
					usedNames.put(paramName, new Integer(1));
				} else {
					int suffix = usedNames.get(paramName).intValue();

					suffix++;
					param = new IWalletParameter(this, paramName, String.valueOf(suffix));
					usedNames.put(paramName, new Integer(suffix));
				}

				// add by zhaoxu 2007-04-11 -->>>
				String sqlType = null;
				try {
					sqlType = iwTable.getColumn(paramName).getSqlTypeName();
					param.setSqlType(sqlType);
				} catch (Exception e) {
					;
				}
				// add by zhaoxu 2007-04-11 --<<<
				param.setPlugin(getPlugin());
				primitiveParams.add(param);

				if (PARAM_TYPE_PRIMITIVE.equals(paramType)) {
					param.setAnnotation(true);
					iwTable.addDaoImport("org.apache.ibatis.annotations.Param");
					iwTable.addDaoImport(param.getQualifiedJavaType());
				}
			}
		} else {
			for (IWalletParamConfig paramConfig : opConfig.getExtraParams()) {
				param = new IWalletParameter(this, paramConfig.getName());
				param.setAnnotation(true);// operation/extraparams/param定义的参数适用Mybatis注解
				param.setJavaType(paramConfig.getJavaType());
				param.setGenericType(paramConfig.getGenericType());

				param.setPlugin(getPlugin());

				if (PARAM_TYPE_OBJECT.equals(paramType)) {
					objectParams.add(param);
				} else {
					primitiveParams.add(param);
				}
				iwTable.addDaoImport("org.apache.ibatis.annotations.Param");
				iwTable.addDaoImport(param.getQualifiedJavaType());
				iwTable.addIbatisImport(param.getQualifiedJavaType());
			}
		}

	}

	/**
	 *
	 * @return
	 */
	public boolean isReturnTypePrimitive() {
		// simplistic approach
		return Sql2Java.isPrimitive(getReturnType());
	}

	/**
	 * @return
	 */
	public String getReturnTypeForPrimitive() {
		return Sql2Java.getClassForPrimitive(getReturnType());
	}

	/**
	 * @return
	 */
	public String getSimpleReturnTypeForPrimitive() {
		return DalUtil.getSimpleJavaType(getReturnTypeForPrimitive());
	}

	/**
	 * @return
	 */
	public boolean isHasSqlmap() {
		return opConfig.isHasSqlmap();
	}

	public String getDescription() {
		return StringUtils.isNotBlank(opConfig.getDescription()) ? opConfig.getDescription() : "No descripions";
	}

	// add by yuanxiao--这个方法其实没什么作用，只是为了区分返回List情况下的泛型
	public int getField() {

		SqlParser parser = opConfig.getSqlParser();
		String ret = parser.getSql();

		getAllField();
		// 获得逗号的位置
		int commaStart = StringUtils.indexOfAny(ret, ",");
		// 获得第一个from的位置
		// int fromStart = StringUtil.indexOfAny(ret, "from");
		// 如果逗号的位置小于from的位置，说明此次操作的参数不止一个
		// if (commaStart < fromStart) {
		// return true;
		// }
		// else if (commaStart == -1) {
		// return false;
		// }
		return commaStart;
	}

	// add by yuanxiao --为了区分是select * 的情况
	public int getAllField() {
		SqlParser parser = opConfig.getSqlParser();
		String ret = parser.getSql();
		int start = StringUtils.indexOfAny(ret, "*");
		return start;
	}

	public String getColumnType() {
		// 为了运行getFiled方法，只想到这种方法了
		getField();

		SqlParser parser = opConfig.getSqlParser();

		if (parser.isSelectItemSingle()) {
			ZSelectItem item = parser.getSelectItem();

			if (item.getAggregate() != null) {
				// the select item is an aggregate
				String aggregateFunc = item.getAggregate();

				if (aggregateFunc.equalsIgnoreCase("COUNT")) {
					return "long";
				} else if (aggregateFunc.equalsIgnoreCase("SUM") || aggregateFunc.equalsIgnoreCase("AVG")
					|| aggregateFunc.equalsIgnoreCase("MAX") || aggregateFunc.equalsIgnoreCase("MIN")) {
					String columnName = item.getColumn();
					int indexStart = columnName.indexOf("(");
					int indexEnd = columnName.indexOf(")", indexStart);

					columnName = columnName.substring(indexStart + 1, indexEnd);

					return ((IWalletColumn) getTable().getColumn(columnName)).getJavaType();
				} else {
					// can not happen
					return "void";
				}
			} else {
				return ((IWalletColumn) getTable().getColumn(item.getColumn())).getJavaType();
			}
		} else {
			return ((IWalletTable) getTable()).getQualifiedDOClassName();
		}
	}

}
