package org.coody.czone.web.blog.service;

import org.coody.czone.common.constant.CacheFinal;
import org.coody.czone.web.blog.domain.BlogInfo;
import org.coody.framework.cache.annotation.CacheWrite;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.jdbc.JdbcHandle;

@AutoBuild
public class BlogInfoService {

	@AutoBuild
	JdbcHandle jdbcHandle;
	
	@CacheWrite(key=CacheFinal.BLOG_INFO,time=72000,fields="userId")
	public BlogInfo getBlogInfo(String userId){
		return jdbcHandle.findBeanFirst(BlogInfo.class,"userId",userId);
	}
}
