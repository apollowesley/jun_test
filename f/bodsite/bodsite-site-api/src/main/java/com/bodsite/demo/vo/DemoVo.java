package com.bodsite.demo.vo;

import java.io.Serializable;

public class DemoVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer demoId;

	private String name;

	private Integer age;

	private Integer sex;

	public Integer getDemoId() {
		return demoId;
	}

	public void setDemoId(Integer demoId) {
		this.demoId = demoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "DemoVo [demoId=" + demoId + ", name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}
	
}
