package com.leech.pagemodel.base;

import java.util.List;

/**
 * 
 *@Title:SessionInfo
 *@Description:session信息模型
 *@Copyright:Copyright (c) 2014
 *@author li.chang 
 *@version 1.1.0
 *@date:2014年6月5日 上午10:08:59
 */
public class SessionInfo implements java.io.Serializable {
	 /** 
	  * serialVersionUID:TODO（用一句话描述这个变量表示什么） 
	  */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private String id;
	/**
	 * 用户登录名
	 */
	private String loginname; 
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 用户IP
	 */
	private String ip;
	/**
	 * 用户可以访问的资源地址列表
	 */
	private List<String> resourceList;

	public List<String> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

}
