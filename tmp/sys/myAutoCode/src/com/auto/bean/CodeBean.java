package com.auto.bean;

import com.auto.codeUtil.DbType;

public class CodeBean {
	private String tabStr;// 表名称字符串，用“，”分割多个表
	private String dbName;// 数据库名称
	private String path;// 生成文件的路径
	private String tableName;// 查询的表名
	private String queryTableNameSql;// 查询表名对应的SQL，默认给出ORACLE的实现，可以替换成其他的数据库的SQL
	private String queryColSql;// 查询列名的sql
	private DbType dbType;
	private String basePackage;// 基础包
	private String actionBasePackage;

	public String getActionBasePackage() {
		return actionBasePackage;
	}

	public void setActionBasePackage(String actionBasePackage) {
		this.actionBasePackage = actionBasePackage;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public DbType getDbType() {
		return dbType;
	}

	public void setDbType(DbType dbType) {
		this.dbType = dbType;
	}

	public CodeBean() {
		super();
	}

	public String getTabStr() {
		return tabStr;
	}

	public void setTabStr(String tabStr) {
		this.tabStr = tabStr;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getQueryTableNameSql() {
		return queryTableNameSql;
	}

	public void setQueryTableNameSql(String queryTableNameSql) {
		this.queryTableNameSql = queryTableNameSql;
	}

	public String getQueryColSql() {
		return queryColSql;
	}

	public void setQueryColSql(String queryColSql) {
		this.queryColSql = queryColSql;
	}

}
