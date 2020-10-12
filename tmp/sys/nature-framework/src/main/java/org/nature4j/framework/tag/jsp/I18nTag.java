package org.nature4j.framework.tag.jsp;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.nature4j.framework.cache.NatureContext;
import org.nature4j.framework.helper.I18NHelper;
import org.nature4j.framework.util.CastUtil;
import org.nature4j.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I18nTag extends SimpleTagSupport {
	private static Logger LOGGER = LoggerFactory.getLogger(I18nTag.class);
	private String name;
	private JspContext jspContext;

	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) jspContext;
		ServletRequest request = pageContext.getRequest();
		String key = request.getParameter("locale");
		
		if (StringUtil.isEmpty(key)) {
			HttpSession session = NatureContext.getRequest().getSession();
			key = CastUtil.castString(session.getAttribute("locale"));
		}else{
			HttpSession session = NatureContext.getRequest().getSession();
			session.setAttribute("locale", key);
		}
		
		if (StringUtil.isEmpty(key)) {
			Locale locale = request.getLocale();
			key = locale.getLanguage() + "_" + locale.getCountry() + "_" + name;
		}else {
			key = key+"_"+name;
		}
		String value = I18NHelper.getValue(key);
		if (value == null) {
			value = key;
			LOGGER.error("i18n'key "+key+" not has a value");
		}
		jspContext.getOut().write(value);
		super.doTag();
	}

	public void setJspContext(JspContext jspContext) {
		this.jspContext = jspContext;
		super.setJspContext(jspContext);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JspContext getJspContext() {
		return jspContext;
	}


}
