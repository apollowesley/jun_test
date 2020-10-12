package com.evil.pojo.statistics;

import java.io.Serializable;

import com.evil.pojo.Questions;

public class QuestionStatisticsModel implements Serializable {
	private static final long serialVersionUID = -1191459602988692373L;
	
	private transient Questions questions; //试题
	private long count;              	  //做这道题的人数
	private long[] optionsCount=new long[4]; //选择各个选项的数量
	private long rightCount;	//回答正确的人数

	
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
