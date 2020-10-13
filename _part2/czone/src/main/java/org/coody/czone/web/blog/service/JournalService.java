package org.coody.czone.web.blog.service;

import java.util.List;

import org.coody.czone.common.constant.CacheFinal;
import org.coody.czone.web.blog.domain.JournalInfo;
import org.coody.framework.cache.annotation.CacheWrite;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.util.StringUtil;
import org.coody.framework.jdbc.JdbcHandle;
import org.coody.framework.jdbc.entity.Pager;
import org.coody.framework.jdbc.entity.Where;

@AutoBuild
public class JournalService {

	@AutoBuild
	JdbcHandle jdbcHandle;

	@CacheWrite(key = CacheFinal.JOURNAL_PAGER)
	public Pager loadPager(String userId, JournalInfo journal, Pager pager) {
		Where where = new Where();
		where.set("userId", userId);
		return jdbcHandle.findPager(journal, where, pager, "id", false);
	}

	/**
	 * 加载最新文章
	 */
	@CacheWrite(key = CacheFinal.JOURNAL_LIST_NEWSD, time = 345600)
	public List<JournalInfo> loadNews(String userId) {
		Pager pager = new Pager();
		pager.setPageSize(12);
		Where where = new Where();
		where.set("userId", userId);
		pager = jdbcHandle.findPager(JournalInfo.class, where, pager, "id", true);
		if (StringUtil.isNullOrEmpty(pager.getData())) {
			return null;
		}
		return pager.getData();
	}

	/**
	 * 加载最热文章
	 */
	@CacheWrite(key = CacheFinal.JOURNAL_LIST_HOTSD, time = 345600)
	public List<JournalInfo> loadHots(String userId) {
		Pager pager = new Pager();
		pager.setPageSize(12);
		Where where = new Where();
		where.set("userId", userId);
		pager = jdbcHandle.findPager(JournalInfo.class, where, pager, "views", true);
		if (StringUtil.isNullOrEmpty(pager.getData())) {
			return null;
		}
		return pager.getData();
	}

	/**
	 * 加载置顶文章
	 */
	@CacheWrite(key = CacheFinal.JOURNAL_LIST_TOPPER, time = 345600)
	public List<JournalInfo> loadToppers(String userId) {
		Pager pager = new Pager();
		pager.setPageSize(12);
		Where where = new Where();
		where.set("userId", userId);
		where.set("isTopper", 1);
		pager = jdbcHandle.findPager(JournalInfo.class, where, pager, "updateTime", true);
		if (StringUtil.isNullOrEmpty(pager.getData())) {
			return null;
		}
		return pager.getData();
	}

	/**
	 * 获得文章详情
	 */
	@CacheWrite(key = CacheFinal.JOURNAL_INFO, time = 72000, fields = "id")
	public JournalInfo loadJournalInfo(Integer id) {
		Where where = new Where();
		where.set("id", id);
		JournalInfo info = jdbcHandle.findBeanFirst(JournalInfo.class, where);
		return info;
	}
}
