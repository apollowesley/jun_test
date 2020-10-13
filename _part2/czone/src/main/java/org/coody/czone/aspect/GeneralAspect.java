package org.coody.czone.aspect;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.coody.czone.common.constant.GeneralFinal;
import org.coody.czone.web.blog.domain.AuthorInfo;
import org.coody.czone.web.blog.domain.BlogInfo;
import org.coody.czone.web.blog.domain.BlogNav;
import org.coody.czone.web.blog.domain.JournalInfo;
import org.coody.czone.web.blog.domain.JournalTag;
import org.coody.czone.web.blog.domain.LinkInfo;
import org.coody.czone.web.blog.service.AuthorInfoService;
import org.coody.czone.web.blog.service.BlogInfoService;
import org.coody.czone.web.blog.service.BlogNavService;
import org.coody.czone.web.blog.service.JournalService;
import org.coody.czone.web.blog.service.JournalTagService;
import org.coody.czone.web.blog.service.LinkInfoService;
import org.coody.czone.web.template.domain.TemplateInfo;
import org.coody.czone.web.template.service.TemplateService;
import org.coody.czone.web.user.domain.UserGeneral;
import org.coody.czone.web.user.domain.UserTemplate;
import org.coody.czone.web.user.service.UserGeneralService;
import org.coody.czone.web.user.service.UserTemplateService;
import org.coody.framework.core.annotation.Around;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.container.ThreadContainer;
import org.coody.framework.core.entity.AspectPoint;
import org.coody.framework.core.util.StringUtil;
import org.coody.framework.web.annotation.PathBinding;
import org.coody.framework.web.container.HttpContainer;
import org.coody.framework.web.util.RequestUtil;

@AutoBuild
public class GeneralAspect {

	@AutoBuild
	UserTemplateService userTemplateService;
	@AutoBuild
	TemplateService templateService;
	@AutoBuild
	BlogInfoService blogInfoService;
	@AutoBuild
	LinkInfoService linkInfoService;
	@AutoBuild
	JournalTagService journalTagService;
	@AutoBuild
	AuthorInfoService authorInfoService;
	@AutoBuild
	UserGeneralService userGeneralService;
	@AutoBuild
	BlogNavService blogNavService;
	@AutoBuild
	JournalService journalService;

	/**
	 * 博客基本控制
	 * 
	 * @param wrapper
	 * @return
	 * @throws Throwable
	 */
	@Around(annotationClass = PathBinding.class)
	public Object blogChoice(AspectPoint point) throws Throwable {
		// AOP获取方法执行信息
		HttpServletRequest request = HttpContainer.getRequest();
		// 模板控制
		String domain = request.getServerName();
		UserTemplate userTemplate = userTemplateService.getUserTemplateByDomain(domain);
		if (userTemplate == null) {
			return null;
		}
		ThreadContainer.set(GeneralFinal.CURRENT_TEMPLATE_INFO, userTemplate);
		TemplateInfo templateInfo = templateService.getTemplate(userTemplate.getTemplateId());
		if (templateInfo == null) {
			return null;
		}
	
		String basePath = RequestUtil.loadBasePath(request);
		String templatePath = basePath + templateInfo.getDir();
		request.setAttribute("templatePath", templatePath);
		// 加载博客设置
		BlogInfo info = blogInfoService.getBlogInfo(userTemplate.getUserId());
		request.setAttribute("blogInfo", info);
		// 加载友情链接
		List<LinkInfo> links = linkInfoService.loadLinks(userTemplate.getUserId());
		request.setAttribute("links", links);
		// 加载标签列表
		List<JournalTag> tags = journalTagService.loadTags(userTemplate.getUserId());
		request.setAttribute("journalTags", tags);
		// 加载博主信息
		AuthorInfo authorInfo = authorInfoService.getAuthorInfo(userTemplate.getUserId());
		request.setAttribute("authorInfo", authorInfo);
		// 加载当前用户信息
		UserGeneral userGeneral = userGeneralService.getUserGeneral(userTemplate.getUserId());
		request.setAttribute("userGeneral", userGeneral);
		// 加载导航栏列表
		List<BlogNav> blogNavs = blogNavService.loadNavs(userTemplate.getUserId());
		request.setAttribute("blogNavs", blogNavs);
		// 加载最新文章列表
		List<JournalInfo> journalNewsd = journalService.loadNews(userTemplate.getUserId());
		request.setAttribute("journalNewsd", journalNewsd);
		// 加载最热文章列表
		List<JournalInfo> journalHosed = journalService.loadHots(userTemplate.getUserId());
		request.setAttribute("journalHosed", journalHosed);
		//加载置顶文章
		List<JournalInfo> journalTopper = journalService.loadToppers(userTemplate.getUserId());
		request.setAttribute("journalTopper", journalTopper);
		
		Object result = point.invoke();
		if (StringUtil.isNullOrEmpty(result)) {
			return null;
		}
		result = StringUtil.formatPath("/" + templateInfo.getDir() + "/" + result.toString());
		return result;
	}
}
