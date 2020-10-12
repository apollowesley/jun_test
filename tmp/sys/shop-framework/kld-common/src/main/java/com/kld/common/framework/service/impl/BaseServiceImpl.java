package com.kld.common.framework.service.impl;

import com.kld.common.framework.dao.BaseDao;
import com.kld.common.framework.dto.GridQueryPara;
import com.kld.common.framework.dto.GridResult;
import com.kld.common.framework.dto.QueryParaDto;

import java.util.List;



public abstract class BaseServiceImpl<T> {
	public abstract BaseDao<T> getBaseDao();
	
	public int insert(T entity){
		return getBaseDao().insert(entity);
	}
	public int update(T entity){
		return getBaseDao().update(entity);
	}
	public <I> int delete(I id){
		return getBaseDao().delete(id);
	}
	public <I> int deleteMulti(boolean haveFK, final I... ids){
		return getBaseDao().deleteMulti(haveFK, ids);
	}
	public <I> T get(I id){
		return getBaseDao().get(id);
	}
	public <I> List<T> find(I params){
		return getBaseDao().find(params);
	}
	public <I> GridResult<I> getPageList(GridQueryPara page, QueryParaDto query){
		return getBaseDao().getPageList(page, query);
	}
	public <I> GridResult<I> getPageList(GridQueryPara page){
		return getBaseDao().getPageList(page);
	}
}
