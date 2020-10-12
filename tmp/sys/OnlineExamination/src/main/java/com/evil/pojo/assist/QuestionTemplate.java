package com.evil.pojo.assist;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.evil.pojo.BaseEntity;
import com.evil.pojo.Questions;
import com.evil.util.QuestionsUtil;

public class QuestionTemplate extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id; // id
	private String name; // 模板名称
	private long questionNum; // 试题的数量
	private long questionTypeNum[] = new long[QuestionsUtil.typeNum]; // 不同类型试题的数量
	private Date addTime = new Date(); // 添加时间
	private String introduce; // 介绍
	private transient Set<Questions> questions = new HashSet<Questions>();// 试卷的列表

	private Set<QuestionTemplate> childrens = new HashSet<QuestionTemplate>(); // 孩子模板的集合
	private QuestionTemplate parent; // 父亲节点

	// init....
	public QuestionTemplate() {
	}

	public QuestionTemplate(String id) {
		this.id = id;
	}

	// /get/set...
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Long questionNum) {
		this.questionNum = questionNum==null?0:questionNum;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Set<QuestionTemplate> getChildrens() {
		return childrens;
	}

	public void setChildrens(Set<QuestionTemplate> childrens) {
		this.childrens = childrens;
	}

	public QuestionTemplate getParent() {
		return parent;
	}

	public void setParent(QuestionTemplate parent) {
		this.parent = parent;
	}

	public Set<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Questions> questions) {
		this.questions = questions;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public long[] getQuestionTypeNum() {
		return questionTypeNum;
	}

	public void setQuestionTypeNum(long[] questionTypeNum) {
		this.questionTypeNum = questionTypeNum;
	}

}
