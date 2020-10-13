package com.xlz.gray.model;

import com.xlz.commons.base.model.BaseDomain;

public class ApplicationService extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 名称 */
	private String name;  
	/** http，dubbo,threft */
	private String type;  
	/** version */
	private String version;  
	/** 被灰度系统Id */
	private Long applicationId;  
	/** 默认为userNo */
	private String param;  
	/** 可以按照用户特征等将其分组 */
	private String remark;  
  	
	private Object strategy ;
	private Object whitelists ;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Object getStrategy() {
		return strategy;
	}

	public void setStrategy(Object strategy) {
		this.strategy = strategy;
	}

	public Object getWhitelists() {
		return whitelists;
	}

	public void setWhitelists(Object whiteLists) {
		this.whitelists = whiteLists;
	}

}
