package com.zlkj.shiro.dyprem;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zlkj.shiro.dyprem.freemarker.tag.ShiroTags;
import com.zlkj.shiro.dyprem.taglib.OrganizationTageModel;

import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
@Component
public class FreemarkerDirectiveComponent {
		@Autowired   
		private Configuration configuration; 
		@Autowired
	    private void init() throws TemplateModelException{
				Map<String,Object>	mapTag=new HashMap<String,Object>();
				mapTag.put("organization", new OrganizationTageModel());
				mapTag.put("shiro", new ShiroTags());
			 this.configuration.setSharedVaribles(mapTag);
	 }
}
