package com.evil.struts2.frontendAction;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.Paper;
import com.evil.pojo.Questions;
import com.evil.service.PaperService;
import com.evil.struts2.action.BaseAction;

@Component("FrontendFormalTestAction")
@Scope("prototype")
public class FrontendFormalTestAction extends BaseAction<Paper> {
	private static final long serialVersionUID = 4916251175582966524L;

	private List<Questions> questionList; // 保存题目列表

	@Resource(name = "PaperService")
	private PaperService paperservice;

	/**
	 * 跳转到参加参加考试页面
	 */
	public String toFrontendFormalTestPage() {
		Paper paper = paperservice.findFormalTestPaper();
		this.model = paper;
		questionList = new ArrayList<Questions>(model.getQuestions());
		return "frontendFormalTestPage";
	}
	
	public List<Questions> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<Questions> questionList) {
		this.questionList = questionList;
	}
	
}
