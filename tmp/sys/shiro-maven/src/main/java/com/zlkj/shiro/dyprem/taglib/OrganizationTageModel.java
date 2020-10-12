package com.zlkj.shiro.dyprem.taglib;
import java.util.List;
import org.springframework.stereotype.Component;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
public class OrganizationTageModel  implements TemplateMethodModelEx{
	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		return Organization.getinstance();
	}

}
