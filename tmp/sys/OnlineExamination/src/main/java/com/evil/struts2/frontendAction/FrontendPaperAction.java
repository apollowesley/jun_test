package com.evil.struts2.frontendAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.Paper;
import com.evil.pojo.Questions;
import com.evil.pojo.User;
import com.evil.pojo.assist.PaperType;
import com.evil.service.PaperService;
import com.evil.struts2.UserAware;
import com.evil.struts2.action.BaseAction;
import com.evil.util.Page;

@Component("FrontendPaperAction")
@Scope("prototype")
public class FrontendPaperAction extends BaseAction<Paper> implements UserAware {
	private static final long serialVersionUID = 4916251175582966524L;

	private List<Questions> questionList; // ������Ŀ�б�

	private String pid;// ����Ծ��id
	private String paperPage;//���Ʒ��ص�ҳ��
	private List<PaperType> typeList;// �Ծ����ͼ���

	@Resource(name = "PaperService")
	private PaperService paperservice;
	// ����user����
	private User user;

	// ע��user
	@Override
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * ����Ծ����������
	 */
	public String getQuestions() {
		Paper paper = paperservice.getPaperWithChildren(pid, false);
		this.model = paper;
		questionList = new ArrayList<Questions>(model.getQuestions());
		System.out.println(paperPage);
		return paperPage.trim();
	}

	/**
	 * ��ת���μӲμӿ���ҳ��
	 */
	public String toPaperPage() {
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cloesd", 1);
		map.put("formalTest",false);  //����ʽ����
		if (model.getPaperType()!=null){
			map.put("paperType", model.getPaperType());
		}
		pageResult = paperservice.findPagePaper(page, map," id desc");
		return "paperPage";
	}



	/**
	 * ��ʾ��������
	 */
	public String showPaperType() {
		typeList = paperservice.findPaperTypers(user);
		return "showPageTypePage";
	}
	//get/set����....
	public List<PaperType> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<PaperType> typeList) {
		this.typeList = typeList;
	}
	public List<Questions> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<Questions> questionList) {
		this.questionList = questionList;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPaperPage() {
		return paperPage;
	}
	public void setPaperPage(String paperPage) {
		this.paperPage = paperPage;
	}
	
}
