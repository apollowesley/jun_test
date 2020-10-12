package com.evil.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.BaseDao;
import com.evil.pojo.system.Log;
import com.evil.service.LogService;
import com.evil.util.Page;
import com.evil.util.PageResult;
import com.evil.util.PageUtil;
import com.evil.util.ValidateUtil;

@Service("logService")
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService {
	@Override
	@Resource(name = "logDao")
	public void setBaseDao(BaseDao<Log> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public PageResult findLogPage(Page page, String sortfield) {
		String hql = "select count(*) from Log  where 1=1";
		Long count = null;
		count = (Long) this.uniqueResult(hql);
		count = count == null ? 0 : count;
		page = PageUtil.createPage(page.getNumPerPage(), count,
				page.getCurrentPage());// 根据总记录数创建分页信息
		hql = "from Log where 1=1";
		if(!ValidateUtil.isNull(sortfield))
			hql +=" order by "+sortfield;
		List<Log> list = this.findEntityByPage(hql,
				page.getCurrentPage(), page.getNumPerPage());// 通过分页信息取得试题
		PageResult result = new PageResult(page, list);// 封装分页信息和记录信息，返回给调用处
		return result;
	}
}
