package com.evil.struts2.action;

import java.lang.reflect.ParameterizedType;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.util.PageResult;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 *抽象的BaseAction，专门用来继承 
 */

@SuppressWarnings("unchecked")
@Component("BaseAction")
@Scope("prototype")
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>,
		Preparable {

	private static final long serialVersionUID = 1L;
	
	protected int numPerPage; // 每页显示的数量
	protected int pageNum;// 当前选择的页数
	protected String orderField=""; // 排序用的字段
	protected String orderDirection="";// 排序的方式 (升序/降序)
	
	protected PageResult pageResult;//保存分页信息
	
	protected String ids;// 选择的多个id字符串  用","分割
	
	public T model;

	public BaseAction() {
		try {
			ParameterizedType type = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			Class<T> clazz = (Class<T>) type.getActualTypeArguments()[0];
			model = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void prepare() throws Exception {
	}
	@Override
	public T getModel() {
		return model;
	}
	public int getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderDirection() {
		return orderDirection;
	}
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public PageResult getPageResult() {
		return pageResult;
	}
	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}
	
	

}
