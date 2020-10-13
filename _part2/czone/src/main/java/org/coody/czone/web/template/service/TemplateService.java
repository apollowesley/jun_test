package org.coody.czone.web.template.service;

import org.coody.czone.common.constant.CacheFinal;
import org.coody.czone.web.template.domain.TemplateInfo;
import org.coody.framework.cache.annotation.CacheWrite;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.jdbc.JdbcHandle;

@AutoBuild
public class TemplateService {

	@AutoBuild
	JdbcHandle jdbcHandle;
	
	@CacheWrite(key=CacheFinal.TEMPLATE_INFO,time=72000)
	public TemplateInfo getTemplate(String templateId){
		return jdbcHandle.findBeanFirst(TemplateInfo.class,"templateId",templateId);
	}
}
