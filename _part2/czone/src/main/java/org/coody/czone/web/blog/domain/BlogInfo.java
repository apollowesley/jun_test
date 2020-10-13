package org.coody.czone.web.blog.domain;

import org.coody.framework.jdbc.entity.DBModel;

/**
 * 博客信息表
 * @author Coody
 * @date 2018年8月15日
 */
@SuppressWarnings("serial")
public class BlogInfo extends DBModel{

	/**
	 * 所属用户
	 */
	private String userId;
	/**
	 * 网站名称
	 */
	private String siteName;
	/**
	 * 关键词
	 */
	private String keywords;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 备案号
	 */
	private String audit;
	
	/**
	 * 网站签名
	 */
	private String sign;
	
	
	
	
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getAudit() {
		return audit;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
