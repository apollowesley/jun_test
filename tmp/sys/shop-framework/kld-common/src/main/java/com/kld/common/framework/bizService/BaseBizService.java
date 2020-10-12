package com.kld.common.framework.bizService;

import com.kld.common.framework.dto.GridQueryPara;
import com.kld.common.framework.dto.GridResult;
import com.kld.common.framework.dto.QueryParaDto;

import java.util.List;



public interface BaseBizService<T> {
	public int insert(T entity);
	public int update(T entity);
	public <I> int delete(I id);
	public <I> int deleteMulti(boolean haveFK, final I... ids);
	public <I> T get(I id);
	public <I> List<T> find(I params);
	public <I> GridResult<I> getPageList(GridQueryPara page, QueryParaDto query);
	public <I> GridResult<I> getPageList(GridQueryPara page);
}
