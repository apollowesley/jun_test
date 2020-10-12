package org.nature4j.framework.tag.jsp;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.nature4j.framework.cache.InvocationTokenStore;

public class TokenTag extends SimpleTagSupport {
	private String name;
	private JspContext jspContext;

	@Override
	public void doTag() throws JspException, IOException {
		String value = UUID.randomUUID().toString().replaceAll("-", "");
		InvocationTokenStore.put(value);
		jspContext.getOut().write("<input type=\"hidden\" name=\"token\" value=\""+value+"\"/>");
		super.doTag();
	}

	@Override
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
