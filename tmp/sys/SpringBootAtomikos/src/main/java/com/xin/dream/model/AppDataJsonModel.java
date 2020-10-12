/**
 * @filename:Const 2017年8月01日
 * @project 微面 边鹏  V1.0
 * Copyright(c) 2017 BianP Co. Ltd. 
 * All right reserved. 
 */
package com.xin.dream.model;

import java.io.Serializable;

import com.github.pagehelper.PageInfo;

/**
 * app响应json数据
 * @author bianp
 * @version $Id: AppDataJsonModel.java, v 0.1 
 * @param <T>
 */
public class AppDataJsonModel<T> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 返回数据
     */
    private Object  data;

    /**
     * 分页组件
     */
    private AppPage appPage = new AppPage();

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public AppPage getAppPage() {
		return appPage;
	}

	public void setAppPage(PageInfo<T> page) {
        if (page != null) {
            this.appPage.setPageAmount(page.getPages());
            this.appPage.setPageNum(page.getPageNum());
            this.appPage.setPageSize(page.getPageSize());
            this.appPage.setTotalAmount(page.getTotal());
        }
	}
    
    
}
