package org.nature.framework.tag.jsp;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.nature.framework.auth.NatureAuther;

public class AuthTag extends SimpleTagSupport {
	private String prem;
	private JspContext jspContext;

	public void doTag() throws JspException, IOException {
		if (NatureAuther.hasPrem(prem)==0) {
			getJspBody().invoke(jspContext.getOut());;
		}
	}

	public void setJspContext(JspContext jspContext) {
		this.jspContext = jspContext;
		super.setJspContext(jspContext);
	}


	public JspContext getJspContext() {
		return jspContext;
	}

	public String getPrem() {
		return prem;
	}

	public void setPrem(String prem) {
		this.prem = prem;
	}


}
