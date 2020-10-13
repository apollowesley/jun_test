package org.coody.czone.web.blog.service;

import java.util.List;

import org.coody.czone.common.constant.CacheFinal;
import org.coody.czone.web.blog.domain.NoticeInfo;
import org.coody.framework.cache.annotation.CacheWrite;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.jdbc.JdbcHandle;
import org.coody.framework.jdbc.entity.Where;

@AutoBuild
public class NoticeService {

	@AutoBuild
	JdbcHandle jdbcHandle;

	@CacheWrite(fields="userId", key = CacheFinal.NOTICE_LIST, time = 3600)
	public List<NoticeInfo> getNotices(String userId) {
		Where where = new Where();
		where.set("userId", userId);
		where.set("status", 1);
		return jdbcHandle.findBean(NoticeInfo.class, where, "seq", false);
	}
}
