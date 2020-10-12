package org.nature4j.framework.auth;

import java.util.Set;

import org.nature4j.framework.core.NatureMap;
import org.nature4j.framework.core.NatureService;
import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.helper.ServiceHelper;
import org.nature4j.framework.util.CastUtil;
import org.nature4j.framework.util.StringUtil;

public class AuthService {
	public static Set<String> getPrems(Object username) {
		NatureService natureService = getNatureServcie();
		String queryPremsSql = ConfigHelper.getQueryPremsSql();
		Set<String> prems;
		if (StringUtil.isNotEmpty(ConfigHelper.getAuthCacheName())) {
			prems=natureService.querySet(queryPremsSql,ConfigHelper.getAuthCacheName(), username);
		}else{
			prems=natureService.querySet(queryPremsSql, username);
		}
		return prems;
	}

	public static NatureMap getUserInfo(Object username) {
		NatureService natureService = getNatureServcie();
		String queryUserSql = ConfigHelper.getQueryUserInfoSql();
		NatureMap userInfo;
		if (StringUtil.isNotEmpty(ConfigHelper.getAuthCacheName())) {
			userInfo= natureService.unique(queryUserSql,username);
		}else{
			userInfo= natureService.unique(queryUserSql,username);
		}
		return userInfo;
	}

	public static String getStorePassword(Object username) {
		NatureService natureService = getNatureServcie();
		String queryPasswordSql = ConfigHelper.getQueryPaswordSql();
		return CastUtil.castString(natureService.uniqueValue(queryPasswordSql, username));
	}
	public static Set<String> getAllPrems() {
		NatureService natureService = getNatureServcie();
		String querySystemPrems = ConfigHelper.getQueryAllPremsSql();
		Set<String> allPrems;
		if (StringUtil.isNotEmpty(ConfigHelper.getAuthCacheName())) {
			allPrems = natureService.querySet(querySystemPrems,ConfigHelper.getAuthCacheName());
		}else{
			allPrems = natureService.querySet(querySystemPrems);
		}
		return allPrems; 
	}
	
	private static NatureService getNatureServcie() {
		NatureService natureService = (NatureService) ServiceHelper.getService(NatureService.class);
		return natureService;
	}
}
