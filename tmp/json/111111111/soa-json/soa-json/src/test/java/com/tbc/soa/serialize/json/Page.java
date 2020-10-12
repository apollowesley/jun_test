package com.tbc.soa.serialize.json;

import java.util.List;

public class Page<T> {

	private List<String> stringList;
	List<T> rows;

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}


}
