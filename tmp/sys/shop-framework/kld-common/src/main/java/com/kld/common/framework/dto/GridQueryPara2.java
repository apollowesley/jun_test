package com.kld.common.framework.dto;

import java.io.Serializable;

public class GridQueryPara2{
	private Integer currentPage;
	private Integer maxRows;
	private Integer sortIndex;
	private Integer sortOrder;
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getMaxRows() {
		return maxRows;
	}
	public void setMaxRows(Integer maxRows) {
		this.maxRows = maxRows;
	}
	public Integer getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
