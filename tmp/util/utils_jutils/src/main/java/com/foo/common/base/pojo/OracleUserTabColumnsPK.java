package com.foo.common.base.pojo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class OracleUserTabColumnsPK implements Serializable {
	private static final long serialVersionUID = 1L;
	private String table_name = "";
	private String column_name = "";

	public String getTable_name() {
		return table_name.toLowerCase();
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getColumn_name() {
		return column_name.toLowerCase();
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

}
