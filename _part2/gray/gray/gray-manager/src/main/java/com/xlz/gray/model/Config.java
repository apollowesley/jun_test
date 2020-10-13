package com.xlz.gray.model;

import com.xlz.commons.base.model.BaseDomain;

public class Config extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 所属应用 */
	private Long applicationId;  
	/** 对应的系统参数 */
	private Long systemSettingId;  
	/** 参数对应的值 */
	private String settingValue;  
	/** 备注 */
	private String remark;  
  	
	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public Long getSystemSettingId() {
		return systemSettingId;
	}

	public void setSystemSettingId(Long systemSettingId) {
		this.systemSettingId = systemSettingId;
	}
	public String getSettingValue() {
		return settingValue;
	}

	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
