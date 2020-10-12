package org.duomn.naive.web.entity;

import java.util.List;

/**
 * 用于返回分页结果的对象
 * @author Hu Qiang
 *
 * @param <T>
 */
public class Pager <T> {
	
	/** 每页多少行记录 */
	private int pageSize = 20;
	/** 当前第几页 */
	private int pageNo = 1;
	/** 记录总数 */
	private int totalRows = 0;
	/** 开始记录数 */
	private int startRow;
	/** 结束记录数 */
	private int endRow;
	/** 总页数 */
	private int totalPages;
	/** 记录集合 */
	private List<T> resultList;
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	
}
