package com.evil.struts2.frontendAction;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.Questions;
import com.evil.pojo.WrongQuestions;
import com.evil.service.PaperService;
import com.evil.struts2.action.BaseAction;
import com.evil.util.JsonUtil;
import com.evil.util.Page;

@Component("WrongQuestionAction")
@Scope(value = "prototype")
public class WrongQuestionAction extends BaseAction<WrongQuestions> {
	private static final long serialVersionUID = -6406708386352231048L;

	@Resource
	// 注入PaperService
	private PaperService paperService;

	private String wid;// 错题的id
	private String qid;// 问题的id
	private int questionType; //问题的类型

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	/**
	 * 显示错题页面
	 */
	public String toMyWrongQuestionsPage() {
		Page page = new Page();
		page.setCurrentPage(pageNum);
		page.setNumPerPage(6);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questions.questionsType", 0);
		pageResult = paperService.findPageWrongs(page, map);
		return "myWrongQuestionsPage";
	}
	
	/**
	 *根据问题的类型查询出题列表
	 */
	public void showTypeWrongQuestion(){
		Page page = new Page();
		page.setCurrentPage(pageNum);
		page.setNumPerPage(6);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questions.questionsType", questionType);
		pageResult = paperService.findPageWrongs(page, map);
		JsonUtil.writeJsonData(pageResult);
	}

	/**
	 * 删除错题
	 */
	public void deleteWrongQuestion() {
		try {
			paperService.deleteWrongQuestion(wid);
			JsonUtil.writeJsonData("删除成功");
		} catch (Exception e) {
			JsonUtil.writeJsonData("删除失败");
		}

	}

	/**
	 * 显示指定的错题
	 */
	public void showWrongQuestion() {
		try {
			Questions questions = paperService.findQuestionById(qid);
			JsonUtil.writeJsonData(questions);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
