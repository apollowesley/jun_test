	package org.coody.czone.web.blog.service;

import java.util.List;

import org.coody.czone.common.constant.CacheFinal;
import org.coody.czone.web.blog.domain.BlogNav;
import org.coody.framework.cache.annotation.CacheWrite;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.jdbc.JdbcHandle;

@AutoBuild
public class BlogNavService {

	@AutoBuild
	JdbcHandle jdbcHandle;
	
	
	@CacheWrite(key=CacheFinal.BLOG_NAV_LIST,time=71000,fields="userId")
	public List<BlogNav> loadNavs(String userId){
		return jdbcHandle.findBean(BlogNav.class,"userId",userId,"seq",false);
	}
}
