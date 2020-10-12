package com.yonge.crud.core.db.model;

import java.util.ArrayList;
import java.util.List;

public class Table {

	private String name;

	private String schema;

	private String catalog;

	private String remarks;

	private String className;

	private String packageName;

	private List<Column> columns = new ArrayList<Column>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name = " + name).append(" catalog = " + catalog)
				.append(" schema = " + schema).append(" remarks = " + remarks)
				.append(" packageName = " + packageName)
				.append(" className = " + className);
		return sb.toString();
	}
}
