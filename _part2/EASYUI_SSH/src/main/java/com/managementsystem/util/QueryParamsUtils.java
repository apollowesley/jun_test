package com.managementsystem.util;

import com.managementsystem.query.QueryParms;
import com.managementsystem.query.SingleQueryParam;

public class QueryParamsUtils {
	
	/**
	 * 操作获取单个查询条件实体
	 * @return SingleQueryParam
	 */
	public static SingleQueryParam addSingleQueryParam() {
		return new SingleQueryParam();
	}
	/**
	 * 操作获取查询条件集合
	 * @return QueryParms
	 */
	public static QueryParms addQueryParms() {
		return new QueryParms();
	}

}
