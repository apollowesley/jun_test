package com.xlz.gray.model;

import java.util.ArrayList;
import java.util.List;

import com.xlz.commons.base.model.BaseDomain;

public class Setting extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 设置类型 */
	private String settingType;  
	/** 设置参数名 */
	private String settingParam;  
	/** 设置参数对应的值 */
	private String settingValue;  
	/** 备注 */
	private String remark;  
	/** 是否可以修改（0否1是） */
	private Integer modfiy;  
	/** 分类，可以按照类别取出list */
	private String category;  
  	
	private List<Setting> childrens = new ArrayList<>();
	
	public String getSettingType() {
		return settingType;
	}

	public void setSettingType(String settingType) {
		this.settingType = settingType;
	}
	public String getSettingParam() {
		return settingParam;
	}

	public void setSettingParam(String settingParam) {
		this.settingParam = settingParam;
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
	public Integer getModfiy() {
		return modfiy;
	}

	public void setModfiy(Integer modfiy) {
		this.modfiy = modfiy;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Setting> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Setting> childrens) {
		this.childrens = childrens;
	}

}
