package org.nature4j.framework.bean;

import java.util.Map;

import org.nature4j.framework.enums.Strategy;

public class TableBean {
	private String tableName;
	private Map<String, FieldBean> columnFieldMap;
	private String primaryKey;
	private Strategy strategy;
	private String[] dbs;

	public TableBean() {
	}

	public TableBean(String tableName, Map<String, FieldBean> columnFieldMap, String primaryKey, Strategy strategy,
			String[] dbs) {
		super();
		this.tableName = tableName;
		this.columnFieldMap = columnFieldMap;
		this.primaryKey = primaryKey;
		this.strategy = strategy;
		this.dbs = dbs;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, FieldBean> getColumnFieldMap() {
		return columnFieldMap;
	}

	public void setColumnFieldMap(Map<String, FieldBean> columnFieldMap) {
		this.columnFieldMap = columnFieldMap;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public String[] getDbs() {
		return dbs;
	}

	public void setDbs(String[] dbs) {
		this.dbs = dbs;
	}

}
