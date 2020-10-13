package durcframework.test.common;

import java.util.List;

import org.durcframework.core.GridResult;

// 自定义的结果类,用来保存查询结果信息
public class MyResultHolder implements GridResult {
	
	private List<?> list;
	
	private int start;
	private int total_count;
	private int page_index;
	private int page_length;
	private int page_count;

	@Override
	public void setList(List<?> list) {
		this.list = list;
	}

	@Override
	public void setTotal(int total) {
		this.total_count = total;
	}

	@Override
	public void setPageIndex(int pageIndex) {
		this.page_index = pageIndex;
	}

	@Override
	public void setPageSize(int pageSize) {
		this.page_length = pageSize;
	}

	@Override
	public void setPageCount(int pageCount) {
		this.page_count = pageCount;
	}

	public List<?> getList() {
		return list;
	}

	public int getTotal_count() {
		return total_count;
	}

	public int getPage_index() {
		return page_index;
	}

	public int getPage_length() {
		return page_length;
	}

	public int getPage_count() {
		return page_count;
	}
	
	public int getStart() {
		return start;
	}

	@Override
	public void setStart(int start) {
		this.start = start;
	}
	
}
