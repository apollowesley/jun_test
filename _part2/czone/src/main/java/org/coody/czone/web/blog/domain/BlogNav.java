package org.coody.czone.web.blog.domain;

import org.coody.framework.jdbc.entity.DBModel;

/**
 * 博客导航栏表
 * @author Coody
 * @date 2018年8月15日
 */
@SuppressWarnings("serial")
public class BlogNav extends DBModel{

	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 导航名
	 */
	private String nav;
	/**
	 * 导航链接
	 */
	private String href;
	/**
	 * 排序
	 */
	private Integer seq;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNav() {
		return nav;
	}
	public void setNav(String nav) {
		this.nav = nav;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	

}
