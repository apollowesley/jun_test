package com.evil.pojo;

import java.util.Date;

import com.evil.pojo.assist.QuestionTemplate;

// default package

/**
 * Questions entity. @author MyEclipse Persistence Tools
 */

public class Questions extends BaseEntity{
	private static final long serialVersionUID = 4789203497831912331L;

	private String id;
	private String title;
	private Integer questionsType;
	private String content;
	private String answer;
	private String optionA;
	private String optionB;
	private String optionD;
	private String optionC;
	private String optionE;
	private String optionF;
	private String optionG;
	private String optionH;
	private String isDelete;

	private Date insertTime=new Date(System.currentTimeMillis());  //添加记录的时间
	
	private transient QuestionTemplate questionTemplate;//到试题模板的多对一关联
	public Questions() {
	}

//	public Paper getPaper() {
//		return paper;
//	}
//
//	public void setPaper(Paper paper) {
//		this.paper = paper;
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getQuestionsType() {
		return questionsType;
	}

	public void setQuestionsType(Integer questionsType) {
		this.questionsType = questionsType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionE() {
		return optionE;
	}

	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}

	public String getOptionF() {
		return optionF;
	}

	public void setOptionF(String optionF) {
		this.optionF = optionF;
	}

	public String getOptionG() {
		return optionG;
	}

	public void setOptionG(String optionG) {
		this.optionG = optionG;
	}

	public String getOptionH() {
		return optionH;
	}

	public void setOptionH(String optionH) {
		this.optionH = optionH;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public QuestionTemplate getQuestionTemplate() {
		return questionTemplate;
	}

	public void setQuestionTemplate(QuestionTemplate questionTemplate) {
		this.questionTemplate = questionTemplate;
	}
	
	
	
	

}