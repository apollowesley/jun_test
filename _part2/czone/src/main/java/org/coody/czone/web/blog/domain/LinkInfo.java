package org.coody.czone.web.blog.domain;

import java.util.Date;

import org.coody.framework.jdbc.entity.DBModel;

/**
 * 友情链接表
 * @author Coody
 * @date 2018年8月15日
 */
@SuppressWarnings("serial")
public class LinkInfo extends DBModel{

	/**
	 * 链接ID
	 */
	private Integer linkId;
	/**
	 * 链接地址
	 */
	private String href;
	/**
	 * 状态，1开启、0关闭
	 */
	private Integer status;
	/**
	 * 链接显示标题
	 */
	private String title;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	public Integer getLinkId() {
		return linkId;
	}
	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	

}
