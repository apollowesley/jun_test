package com.kld.common.framework.dto;

import com.kld.common.framework.result.OperationResult;

import java.util.List;



public class GridResult<T> implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	//if T = Map<String, Object>, the key is the column name, the value is cell value for this column.	
	private List<T> rows;
	
	//Current page No.
	private Integer page;
	
	//Total pages count
	private Integer total;
	
	//Total rows count
	private Integer records;
	
	public GridResult(){}
	
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}

	public OperationResult toOperationResult(){
		return OperationResult.buildSuccessResult(this);
	}
}
