package com.kld.common.framework.bizService.impl;

import com.kld.common.framework.dto.GridQueryPara;
import com.kld.common.framework.dto.GridResult;
import com.kld.common.framework.dto.QueryParaDto;
import com.kld.common.framework.service.BaseService;

import java.util.List;



public abstract class BaseBizServiceImpl<T> {
	public abstract BaseService<T> getBaseService();
	
	public int insert(T entity){
		return getBaseService().insert(entity);
	}
	public int update(T entity){
		return getBaseService().update(entity);
	}
	public <I> int delete(I id){
		return getBaseService().delete(id);
	}
	public <I> int deleteMulti(boolean haveFK, final I... ids){
		return getBaseService().deleteMulti(haveFK, ids);
	}
	public <I> T get(I id){
		return getBaseService().get(id);
	}
	public <I> List<T> find(I params){
		return getBaseService().find(params);
	}
	public <I> GridResult<I> getPageList(GridQueryPara page, QueryParaDto query){
		return getBaseService().getPageList(page, query);
	}
	public <I> GridResult<I> getPageList(GridQueryPara page){
		return getBaseService().getPageList(page);
	}
}
