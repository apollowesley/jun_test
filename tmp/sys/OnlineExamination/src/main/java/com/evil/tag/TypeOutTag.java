package com.evil.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.evil.pojo.system.TypeDictionary;

/**
 * 根据类型字典的值和类型输出名称（value=0，type=0->试卷的第一个类型）
 * @author frank_evil
 *
 */
public class TypeOutTag extends SimpleTagSupport {

	private Integer value; // 类型的值
	private String name; // 类型的名称

	@SuppressWarnings("unchecked")
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		JspContext jspContext = getJspContext();
		ServletContext application = ((PageContext) jspContext).getServletContext();
		if("paper".equals(name)){
			List<TypeDictionary> paperTypes=(List<TypeDictionary>) application.getAttribute("paperTypes");
			for (TypeDictionary paperType: paperTypes) {
				if(paperType.getValue()==value){
					out.write(paperType.getName());
					break;
				}
			}
		}
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
