package org.nature4j.framework.tag.freemarker;

import java.io.Writer;
import java.util.Map;
import java.util.UUID;

import org.nature4j.framework.cache.InvocationTokenStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateModel;

public class TokenTag implements TemplateDirectiveModel{
	private static Logger LOGGER = LoggerFactory.getLogger(TokenTag.class);
	@SuppressWarnings("rawtypes")
	public void execute(Environment en, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody){
		String value = UUID.randomUUID().toString().replaceAll("-", "");
		InvocationTokenStore.put(value);
		try {
			Writer out = en.getOut();
			out.write("<input type=\"hidden\" name=\"token\" value=\""+value+"\"/>");
		} catch (Exception e) {
			LOGGER.error("Environment's IO is error");
			throw new RuntimeException(e);
		}
	}
}
