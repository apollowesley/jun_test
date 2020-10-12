package org.durcframework.core.support;

import org.durcframework.core.SearchEntity;

/**
 * jquery easyUI的datagrid查询类
 */
public class SearchEasyUI extends SearchEntity {

	// 当前第几页
	private int page = 1;
	// 每页记录数
	private int rows = 20;
	
	private String sort;
	private String order;
	
	public SearchEasyUI() {
		this.setPageIndex(page);
		this.setPageSize(rows);
	}
	
	
	public void setPage(int page) {
		this.page = page;
		this.setPageIndex(this.page);
	}

	public void setRows(int rows) {
		this.rows = rows;
		this.setPageSize(this.rows);
	}

	public void setSort(String sort) {
		this.sort = sort;
		this.setSortname(this.sort);
	}

	public void setOrder(String order) {
		this.order = order;
		this.setSortorder(this.order);
	}

}
