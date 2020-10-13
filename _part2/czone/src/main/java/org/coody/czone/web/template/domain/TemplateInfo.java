package org.coody.czone.web.template.domain;

import org.coody.framework.jdbc.entity.DBModel;

/**
 * 博客模板表(目前只一套模板)
 * @author Coody
 * @date 2018年8月15日
 */
@SuppressWarnings("serial")
public class TemplateInfo extends DBModel{
	
	/**
	 * 模板ID （UUID）
	 */
	private String templateId;
	/**
	 * 模板目录
	 */
	private String dir;
	/**
	 * 使用次数
	 */
	private Integer useNum;
	/**
	 * 模板名称
	 */
	private String templateName;
	
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public Integer getUseNum() {
		return useNum;
	}
	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}


}
