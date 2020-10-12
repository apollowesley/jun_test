package com.kld.common.framework.result;


import com.kld.common.framework.dto.GridResult;

public class Dubbo4PageResult<T> extends CommonResult <T> {
	
    private static final long serialVersionUID = -7393535371067917688L;
    /** 带分页**/
    private GridResult<T> pageList;
    
	public GridResult<T> getPageList() {
		return pageList;
	}
	public void setPageList(GridResult<T> pageList) {
		this.pageList = pageList;
	}
    
    

}
