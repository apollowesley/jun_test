package com.evil.pojo;

// default package

/**
 * WrongQuestions entity. @author MyEclipse Persistence Tools
 */

public class WrongQuestions extends BaseEntity {
	private static final long serialVersionUID = 7841671202764173560L;

	private String id;
	private Integer correctNumber;
	private String userId; // 用户的id

	// 从WrongQuestions到Questions的多对一映射
	private Questions questions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCorrectNumber() {
		return correctNumber;
	}

	public void setCorrectNumber(Integer correctNumber) {
		this.correctNumber = correctNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Questions getQuestions() {
		return questions;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}

}