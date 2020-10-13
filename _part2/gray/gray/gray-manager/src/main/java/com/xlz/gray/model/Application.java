package com.xlz.gray.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xlz.commons.base.model.BaseDomain;

public class Application extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 名称 */
	private String name;  
	/** group_id */
	private String applicationId;  
	/** 部门id */
	private String organizationId;  
	/** 灰度参数名称*/
	private String param;  
	/** 可以按照用户特征等将其分组 */
	private String remark;  
	
	private String nginxs;
	
	private Object strategy = new HashMap<>(); 
	private Map<String ,String> config = new HashMap<>(); 
  	
	private Object whitelists;
	private List<ApplicationService> services;
	
	/**当前应用所配置的引擎*/
	private String engine;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getNginxs() {
		return nginxs;
	}

	public void setNginxs(String nginxs) {
		this.nginxs = nginxs;
	}

	public Object getStrategy() {
		return strategy;
	}

	public void setStrategy(Object strategy) {
		this.strategy = strategy;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Object getWhitelists() {
		return whitelists;
	}

	public void setWhitelists(Object whitelists) {
		this.whitelists = whitelists;
	}

	public List<ApplicationService> getServices() {
		return services;
	}

	public void setServices(List<ApplicationService> services) {
		this.services = services;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public Map<String, String> getConfig() {
		return config;
	}

	public void setConfig(Map<String, String> config) {
		this.config = config;
	}
}
