package com.evil.struts2.form;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import com.evil.util.QuestionsUtil.QuestionType;

public class TemplateForm {
	private String id; // 模板的id
	private String name; // 模板的名称
	private long singleNum;// 单选题数量
	private long multipleNum;// 多选题数量
	private long judgeNum;// 判断题数量

	private EnumMap<QuestionType, Long> numMap;// 保存不同的类型问题数量的map
	
	public static ArrayList<TemplateForm> ListDeduplication(List<TemplateForm> templateForms){
		ArrayList<TemplateForm> newTemplateForms=new ArrayList<TemplateForm>();
		for (TemplateForm tf : templateForms) {
			int i=0,length=newTemplateForms.size();
			for (i = 0; i <length ; i++) {
				TemplateForm newtf =newTemplateForms.get(i);
				if(newtf.getId().equals(tf.getId())){  //如果是同一模板就将随机数量叠加
					newtf.setJudgeNum(newtf.getJudgeNum()+tf.getJudgeNum());
					newtf.setMultipleNum(newtf.getMultipleNum()+tf.getMultipleNum());
					newtf.setSingleNum(newtf.getSingleNum()+tf.getSingleNum());
					break;
				}
			}
			if(i>=length){
				newTemplateForms.add(tf);
			}
		}
		return newTemplateForms;
	}
	
	// get/set方法。。。。
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSingleNum() {
		return singleNum;
	}

	public void setSingleNum(long singleNum) {
		this.singleNum = singleNum;
	}

	public long getMultipleNum() {
		return multipleNum;
	}

	public void setMultipleNum(long multipleNum) {
		this.multipleNum = multipleNum;
	}

	public long getJudgeNum() {
		return judgeNum;
	}

	public void setJudgeNum(long judgeNum) {
		this.judgeNum = judgeNum;
	}

	public EnumMap<QuestionType, Long> getNumMap() {
		numMap = new EnumMap<QuestionType, Long>(QuestionType.class);
		if (singleNum != 0) {
			numMap.put(QuestionType.single, singleNum);
		}
		if (multipleNum != 0) {
			numMap.put(QuestionType.multiple, multipleNum);
		}
		if (judgeNum != 0) {
			numMap.put(QuestionType.judge, judgeNum);
		}
		return numMap;
	}

	public void setNumMap(EnumMap<QuestionType, Long> numMap) {
		this.numMap = numMap;
	}

}
