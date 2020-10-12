package com.course.model;

/**
 * 学生model
 */
public class Student {

	/* 学生ID*/
	private Long sId;

	/* 姓名*/
	private String sName;

	/* 性别（0未知、1男、2女）*/
	private Integer gender;

	/* 所在班级*/
	private Long classId;

	/* 电子邮件*/
	private String email;

	/* 手机号*/
	private String phoneNum;

	/*广播表id*/
	private Long bId;

	public Long getsId() {
		return sId;
	}

	public void setsId(Long sId) {
		this.sId = sId;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Long getbId() {
		return bId;
	}

	public void setbId(Long bId) {
		this.bId = bId;
	}
}