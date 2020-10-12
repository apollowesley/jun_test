package com.evil.util;

/**
 * 页面信息
 */
public class Page {
	private int numPerPage; // 每页显示记录数
	private long totalCount; // 总记录数
	private int pageNumShown; // 总页数
	private int currentPage; // 当前页
	private int beginIndex; // 查询起始点
	private boolean hasPrePage; // 是否有上一页
	private boolean hasNextPage; // 是否有下一页

	public Page(int numPerPage, long totalCount, int pageNumShown,
			int currentPage, int beginIndex, boolean hasPrePage,
			boolean hasNextPage) { // 自定义构造方法
		this.numPerPage = numPerPage;
		this.totalCount = totalCount;
		this.pageNumShown = pageNumShown;
		this.currentPage = currentPage;
		this.beginIndex = beginIndex;
		this.hasPrePage = hasPrePage;
		this.hasNextPage = hasNextPage;
	}

	public Page() {
	} // 默认构造函数

	public int getNumPerPage() { // 获得每页显示记录数
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {// 设置每页显示记录数
		this.numPerPage = numPerPage;
	}

	public int getPageNumShown() {// 获得总记录数
		return pageNumShown;
	}

	public void setPageNumShown(int pageNumShown) {// 设置总记录数
		this.pageNumShown = pageNumShown;
	}

	public long getTotalCount() {// 获得总页数
		return totalCount;
	}

	public void setTotalCount(long totalCount) {// 设置总页数
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {// 获得当前页
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {// 设置当前页
		this.currentPage = currentPage;
	}

	public int getBeginIndex() {// 获得查询起始点
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {// 设置查询起始点
		this.beginIndex = beginIndex;
	}

	public boolean isHasPrePage() {// 获得是否有上一页
		return hasPrePage;
	}

	public void setHasPrePage(boolean hasPrePage) {// 设置是否有上一页
		this.hasPrePage = hasPrePage;
	}

	public boolean isHasNextPage() {// 获得是否有下一页
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {// 设置是否有下一页
		this.hasNextPage = hasNextPage;
	}
	
	/**
	 * 重写hashCode方法保证当每页的数量总数量以及当前都相等的时候是同一个对象
	 */
	@Override
	public int hashCode() {
		int result = 17; // 任意素数
		result = 31 * result + numPerPage; // c1,c2是什么看下文解释
		result = 31 * result + (int) totalCount;
		result = 31 * result + (int) currentPage;
		return result;
	}

}
