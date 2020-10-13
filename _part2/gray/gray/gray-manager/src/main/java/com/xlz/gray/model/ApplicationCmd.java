package com.xlz.gray.model;

import com.xlz.commons.base.model.BaseDomain;

public class ApplicationCmd extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 操作命令 */
	private String grayCmd;  
	/** 应用id */
	private String applicationCode;  
	/** 版本 */
	private Integer version;  
  	
	public String getGrayCmd() {
		return grayCmd;
	}

	public void setGrayCmd(String grayCmd) {
		this.grayCmd = grayCmd;
	}
	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
