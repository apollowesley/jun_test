package com.antdsp.common.pagination;

import java.util.List;

/**
 * 
 * <p>title:PaginationData</p>
 * <p>Description: 分页信息</p>  
 * <p>Copyright: Copyright (c) 2019</p> 
 * 
 * @author ljt
 * @date 2019年5月31日
 * @email a496401006@qq.com
 *
 * @param <T> 	数据类型
 * data			数据
 * pagination	分页参数，字段与antd中对应
 */
public class PaginationData<T> {

	private List<T> data;
	private Pagination pagination;
	
	public PaginationData() {
		super();
	}
	public PaginationData(List<T> data, Pagination pagination) {
		super();
		this.data = data;
		this.pagination = pagination;
	}
	
	/**
	 * 
	 * @param data		记录
	 * @param current	当前页码
	 * @param total		总条数
	 */
	public PaginationData(List<T> data , int current , Long total) {
		this.data = data;
		this.pagination = new Pagination(current + 1 , total);
	}
	
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	
}
