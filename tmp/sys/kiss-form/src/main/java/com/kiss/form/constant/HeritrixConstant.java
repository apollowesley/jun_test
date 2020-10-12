/**
 * 类名：HeritrixConstant.java
 * 工程名：hrrm-form
 * 包名：com.hrrm.kiss.form.constant	
 */
package com.kiss.form.constant;

/**
 * @author leyvi
 * @since 2013-8-13下午6:07:04
 */
public enum HeritrixConstant {
	HERITRIX_PATH("HERITRIX_PATH");
	
	private String value;
	HeritrixConstant(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
