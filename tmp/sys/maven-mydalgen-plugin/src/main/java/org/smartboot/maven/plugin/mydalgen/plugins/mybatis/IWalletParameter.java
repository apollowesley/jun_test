/*
 * Taobao.com Inc.
 * Copyright (c) 2000-2004 All Rights Reserved.
 */
package org.smartboot.maven.plugin.mydalgen.plugins.mybatis;

import org.apache.commons.lang.StringUtils;
import org.smartboot.maven.plugin.mydalgen.DbNameConverter;
import org.smartboot.maven.plugin.mydalgen.PreferenceAware;
import org.smartboot.maven.plugin.mydalgen.javax.Sql2Java;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.DalUtil;

/**
 * @author Seer
 * @version IWalletParameter.java, v 0.1 2015年7月25日 上午10:23:16 Seer Exp.
 */
public class IWalletParameter extends PreferenceAware implements Parameter {

	/** the name of the parameter */
	private String name;

	/** the qualified java type of the parameter */
	private String javaType;

	/** 泛型类型 */
	private String genericType;

	/** an instance of an IWalletOperation */
	private IWalletOperation operation;
	private String suffix;

	/** the sqlType of the parameter. eg:NUMBER VARCHAR2 */
	private String sqlType;

	private boolean annotation;

	/**
	 * Constructor for IWalletParameter.
	 */
	public IWalletParameter(IWalletOperation operation, String name) {
		this(operation, name, null);
	}

	/**
	 * Constructor for IWalletParameter.
	 */
	public IWalletParameter(IWalletOperation operation, String name, String suffix) {
		super();

		this.operation = operation;
		this.name = name;
		this.suffix = suffix;
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.PreferenceAware#prefsPrefix()
	 */
	@Override
	protected String prefsPrefix() {
		return "java-method-param";
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Parameter#getQualifiedJavaType()
	 */
	public String getQualifiedJavaType() {
		// special case first
		if (StringUtils.isNotBlank(javaType)) {
			return javaType;
		}

		if (name.endsWith("_list")) {
			return "java.lang.String";
		}

		if (name.equalsIgnoreCase("rownum")) {
			return "int";
		}

		// more special parameters can be added here
		// parameter as dataobject
		if (name.equals(((IWalletTable) operation.getTable()).getSingularisedVariableName())) {
			return ((IWalletTable) operation.getTable()).getQualifiedDOClassName();
		}

		// parameter corresponding to database table column
		IWalletColumn column = null;

		try {
			column = (IWalletColumn) operation.getTable().getColumn(name);
		} catch (Exception e) {
			// ignore
			// LogUtils.get().debug("Can't get column for name " + name, e);
		}

		if (column != null) {
			return column.getJavaType();
		}

		return null;
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Parameter#getSimpleJavaType()
	 */
	public String getSimpleJavaType() {
		return DalUtil.getSimpleJavaType(getQualifiedJavaType());
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Parameter#getName()
	 */
	public String getName() {
		String retName = "";

		if (name.equals(((IWalletTable) operation.getTable()).getSingularisedVariableName())) {
			retName = name;
		} else {
			IWalletColumn column = null;

			try {
				column = (IWalletColumn) operation.getTable().getColumn(name);
			} catch (Exception e) {
				// ignore
				// LogUtils.get().debug("Can't get column by name " + name, e);
			}

			if (column != null) {
				retName = column.getVariableName();
			} else {
				retName = org.smartboot.maven.plugin.mydalgen.Util.decapitalise(DbNameConverter.getInstance()
					.columnNameToVariableName(name));
			}
		}

		if (StringUtils.isBlank(suffix)) {
			return retName;
		} else {
			return retName + suffix;
		}
	}

	/**
	 *
	 * @return
	 */
	public boolean isJavaTypePrimitive() {
		// simplistic approach
		return Sql2Java.isPrimitive(getQualifiedJavaType());
	}

	/**
	 * @return
	 */
	public String getJavaTypeForPrimitive() {
		return Sql2Java.getClassForPrimitive(getQualifiedJavaType());
	}

	/**
	 * @return
	 */
	public String getSimpleJavaTypeForPrimitive() {
		return DalUtil.getSimpleJavaType(getJavaTypeForPrimitive());
	}

	/**
	 * @param javaType
	 *            The javaType to set.
	 */
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getGenericType() {
		return genericType;
	}

	public void setGenericType(String genericType) {
		this.genericType = genericType;
	}

	/**
	 * @return the sqlType
	 */
	public String getSqlType() {
		return sqlType;
	}

	/**
	 * @param sqlType
	 *            the sqlType to set
	 */
	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public final boolean isAnnotation() {
		return annotation;
	}

	public final void setAnnotation(boolean annotation) {
		this.annotation = annotation;
	}

}
