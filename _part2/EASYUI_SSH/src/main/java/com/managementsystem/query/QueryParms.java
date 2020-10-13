package com.managementsystem.query;

import java.util.List;

public class QueryParms {
	/**
	 * 多参数
	 */
	private List<SingleQueryParam> singleQueryParams;

	public List<SingleQueryParam> getSingleQueryParams() {
		return singleQueryParams;
	}

	public void setSingleQueryParams(List<SingleQueryParam> singleQueryParams) {
		this.singleQueryParams = singleQueryParams;
	}
		
}
