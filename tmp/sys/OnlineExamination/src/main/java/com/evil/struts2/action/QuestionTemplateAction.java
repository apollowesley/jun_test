package com.evil.struts2.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.assist.QuestionTemplate;
import com.evil.service.QuestionTemplateService;
import com.evil.util.JsonUtil;
import com.evil.util.ReturnMsg;

@Component("QuestionTemplateAction")
@Scope("prototype")
public class QuestionTemplateAction extends BaseAction<QuestionTemplate> {
	private static final long serialVersionUID = 4916251175582966524L;

	@Resource(name = "questionTemplateService")
	private QuestionTemplateService questionTemplateService;

	private List<QuestionTemplate> questionTemplates;
	private String pid;// 父节点的id
	private String isTypeNum="false"; //是否查询试题类型数量

	/**
	 * 到试题模板页面
	 */
	public String toQuestionTemplatePage() {
		return "questionTemplatePage";
	}

	/**
	 * 将模板数据写入json中
	 */
	public void showQuestionTemplate() {
		Map<String, Object> map=new HashMap<String, Object>();
		boolean isType = false;
		if("true".equals(isTypeNum)){
			isType=true;
			map.put("all", questionTemplateService.findAllQuestionTypeNum());
		}
		System.out.println(isTypeNum);
		questionTemplates = questionTemplateService.findAllTemplates(isType);
		map.put("questionTemplates", questionTemplates);
		JsonUtil.writeJsonData(map);
	}
	/**
	 * 将指定的问题模板写入json
	 */
	
	public void findQuestionTypeByTemplateId(){
		long nums[]=questionTemplateService.findQuestionTypeByTemplateId(model.getId());
		JsonUtil.writeJsonData(nums);
	}

	/**
	 * 将该模板的所有孩子写入json
	 */
	public void displayTemplate() {
		List<String> tids = questionTemplateService
				.findTemplateChildrensByid(model.getId());
		JsonUtil.writeJsonData(tids);
	}

	/**
	 * 到添加试题模板
	 */
	public String toAddQuestionTemplatePage() {
		return "editQuestionTemplatePage";
	}

	/**
	 * 到修改试题模板页面
	 */
	public String toUpdateQuestionTemplatePage() {
		model = questionTemplateService.getEntity(model.getId());
		// System.out.println(model.getParent().getId()+"...");
		return "editQuestionTemplatePage";
	}

	/**
	 * 保存/更新模板
	 */
	public void doSaveOrUpdateTemplate() {
		ReturnMsg rm = new ReturnMsg();
		try {
			questionTemplateService.saveOrUpdateTemplate(model,pid);
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setNavTabId("tmeplateTab");
		JsonUtil.returnMsg(rm);
	}
	
	public void doDeleteaQuestionTemplate(){
		ReturnMsg rm = new ReturnMsg();
		try {
			questionTemplateService.deleteQuestionTemplate(model.getId());
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("删除失败" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setNavTabId("tmeplateTab");
		JsonUtil.returnMsg(rm);
	}

	// get/set....
	public List<QuestionTemplate> getQuestionTemplates() {
		return questionTemplates;
	}

	public void setQuestionTemplates(List<QuestionTemplate> questionTemplates) {
		this.questionTemplates = questionTemplates;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getIsTypeNum() {
		return isTypeNum;
	}

	public void setIsTypeNum(String isTypeNum) {
		this.isTypeNum = isTypeNum;
	}
	

}
