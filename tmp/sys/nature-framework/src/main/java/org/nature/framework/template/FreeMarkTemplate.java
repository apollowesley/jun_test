package org.nature.framework.template;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature.framework.tag.freemarker.AuthTag;
import org.nature.framework.tag.freemarker.I18nTag;
import org.nature.framework.tag.freemarker.TokenTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkTemplate {
	private static Logger LOGGER = LoggerFactory.getLogger(FreeMarkTemplate.class);
	@SuppressWarnings("deprecation")
	public void sendTemplate(HttpServletRequest req,HttpServletResponse res,String template,Map<String, Object> rootMap){
		Configuration configuration = new Configuration();
		//TODO this is Debug settingS
		configuration.setTemplateUpdateDelay(10000);
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
		//自定义标签
		configuration.setSharedVariable("auth", new AuthTag());
		configuration.setSharedVariable("token", new TokenTag());
		configuration.setSharedVariable("i18n", new I18nTag());
		//configuration.setObjectWrapper(ObjectWrapper.SIMPLE_WRAPPER);
		//value or key can be null
		configuration.setClassicCompatible(true);
		configuration.setLocale(req.getLocale());
		configuration.setServletContextForTemplateLoading(req.getServletContext(), "");
		configuration.setEncoding(req.getLocale(), "UTF-8");
		Writer out=null;
		 try {
			out = new BufferedWriter(
			            new OutputStreamWriter(res.getOutputStream(), "UTF-8"));
			    res.setContentType("text/html; charset=UTF-8");
			
			Template t = configuration.getTemplate(template);
			t.setAutoFlush(true);
			t.process(rootMap, out);
		} catch (Exception e) {
			LOGGER.error("freemarker send template error");
			throw new RuntimeException(e);
		}
	}
	
	
}
