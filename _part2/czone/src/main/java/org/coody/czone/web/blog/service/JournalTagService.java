package org.coody.czone.web.blog.service;

import java.util.List;

import org.coody.czone.common.constant.CacheFinal;
import org.coody.czone.web.blog.domain.JournalTag;
import org.coody.framework.cache.annotation.CacheWrite;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.jdbc.JdbcHandle;

@AutoBuild
public class JournalTagService {

	@AutoBuild
	JdbcHandle jdbcHandle;
	
	@CacheWrite(key=CacheFinal.TAG_LIST,time=72000,fields="userId")
	public List<JournalTag> loadTags(String userId){
		return jdbcHandle.findBean(JournalTag.class,"userId",userId,"weight",true);
	}
}
