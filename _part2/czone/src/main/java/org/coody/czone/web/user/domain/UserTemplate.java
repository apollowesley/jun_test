package org.coody.czone.web.user.domain;

import org.coody.framework.jdbc.entity.DBModel;

/**
 * 用户模板设置表
 * @author Coody
 * @date 2018年8月15日
 */
@SuppressWarnings("serial")
public class UserTemplate extends DBModel{

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户域名
	 */
	private String domain;
	/**
	 * 用户模板
	 */
	private String templateId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	


}
