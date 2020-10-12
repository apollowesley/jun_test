package com.king.base.controller;

import java.util.List;

/**
 * ResultPage
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class ResultPage {

	public final static int DEFAULT_PAGE_SIZE = 10;

	public final static int DEFAULT_PAGE_NO = 1;

	// 当前页
	private int currentPage = 1;
	// 每页显示数量
	private int pageSize = 10;
	//总页数
	private int pageCount = 1;
	// 总条数
	private int totalCount;

	private boolean haveNextPage;

	private boolean havePrePage;

	// 存放查询结果用的list
	private List items;

	public ResultPage(int totalCount, int pageSize, int currentPage,
					  List items) {
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.pageCount = operatorPageCount();
		this.items = items;
	}


	/**
	 * 计算总页数
	 *
	 * @return
	 */
	public int operatorPageCount() {
		int pageCount = 0;
		if (pageSize != 0) {
			pageCount = totalCount / pageSize;
			if (totalCount % pageSize != 0)
				pageCount++;
		}

		return pageCount;
	}

	public int getCurrentPage() {
		currentPage = currentPage < pageCount ? currentPage
				: pageCount;
		currentPage = currentPage < 1 ? 1 : currentPage;

		return currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public boolean isHaveNextPage() {
		haveNextPage = false;
		if ((pageCount > 1) && (pageCount > getCurrentPage()))
			haveNextPage = true;
		return haveNextPage;
	}

	public boolean isHavePrePage() {
		havePrePage = false;
		if ((pageCount > 1) && (currentPage > 1))
			havePrePage = true;
		return havePrePage;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}