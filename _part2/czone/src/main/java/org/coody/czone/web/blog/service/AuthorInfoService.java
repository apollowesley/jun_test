package org.coody.czone.web.blog.service;

import org.coody.czone.common.constant.CacheFinal;
import org.coody.czone.web.blog.domain.AuthorInfo;
import org.coody.framework.cache.annotation.CacheWrite;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.jdbc.JdbcHandle;

@AutoBuild
public class AuthorInfoService {

	@AutoBuild
	JdbcHandle jdbcHandle;
	
	@CacheWrite(key=CacheFinal.AUTHOR_INFO,time=36000,fields="userId")
	public AuthorInfo getAuthorInfo(String userId){
		return jdbcHandle.findBeanFirst(AuthorInfo.class,"userId",userId);
	} 
}
