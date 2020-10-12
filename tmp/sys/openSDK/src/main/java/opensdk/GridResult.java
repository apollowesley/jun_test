package opensdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 存放查询结果信息的类,从这个类中可以获取结果集,分页信息
 */
public class GridResult<T> extends MsgResult {
	private List<T> rows;
	private int total = 0;
	private int start = 0;
	private int pageIndex = 1;
	private int pageSize = 10;
	private int pageCount = 0;
	
	private boolean isConverted = false;

	public List<T> getList(Class<T> elementClass) {
		if (isConverted) {
			return rows;
		}
		if (rows == null || rows.size() == 0) {
			return Collections.emptyList();
		}
		List<T> list = new ArrayList<T>(rows.size());
		for (Object e : rows) {
			JSONObject jsonObj = (JSONObject)e;
			list.add(JSON.toJavaObject(jsonObj, elementClass));
		}
		rows = list;
		isConverted = true;
		return rows;
	}

	public GridResult() {
		super();
	}

	public GridResult(List<T> rows) {
		this.rows = rows;
		if (rows != null && rows.size() > 0) {
			this.total = rows.size();
		}
	}

	public GridResult(List<T> rows, int total, int pageIndex,
			int pageSize) {
		this.rows = rows;
		this.total = total;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;

		// 计算总页数
		if (total == 0) {
			this.pageCount = 0;
		} else {
			this.pageCount = calcPageCount(total, pageSize);
		}
	}

	/**
	 * 分页数算法:页数 = (总记录数 + 每页记录数 - 1) / 每页记录数
	 * 
	 * @param total
	 * @param pageSize
	 * @return
	 */
	public static int calcPageCount(int total, int pageSize) {
		return pageSize == 0 ? 1 : (total + pageSize - 1) / pageSize;
	}

	public int getCurrentPageIndex() {
		return pageIndex;
	}

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public int getPrePageIndex() {
		return (pageIndex - 1 <= 0) ? 1 : pageIndex - 1;
	}

	/**
	 * 下一页
	 * 
	 * @return
	 */
	public int getNextPageIndex() {
		return (pageIndex + 1 > pageCount) ? pageCount : pageIndex + 1;
	}

	/**
	 * 首页
	 * 
	 * @return
	 */
	public int getFirstPageIndex() {
		return 1;
	}

	/**
	 * 最后一页
	 * 
	 * @return
	 */
	public int getLastPageIndex() {
		return pageCount;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/**
	 * 总记录数
	 * 
	 * @return
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 当前页索引,等同于getCurrentPageIndex()
	 * 
	 * @return
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * 每页显示几条记录
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 共几页
	 * 
	 * @return
	 */
	public int getPageCount() {
		return pageCount;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}


}
