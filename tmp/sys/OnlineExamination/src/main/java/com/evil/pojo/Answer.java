package com.evil.pojo;

import java.util.*;;

public class Answer extends BaseEntity {
	private static final long serialVersionUID = 6740253737676397837L;
	
	private String id;
	private String answers;//答案
	private String userid;//批次
	private Date answerTime; //完成的时间
	private String questionId; //问题的索引
	private String paperid;  //试卷索引
	
	private boolean rights;  //是否正确
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getPaperid() {
		return paperid;
	}
	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}
	public boolean isRights() {
		return rights;
	}
	public void setRights(boolean rights) {
		this.rights = rights;
	}
	
	
	
	
	
	
	
	
	

}
