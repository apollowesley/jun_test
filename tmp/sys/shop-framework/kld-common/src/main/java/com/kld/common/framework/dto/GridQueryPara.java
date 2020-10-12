package com.kld.common.framework.dto;

import java.io.Serializable;

public class GridQueryPara implements Serializable {
	private static final long serialVersionUID = 1L;
	private String currentPage;
	private String maxRows;
	private String sortIndex;
	private String sortOrder;
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getMaxRows() {
		return maxRows;
	}
	public void setMaxRows(String maxRows) {
		this.maxRows = maxRows;
	}
	public String getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
}
