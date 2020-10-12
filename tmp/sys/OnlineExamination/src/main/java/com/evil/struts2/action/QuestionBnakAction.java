package com.evil.struts2.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.Questions;
import com.evil.pojo.assist.QuestionTemplate;
import com.evil.service.PaperService;
import com.evil.service.QuestionBankService;
import com.evil.struts2.form.TemplateForm;
import com.evil.util.JsonUtil;
import com.evil.util.Page;
import com.evil.util.ReturnMsg;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Component("QuestionBnakAction")
@Scope("prototype")
public class QuestionBnakAction extends BaseAction<Questions> {
	private static final long serialVersionUID = 4598773023112618217L;

	@Resource(name = "QuestionBankService")
	private QuestionBankService questionBankService;
	@Resource(name = "PaperService")
	private PaperService paperService;

	private String qid;// ��ǰ�����id
	private String answers[]; // ����Ĵ�
	private String questionPage;// ��ת��ҳ��
	private String tid;// ģ���id
	private TemplateForm templateForm;//ģ��ʵ��

	/**
	 * ��ת��ʾ�����ҳ��
	 */
	public String toQuestionsContentPage() {
		return "questionsContentPage";
	}

	/**
	 * ���ݵ���������ͽ��з�ҳ������
	 */
	public String doTopic() {
		Page page = new Page();
		page.setCurrentPage(pageNum);
		page.setNumPerPage(numPerPage);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", "1");
		String sortfield = orderField + " " + orderDirection;
		map.put("title",
				"%" + (model.getTitle() == null ? "" : model.getTitle()) + "%");
		map.put("questionsType", model.getQuestionsType());
		map.put("tid", tid);
		if(templateForm!=null&&!ValidateUtil.isNull(templateForm.getId())){
			map.put("tid", templateForm.getId());
		}
		pageResult = questionBankService.findPageQuestions(page, map,
				sortfield, " id desc");
		return questionPage;
	}

	/**
	 * ��ת���޸�/�������ҳ��
	 */
	public String toEditQuestionPage() {
		if (!ValidateUtil.isNull(qid)) {
			model = questionBankService.getEntity(qid);
		}
		return questionPage;
	}

	/**
	 * �������/�޸�����
	 */
	public void doEditQuestion() {
		ReturnMsg rm = new ReturnMsg();
		try {
			model.setIsDelete("1");
			model.setAnswer(StringUtil.arr2Str(answers));
			model.setQuestionTemplate(new QuestionTemplate(tid));
			questionBankService.saveOrUpdateEntity(model);
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		} finally {
			rm.setCallbackType("closeCurrent");
			rm.setRel("jbsxBox");
			JsonUtil.returnMsg(rm);
		}

	}

	/**
	 * ����ɾ������
	 */
	public void bacthDeleteQuestions() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if (!ValidateUtil.isNull(ids)) {
				String qids[] = StringUtil.strSplit(ids, ",");
				String pids[] = questionBankService
						.findPaperIdByQuestionsIds(qids);// ���������Ӱ����Ծ�id
				questionBankService.batchDeleteQuestion(qids); // ɾ������
				if (!ValidateUtil.isNull(pids)) {
					for (String pid : pids) {
						paperService.alterPaperDetailMess(pid); // �޸����е���Ӱ����Ծ����Ϣ
					}
				}
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setRel("jbsxBox");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * ����ʾ����ͳ��ҳ��
	 */
	public String toQuestionStatisticsPage() {
		return "questionStatisticsPage";
	}

	/**
	 * ��������������
	 */
	public String toQuestionIndexPage() {
		pageResult = null;
		return "questionIndexPage";
	}
	/**
	 *���ƶ�����ҳ�� 
	 */
	public String toMoveQuestionPage() {
		return "moveQuestionPage";
	}


	/**
	 * ���������ƶ�
	 */
	public void doMoveQuestions() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if (!ValidateUtil.isNull(ids)&&!ValidateUtil.isNull(model.getId())) {
				questionBankService.moveQuestions(StringUtil.strSplit(ids, ","),model.getId());

			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setRel("jbsxBox");
		JsonUtil.returnMsg(rm);
	}
	
	public void clearQuestionBnakAndTemplate(){
		ReturnMsg rm = new ReturnMsg();
		try {
			questionBankService.clearQuestionBnakAndTemplate();
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		JsonUtil.returnMsg(rm);
		
	}

	// get/set����....
	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public String getQuestionPage() {
		return questionPage;
	}

	public void setQuestionPage(String questionPage) {
		this.questionPage = questionPage;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public TemplateForm getTemplateForm() {
		return templateForm;
	}

	public void setTemplateForm(TemplateForm templateForm) {
		this.templateForm = templateForm;
	}
	
	

}
