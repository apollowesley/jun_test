package com.evil.struts2.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.evil.pojo.Answer;
import com.evil.pojo.Questions;
import com.evil.pojo.UserPaper;
import com.evil.service.GradeService;
import com.evil.util.JsonUtil;
import com.evil.util.Page;
import com.evil.util.ReturnMsg;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Controller("GradeAction")
@Scope("prototype")
public class GradeAction extends BaseAction<UserPaper> {
	private static final long serialVersionUID = 6289366840932643703L;

	//注入成绩service
	@Resource
	private GradeService gradeService;

	// 用于条件检索
	private String paperTitle;// 试卷名称
	private String userName;// 考生姓名
	private String date;// 完成 时间
	private String gid;// 成绩的id
	
	private boolean formalTest;

	private List<Questions> questionList; // 保存题目列表
	private Map<String, Answer> answerMap;// 答案的map格式<问题id，答案 >

	/**
	 * 显示成绩
	 */
	public String toGradePage() {
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", "1");
		map.put("paperTitle", paperTitle);
		map.put("userName", userName);
		map.put("finishTime", date);
		map.put("paper.formalTest",formalTest); //所有非正式考试成绩
		String sortfield = orderField + " " + orderDirection;
		pageResult = gradeService.findPageGrade(page, map, sortfield,
				" id desc");
		return "gradePage";
	}
	/**
	 * 批量删除成绩
	 */
	public void batchDeleteGrade() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if (!ValidateUtil.isNull(ids)) {
				gradeService.batchDeleteGrade(StringUtil.strSplit(ids, ","));
				rm.setNavTabId("gradeTab");
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}finally{
			rm.setCallbackType("");
			JsonUtil.returnMsg(rm);
		}
	}

	/**
	 * 清空所有的成绩
	 */
	public void clearGrade() {
		ReturnMsg rm = new ReturnMsg();
		try {
			gradeService.clearGrade();
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setNavTabId("gradeTab");
		JsonUtil.returnMsg(rm);
	}

	/**
	 *到后台成绩详情页面 
	 */
    public	String toGradeDetailsPage(){
		model = gradeService.findGradeById(gid);
		questionList = new ArrayList<Questions>(model.getPaper().getQuestions());
		List<Answer> answers = gradeService.findGradeAnswer(model);
		answerMap = new HashMap<String, Answer>();
		for (Answer a : answers) {
			answerMap.put(a.getQuestionId(), a);
		}
		return "latarGradeDetailsPage";
	}
    
    //get/set..方法
	public List<Questions> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Questions> questionList) {
		this.questionList = questionList;
	}

	public Map<String, Answer> getAnswerMap() {
		return answerMap;
	}

	public void setAnswerMap(Map<String, Answer> answerMap) {
		this.answerMap = answerMap;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public String getPaperTitle() {
		return paperTitle;
	}

	public void setPaperTitle(String paperTitle) {
		this.paperTitle = paperTitle;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isFormalTest() {
		return formalTest;
	}
	public void setFormalTest(boolean formalTest) {
		this.formalTest = formalTest;
	}
	

}
