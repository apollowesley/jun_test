package org.coody.czone.web.user.service;

import org.coody.czone.common.constant.CacheFinal;
import org.coody.czone.web.user.domain.UserGeneral;
import org.coody.framework.cache.annotation.CacheWrite;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.jdbc.JdbcHandle;

@AutoBuild
public class UserGeneralService {

	@AutoBuild
	JdbcHandle jdbcHandle;
	
	
	@CacheWrite(key=CacheFinal.USER_GENERAL,time=72000,fields="userId")
	public UserGeneral getUserGeneral(String userId){
		return jdbcHandle.findBeanFirst(UserGeneral.class,"userId",userId);
	}
}
