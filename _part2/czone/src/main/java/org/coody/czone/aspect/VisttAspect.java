package org.coody.czone.aspect;

import javax.servlet.http.HttpServletRequest;

import org.coody.czone.task.VisitQueue;
import org.coody.czone.web.user.domain.UserTemplate;
import org.coody.czone.web.user.service.UserTemplateService;
import org.coody.framework.core.annotation.Around;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.entity.AspectPoint;
import org.coody.framework.web.annotation.PathBinding;
import org.coody.framework.web.container.HttpContainer;

@AutoBuild
public class VisttAspect {
	
	@AutoBuild
	UserTemplateService userTemplateService;
	@AutoBuild
	VisitQueue visitQueue;

	/**
	 * 访问统计
	 * 
	 * @param wrapper
	 * @return
	 * @throws Throwable
	 */
	@Around(annotationClass = PathBinding.class)
	public Object visitChoice(AspectPoint point) throws Throwable {
		// 模板控制
		HttpServletRequest request = HttpContainer.getRequest();
		String domain = request.getServerName();
		UserTemplate userTemplate = userTemplateService.getUserTemplateByDomain(domain);
		visitQueue.writeVisit(userTemplate.getUserId());
		return point.invoke();
	}
}
