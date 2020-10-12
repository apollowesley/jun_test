package com.antdsp.common.pagination;

/**
 * 
 * <p>title:Pagination.java</p>
 * <p>Description: 分页数据</p>  
 * <p>Copyright: Copyright (c) 2019</p> 
 * 
 * @author ljt
 * @date 2019年5月31日
 * @email a496401006@qq.com
 *
 */
public class Pagination {

	/**
	 * 当前页码
	 */
	private int current;
	
	private boolean showSizeChanger;
	
	/**
	 * 总条数
	 */
	private Long total;
	
	public Pagination() {
		this(1 , 0L);
	}

	public Pagination(int current, Long total) {
		this.current = current;
		this.total = total;
		showSizeChanger = true;
	}
	
	public Pagination(int current, Long total, boolean showSizeChanger) {
		this.current = current;
		this.total = total;
		this.showSizeChanger = showSizeChanger;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public boolean isShowSizeChanger() {
		return showSizeChanger;
	}

	public void setShowSizeChanger(boolean showSizeChanger) {
		this.showSizeChanger = showSizeChanger;
	}
	
	
}
