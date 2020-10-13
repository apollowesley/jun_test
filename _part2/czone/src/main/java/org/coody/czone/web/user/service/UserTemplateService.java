package org.coody.czone.web.user.service;

import org.coody.czone.common.constant.CacheFinal;
import org.coody.czone.web.user.domain.UserTemplate;
import org.coody.framework.cache.annotation.CacheWrite;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.jdbc.JdbcHandle;

@AutoBuild
public class UserTemplateService {

	@AutoBuild
	JdbcHandle jdbcHandle;
	
	@CacheWrite(key=CacheFinal.USER_TEMPLATE_INFO,time=72000,fields="userId")
	public UserTemplate getUserTemplate(String userId){
		return jdbcHandle.findBeanFirst(UserTemplate.class,"userId",userId);
	}
	
	@CacheWrite(key=CacheFinal.USER_TEMPLATE_INFO,time=72000,fields="domain")
	public UserTemplate getUserTemplateByDomain(String domain){
		return jdbcHandle.findBeanFirst(UserTemplate.class,"domain",domain);
	}
	
}
