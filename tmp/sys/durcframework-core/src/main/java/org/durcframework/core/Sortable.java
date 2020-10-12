package org.durcframework.core;

public interface Sortable {
	/**
	 * 添加排序字段映射
	 * @param field java类中的字段名
	 * @param column 数据库中的字段名
	 * @return
	 */
	Sortable addSortField(String field,String column); 
}
