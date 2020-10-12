package org.durcframework.core.support;

import java.util.List;

import org.durcframework.core.DefaultMessageResult;
import org.durcframework.core.GridResult;

/**
 * 支持bsgrid的结果类 bsgrid:http://git.oschina.net/bs2004/jquery.bsgrid
 */
public class BsgridResult extends DefaultMessageResult implements GridResult {
	private static final long serialVersionUID = -5627579622592237361L;
	private int curPage;
	private int totalRows;
	private List<?> data;

	@Override
	public void setList(List<?> list) {
		this.setData(list);
	}

	@Override
	public void setTotal(int total) {
		this.setTotalRows(total);
	}

	@Override
	public void setStart(int start) {
	}

	@Override
	public void setPageIndex(int pageIndex) {
		this.setCurPage(pageIndex);
	}

	@Override
	public void setPageSize(int pageSize) {
	}

	@Override
	public void setPageCount(int pageCount) {
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

}
