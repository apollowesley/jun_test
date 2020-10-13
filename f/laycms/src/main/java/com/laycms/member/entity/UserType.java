package com.laycms.member.entity;

/**
 * 考试点用户类型
 * @author kouis
 *
 */
public enum UserType {
	
	STUDENT("学生"),

	TEACHER("老师"),
	
	CMSUSER("cms用户"),
	
	ASSISTANT("助教"),
	;

	private String label;
	
	private UserType(String label) {

		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	

}