package org.nature.framework.tag.freemarker;

import java.util.Map;

import org.nature.framework.auth.NatureAuther;
import org.nature.framework.util.CastUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateModel;

public class AuthTag implements TemplateDirectiveModel{
	private static Logger LOGGER = LoggerFactory.getLogger(AuthTag.class);
	
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body){
		try {
			String prem = CastUtil.castString(params.get("prem"));
			if (NatureAuther.hasPrem(prem)==0) {
				body.render(env.getOut());
			}
		} catch (Exception e) {
			LOGGER.error("get auth prem error");
		}
	}

}
