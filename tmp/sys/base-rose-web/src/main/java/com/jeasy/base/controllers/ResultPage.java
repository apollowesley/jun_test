package com.jeasy.base.controllers;

import java.util.List;

import lombok.Data;

/**
 * ResultPage
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Data
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

	// 存放查询结果用的list
	private List items;

	public ResultPage(int totalCount, int pageSize, int currentPage, List items) {
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.items = items;
	}
}