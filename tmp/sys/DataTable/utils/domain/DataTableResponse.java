package cc.yihy.utils.domain;

import java.util.List;

/**
 * 
 * @param <T>
 * @ClassName:DataTable.java
 * @Description: Jq DateTable 插件适配类
 * @Author: yihy
 * @Since: 2016年8月28日
 * @Version: 1.0
 */
public class DataTableResponse<T> {
	/**
	 * 请求次数
	 */
	private int draw;
	/**
	 * 总记录数
	 */
	private int recordsTotal;
	/**
	 * 过滤后的总记录数
	 */
	private int recordsFiltered;

	private List<T> data;

	private String error;

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getDraw() {
		return this.draw;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsTotal() {
		return this.recordsTotal;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getRecordsFiltered() {
		return this.recordsFiltered;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public List<T> getData() {
		return this.data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
