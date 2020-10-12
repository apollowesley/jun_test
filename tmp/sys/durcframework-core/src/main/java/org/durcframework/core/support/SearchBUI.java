package org.durcframework.core.support;

import org.durcframework.core.SearchPojo;

/**
 * 支持BUI的查询接受类,BUI:http://www.builive.com
 * 
 * @author Administrator 2016-5-3
 */
public class SearchBUI extends SearchPojo {

	/** 排序字段名 */
	private String field;
	/** 排序方向ASC,DESC */
	private String direction;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
		this.setSortname(field);
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
		this.setSortorder(direction);
	}

}
