package com.evil.struts2.action;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.Paper;
import com.evil.service.PaperService;
import com.evil.service.QuestionBankService;
import com.evil.struts2.form.TemplateForm;
import com.evil.util.JsonUtil;
import com.evil.util.ReturnMsg;

@Component("IntelligentPaperAction")
@Scope("prototype")
public class IntelligentPaperAction extends BaseAction<Paper>{
	private static final long serialVersionUID = 4916251175582966524L;

	
	@Resource(name = "PaperService")
	private PaperService paperservice;
	@Resource(name="QuestionBankService")
	private QuestionBankService questionService;
	
	private int singleScore;
	private int multipleScore;
	private int judgeScore;
	private ArrayList<TemplateForm> templateForms=new ArrayList<TemplateForm>();//����ģ�������б�
	

	/**
	 *����������Ծ�ҳ�� 
	 */
	public String toIntelligentAddPage(){
		return "intelligentAddPage";
	}
	/**
	 * ��������Ծ�
	 */
	public void doIntelligentAddPaper(){
		model.setItemScore(singleScore+","+multipleScore+","+judgeScore);
		model.setIsDelete("1");
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.savIntelligentPaper(model,TemplateForm.ListDeduplication(templateForms));
			paperservice.alterPaperDetailMess(model.getId());
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("�Ծ����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setNavTabId("paperTab");
		JsonUtil.returnMsg(rm);
	}

	/**
	 *ȥ��������Ծ�����ҳ�� 
	 */
	public String toIntelligentAddPaperQuestionPage(){
		return "intelligentAddPaperQuestionPage";
	}
	/**
	 *������������Ծ����� 
	 */
	public void  doIntelligenceAddPaperQuestion(){
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.intelligenceAddPaperQuestion(model.getId(),TemplateForm.ListDeduplication(templateForms));;
			paperservice.alterPaperDetailMess(model.getId());
		} catch (Exception e) {
			e.printStackTrace();
			rm = ReturnMsg.ERRORMsg("�Ծ����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setRel("questionBox");
		JsonUtil.returnMsg(rm);
	}
	
	
	//get/set����.....
	public int getSingleScore() {
		return singleScore;
	}
	public void setSingleScore(int singleScore) {
		this.singleScore = singleScore;
	}
	public int getMultipleScore() {
		return multipleScore;
	}
	public void setMultipleScore(int multipleScore) {
		this.multipleScore = multipleScore;
	}
	public int getJudgeScore() {
		return judgeScore;
	}
	public void setJudgeScore(int judgeScore) {
		this.judgeScore = judgeScore;
	}
	public ArrayList<TemplateForm> getTemplateForms() {
		return templateForms;
	}
	public void setTemplateForms(ArrayList<TemplateForm> templateForms) {
		this.templateForms = templateForms;
	}
	
	
}
