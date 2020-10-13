package org.coody.czone.web.blog.domain;

import java.util.Date;

import org.coody.framework.jdbc.entity.DBModel;

/**
 * 文章表
 * @author Coody
 * @date 2018年8月15日
 */
@SuppressWarnings("serial")
public class JournalInfo extends DBModel{

	/**
	 * 文章ID
	 */
	private Long id;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 文章标题
	 */
	private String title;
	/**
	 * 图标
	 */
	private String ico;
	/**
	 * 文章内容(gzip压缩)
	 */
	private byte[] context;
	/**
	 * 文章简介
	 */
	private String brief;
	/**
	 * 阅读数
	 */
	private Integer views;
	/**
	 * 文章标签
	 */
	private String tags;
	
	/**
	 * 是否置顶
	 */
	private Integer isTopper;
	
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	
	
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getIsTopper() {
		return isTopper;
	}
	public void setIsTopper(Integer isTopper) {
		this.isTopper = isTopper;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public byte[] getContext() {
		return context;
	}
	public void setContext(byte[] context) {
		this.context = context;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	

}
