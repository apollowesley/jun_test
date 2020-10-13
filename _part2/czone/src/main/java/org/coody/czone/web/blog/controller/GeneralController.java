package org.coody.czone.web.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.coody.czone.common.cache.RedisCache;
import org.coody.czone.common.constant.GeneralFinal;
import org.coody.czone.common.controller.BaseController;
import org.coody.czone.entity.PageInfo;
import org.coody.czone.web.blog.domain.JournalInfo;
import org.coody.czone.web.blog.domain.NoticeInfo;
import org.coody.czone.web.blog.schema.JournalSchema;
import org.coody.czone.web.blog.service.JournalService;
import org.coody.czone.web.blog.service.NoticeService;
import org.coody.czone.web.user.domain.UserTemplate;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.container.ThreadContainer;
import org.coody.framework.jdbc.entity.Pager;
import org.coody.framework.web.adapt.FormMealAdapt;
import org.coody.framework.web.annotation.ParamsAdapt;
import org.coody.framework.web.annotation.PathBinding;

@PathBinding("/")
public class GeneralController extends BaseController {

	@AutoBuild
	JournalService journalService;
	@AutoBuild
	NoticeService noticeService;
	
	@AutoBuild
	RedisCache redisCache;

	/**
	 * 首页
	 */
	@PathBinding("/index.do")
	public String indexs(HttpServletRequest request) {
		
		redisCache.setCache("AAA", "CCC");
		System.out.println(redisCache.getCache("AAA").toString());
		UserTemplate userTemplate = ThreadContainer.get(GeneralFinal.CURRENT_TEMPLATE_INFO);

		// 加载系统公告
		List<NoticeInfo> notices = noticeService.getNotices(userTemplate.getUserId());
		setAttribute("notices", notices);
		// 加载分页文章
		Pager pager = getBeanAll(PageInfo.class);
		JournalInfo journalInfo = getBeanAll(JournalInfo.class);
		journalInfo.setUserId(userTemplate.getUserId());
		pager = journalService.loadPager(userTemplate.getUserId(), journalInfo, pager);
		setAttribute("pager", pager);
		return "index.jsp";
	}

	/**
	 * 文章详情
	 * 
	 * @param request
	 * @return
	 */
	@PathBinding("/journal.do")
	@ParamsAdapt(FormMealAdapt.class)
	public String journals(HttpServletRequest request) {
		Integer id = getParaInteger("id");
		JournalInfo journal = journalService.loadJournalInfo(id);
		if (journal == null) {
			return "404.jsp";
		}
		setAttribute("title", journal.getTitle());
		JournalSchema journalSchema = new JournalSchema(journal);
		setAttribute("journalSchema", journalSchema);
		return "detail.jsp";
	}

	@PathBinding("/articles.do")
	public String articles() {
		return "articles.jsp";
	}
	
}
