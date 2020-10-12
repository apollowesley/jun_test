/**
 * @filename:Const 2017年8月01日
 * @project 微面 边鹏  V1.0
 * Copyright(c) 2017 BianP Co. Ltd. 
 * All right reserved. 
 */
package com.xin.dream.model;

import java.io.Serializable;

/**
 * app分页组件
 * @author bianP
 * @version $Id: AppPage.java, v 0.1 2017年10月30日 下午2:31:23 
 */
public class AppPage  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
     * 当前页
     */
    private int pageNum=1;
    /**
     * 每页记录数
     */
    private int pageSize=10;
    /**
     * 总页数
     */
    private int pageAmount;
    /**
     * 总记录数
     */
    private long totalAmount;
    /**
     * 分页外的其它参数
     */
    private Record record=new Record();
    
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageAmount() {
        return pageAmount;
    }
    public void setPageAmount(int pageAmount) {
        this.pageAmount = pageAmount;
    }
    public long getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }
	public Record getRecord() {
		return record;
	}
	public void setRecord(Record record) {
		this.record = record;
	}
}
