package com.evil.struts2.frontendAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.evil.pojo.Answer;
import com.evil.pojo.Questions;
import com.evil.pojo.User;
import com.evil.pojo.UserPaper;
import com.evil.service.GradeService;
import com.evil.struts2.UserAware;
import com.evil.struts2.action.BaseAction;
import com.evil.util.Page;

@Controller("FrontendGradeAction")
@Scope("prototype")
public class FrontendGradeAction extends BaseAction<UserPaper> implements UserAware {
	private static final long serialVersionUID = 6289366840932643703L;

	//ע��ɼ�service
	@Resource
	private GradeService gradeService;
	private String gid;// �ɼ���id

	private int paperType; // �Ծ������

	private User user;// ע��user

	private List<Questions> questionList; // ������Ŀ�б�
	private Map<String, Answer> answerMap;// �𰸵�map��ʽ<����id���� >


	// ����user
	@Override
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * ����ѯ�ɼ�ҳ��
	 */
	public String toQueryGradePage() {
		Page page = new Page();
		page.setNumPerPage(6);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", "1");
		map.put("userId", user.getId());
		map.put("paper.paperType", paperType);
		map.put("formalTest",false); 
		pageResult = gradeService.findPageGrade(page, map);
		return "queryGragePage";
	}

	/**
	 * ���ɼ�����ҳ��
	 */
	public String gradeDetailsPage() {
		model = gradeService.findGradeById(gid);
		questionList = new ArrayList<Questions>(model.getPaper().getQuestions());
		List<Answer> answers = gradeService.findGradeAnswer(model);
		answerMap = new HashMap<String, Answer>();
		for (Answer a : answers) {
			answerMap.put(a.getQuestionId(), a);
		}
		return "gradeDetailsPage";
	}
	
	public String toFormalTestGradePage(){
		model=gradeService.findFormalTestGrade(user);
		if(model!=null){
			questionList = new ArrayList<Questions>(model.getPaper().getQuestions());
			List<Answer> answers = gradeService.findGradeAnswer(model);
			answerMap = new HashMap<String, Answer>();
			for (Answer a : answers) {
				answerMap.put(a.getQuestionId(), a);
			}
		}
		return "gradeDetailsPage";
	}
	
    //get/set..����
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

	public int getPaperType() {
		return paperType;
	}

	public void setPaperType(int paperType) {
		this.paperType = paperType;
	}
}
