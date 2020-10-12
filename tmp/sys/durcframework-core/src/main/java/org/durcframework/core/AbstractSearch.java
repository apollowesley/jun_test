package org.durcframework.core;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSearch implements SearchSupport,Sortable {
	
	private Map<String,String> sortMap = null;
	
	public AbstractSearch() {
		initSortMap(this);
	}
	
	protected void initSortMap(Sortable sort) {
		
	}

	@Override
	public Sortable addSortField(String field,String column) {
		if(this.sortMap == null) {
			this.sortMap = new HashMap<String, String>();
		}
		
		this.sortMap.put(field, column);
		
		return this;
	}


	/**
	 * 返回数据库的排序字段名称<br>
	 * 子类覆盖getSortMap()方法,并返回java字段与数据库中的字段名映射关系<br>
	 * key为java字段名,value为数据库字段名
	 */
	@Override
	public String getDBSortname() {
		Map<String, String> sortMap = this.sortMap;
		String defSortname = this.getSortname();
		
		if(sortMap == null) {
			return defSortname;
		}
		
		String sortname = sortMap.get(defSortname);
		
		return sortname == null ? defSortname : sortname;
	}
}
