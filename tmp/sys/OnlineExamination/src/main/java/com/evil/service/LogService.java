package com.evil.service;

import com.evil.pojo.system.Log;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface LogService extends BaseService<Log> {
	
	/**
	 * 查询系统日子列表并分页
	 * @param page  分页信息
	 * @param sortfield 排序信息
	 * @return
	 */
	PageResult findLogPage(Page page, String sortfield);

}
