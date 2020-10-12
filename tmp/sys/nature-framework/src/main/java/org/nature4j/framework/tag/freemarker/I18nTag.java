package org.nature4j.framework.tag.freemarker;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nature4j.framework.cache.NatureContext;
import org.nature4j.framework.helper.I18NHelper;
import org.nature4j.framework.util.CastUtil;
import org.nature4j.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class I18nTag implements TemplateDirectiveModel{
	private static Logger LOGGER = LoggerFactory.getLogger(I18nTag.class);
	@SuppressWarnings("rawtypes")
	public void execute(Environment en, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody){
		String name = CastUtil.castString(map.get("name"));
		String key = null;
		try {
			key = CastUtil.castString(en.__getitem__("locale"));
			if (StringUtil.isEmpty(key)) {
				HttpSession session = NatureContext.getRequest().getSession();
				key = CastUtil.castString(session.getAttribute("locale"));
			}else{
				HttpSession session = NatureContext.getRequest().getSession();
				session.setAttribute("locale", key);
			}
		} catch (TemplateModelException e) {
			LOGGER.error("get locale param error");
			throw new RuntimeException(e);
		}
		
		if (StringUtil.isBank(key)) {
			Locale locale = en.getLocale();
			key = locale.getLanguage()+"_"+locale.getCountry()+"_"+name;
		}else{
			key=key+"_"+name;
		}
		String value = I18NHelper.getValue(key);
		if (value == null) {
			value = key;
			LOGGER.error("i18n'key "+key+" not has a value");
		}
		try {
			en.getOut().write(value);
		} catch (IOException e) {
			LOGGER.error("Environment's IO is error");
			throw new RuntimeException(e);
		}
	}


}
