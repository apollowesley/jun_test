package org.coody.czone.web.blog.domain;

import org.coody.framework.jdbc.entity.DBModel;

/**
 * 标签表
 * @author Coody
 * @date 2018年8月15日
 */
@SuppressWarnings("serial")
public class JournalTag extends DBModel{

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 标签名称
	 */
	private String tag;
	/**
	 * 权重
	 */
	private Integer weight;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	

}
