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
	private String name; // ģ������
	private long questionNum; // ���������
	private long questionTypeNum[] = new long[QuestionsUtil.typeNum]; // ��ͬ�������������
	private Date addTime = new Date(); // ���ʱ��
	private String introduce; // ����
	private transient Set<Questions> questions = new HashSet<Questions>();// �Ծ���б�

	private Set<QuestionTemplate> childrens = new HashSet<QuestionTemplate>(); // ����ģ��ļ���
	private QuestionTemplate parent; // ���׽ڵ�

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
