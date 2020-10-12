package com.evil.pojo.statistics;

import java.io.Serializable;

import com.evil.pojo.Questions;

public class QuestionStatisticsModel implements Serializable {
	private static final long serialVersionUID = -1191459602988692373L;
	
	private transient Questions questions; //����
	private long count;              	  //������������
	private long[] optionsCount=new long[4]; //ѡ�����ѡ�������
	private long rightCount;	//�ش���ȷ������

	
	public Questions getQuestions() {
		return questions;
	}
	public void setQuestions(Questions questions) {
		this.questions = questions;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public long[] getOptionsCount() {
		return optionsCount;
	}
	public void setOptionsCount(long[] optionsCount) {
		this.optionsCount = optionsCount;
	}
	public long getRightCount() {
		return rightCount;
	}
	public void setRightCount(long rightCount) {
		this.rightCount = rightCount;
	}
	
	

}
