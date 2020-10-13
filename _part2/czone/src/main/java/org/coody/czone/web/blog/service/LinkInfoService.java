package org.coody.czone.web.blog.service;

import java.util.List;

import org.coody.czone.common.constant.CacheFinal;
import org.coody.czone.web.blog.domain.LinkInfo;
import org.coody.framework.cache.annotation.CacheWrite;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.jdbc.JdbcHandle;
import org.coody.framework.jdbc.entity.Where;

@AutoBuild
public class LinkInfoService {

	@AutoBuild
	JdbcHandle jdbcHandle;
	
	@CacheWrite(key=CacheFinal.LINK_LIST,time=72000,fields="userId")
	public List<LinkInfo> loadLinks(String userId){
		Where where=new Where();
		where.set("userId",userId);
		where.set("status", 1);
		return jdbcHandle.findBean(LinkInfo.class,where);
	}
}
