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

	private String qid;// 当前问题的id
	private String answers[]; // 问题的答案
	private String questionPage;// 跳转的页面
	private String tid;// 模板的id
	private TemplateForm templateForm;//模板实体

	/**
	 * 跳转显示问题的页面
	 */
	public String toQuestionsContentPage() {
		return "questionsContentPage";
	}

	/**
	 * 根据的问题的类型进行分页和排序
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
	 * 跳转到修改/添加问题页面
	 */
	public String toEditQuestionPage() {
		if (!ValidateUtil.isNull(qid)) {
			model = questionBankService.getEntity(qid);
		}
		return questionPage;
	}

	/**
	 * 处理添加/修改问题
	 */
	public void doEditQuestion() {
		ReturnMsg rm = new ReturnMsg();
		try {
			model.setIsDelete("1");
			model.setAnswer(StringUtil.arr2Str(answers));
			model.setQuestionTemplate(new QuestionTemplate(tid));
			questionBankService.saveOrUpdateEntity(model);
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		} finally {
			rm.setCallbackType("closeCurrent");
			rm.setRel("jbsxBox");
			JsonUtil.returnMsg(rm);
		}

	}

	/**
	 * 批量删除问题
	 */
	public void bacthDeleteQuestions() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if (!ValidateUtil.isNull(ids)) {
				String qids[] = StringUtil.strSplit(ids, ",");
				String pids[] = questionBankService
						.findPaperIdByQuestionsIds(qids);// 获得所有受影响的试卷id
				questionBankService.batchDeleteQuestion(qids); // 删除问题
				if (!ValidateUtil.isNull(pids)) {
					for (String pid : pids) {
						paperService.alterPaperDetailMess(pid); // 修改所有的受影响的试卷的信息
					}
				}
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setRel("jbsxBox");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * 到显示试题统计页面
	 */
	public String toQuestionStatisticsPage() {
		return "questionStatisticsPage";
	}

	/**
	 * 到试题索引界面
	 */
	public String toQuestionIndexPage() {
		pageResult = null;
		return "questionIndexPage";
	}
	/**
	 *到移动试题页面 
	 */
	public String toMoveQuestionPage() {
		return "moveQuestionPage";
	}


	/**
	 * 处理试题移动
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
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
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
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		JsonUtil.returnMsg(rm);
		
	}

	// get/set方法....
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
