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
	// ע��PaperService
	private PaperService paperService;

	private String wid;// �����id
	private String qid;// �����id
	private int questionType; //���������

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
	 * ��ʾ����ҳ��
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
	 *������������Ͳ�ѯ�����б�
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
	 * ɾ������
	 */
	public void deleteWrongQuestion() {
		try {
			paperService.deleteWrongQuestion(wid);
			JsonUtil.writeJsonData("ɾ���ɹ�");
		} catch (Exception e) {
			JsonUtil.writeJsonData("ɾ��ʧ��");
		}

	}

	/**
	 * ��ʾָ���Ĵ���
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
