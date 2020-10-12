package com.evil.struts2.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.evil.pojo.Paper;
import com.evil.pojo.Questions;
import com.evil.service.PaperService;
import com.evil.util.JsonUtil;
import com.evil.util.Page;
import com.evil.util.ReturnMsg;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Component("PaperAction")
@Scope("prototype")
public class PaperAction extends BaseAction<Paper>{
	private static final long serialVersionUID = 4916251175582966524L;

	private List<Paper> paperList; // 保存试卷列表

	private List<Questions> questionList; // 保存题目列表

	private String pid;// 获得试卷的id
	private String qid;// 获得问题的id
	
	private Questions question;//处里单个问题

	private String jsonResult;// 用于json数据的返回
	private String paperName;// 用于接受试卷的名称
	private String ids;// 多个试卷的id集 (用于删除)
	private String iscloesd;// 获得用户选择的状态
	private int questionsType;// 问题的类型
	private int score;//每个问题的分值
	private String paperPage;//控制返回的页面


	@Resource(name = "PaperService")
	private PaperService paperservice;

	/**
	 * 获得试卷的所有问题
	 */
	public String findQuestionsByPaperId() {
		Paper paper = paperservice.getPaperWithChildren(pid, false);
		this.model = paper;
		questionList = new ArrayList<Questions>(model.getQuestions());
		System.out.println(paperPage);
		return paperPage.trim();
	}


	/**
	 * 根据条件查询相应的试卷
	 */
	public String findConditionPaper() {
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		iscloesd = ValidateUtil.isNull(iscloesd) ? null : iscloesd;
		map.put("title", "%" + model.getTitle() + "%");
		map.put("cloesd", iscloesd);
		map.put("formalTest",false);  //不是正式考试
		if(model.getPaperType()!=null){
			map.put("paperType", model.getPaperType());
		}
		String sortfield= orderField + " " + orderDirection;
		pageResult = paperservice.findPagePaper(page, map,sortfield," id desc");
		return "showPapersPage";
	}

	public void findPaperByNameJson() {
		List<Paper> list = paperservice.findPapersByName(paperName);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("papers", list);
		JsonUtil.writeJsonData(dataMap);
		this.jsonResult = JSONArray.toJSONString(dataMap);
	}

	/**
	 * 删除指定的试卷
	 */
	public void deletePaPer() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.deletePaPer(pid);
			rm.setNavTabId("paperTab");
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("删除失败");
		}finally{
			JsonUtil.returnMsg(rm);
		}
	}

	/**
	 * 批量删除多个试卷
	 */
	public void bacthDeletePaper() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.batchDeletePaper(ids);
			rm.setForwardUrl("PaperAction_toAllPapersPage");
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("删除失败" + e.getMessage());
		}
		JsonUtil.returnMsg(rm);
	}

	/**
	 * 开启/关闭试卷
	 */
	public void toggleStatus() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.toggleStatus(pid);
			rm.setCallbackType("");
			rm.setNavTabId("paperTab");
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		JsonUtil.returnMsg(rm);
	}

	public String toAlerPaperPage() {
		this.model = paperservice.findById(pid);
		return "toalerPaperPage";
	}
	/**
	 * 到添加手动试卷页面
	 */
	public String newPaper(){
		return "toalerPaperPage";
	}

	/**
	 * 更新试卷
	 */
	public void doUapdatePaper() {
		ReturnMsg rm = new ReturnMsg();
		Paper paper = null;
		if(model.getId()!=null){
			paper=paperservice.findById(model.getId());
		}else{
			paper=new Paper();
		}
		paper.setTitle(model.getTitle());
		paper.setExanTime(model.getExanTime());
		paper.setCloesd(model.isCloesd());
		paper.setPaperType(model.getPaperType());
		paper.setStartTime(model.getStartTime());
		paper.setEndTime(model.getEndTime());
		paper.setExamParsing(model.isExamParsing());
		try {
			paperservice.saveOrUpdatePaper(paper);

		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setNavTabId("paperTab");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * 到设计试卷页面
	 */
	public String toDesignPaperPage() {
		findPaperQuestionsByType();
		return "designPaperPage";
	}

	/**
	 * 显示不同试卷的不同型的问题
	 */
	public String doPaperQuestionsByQuesType() {
		findPaperQuestionsByType();
		return "QuestionsTypePage";
	}
	
	private void findPaperQuestionsByType() {
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		this.model = paperservice.getPaperWithChildren(pid, false);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p.id", model.getId());
		map.put("q.questionsType", questionsType);
		pageResult = paperservice.findPaperQuestionsByType(page, map);
	}

	/**
	 * 清空试卷指定类型的问题
	 */
	public void clearQuestion() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.clearQuestion(pid, questionsType);
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setRel("questionBox");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * 删除单个问题
	 */
	public void deleteQuestion() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.deleteQuestion(pid,qid);
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("删除失败" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setRel("questionBox");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * 删除多个问题
	 */
	public void batchDeleteQuestions() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.batchDeleteQuestion(pid,StringUtil.strSplit(ids, ","));
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("删除失败" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setRel("questionBox");
		JsonUtil.returnMsg(rm);
	}
	
	/**
	 * 到编辑试卷的大标题页面
	 */
	public String toEditPaperHeadingPage(){
		this.model=paperservice.getPaperWithChildren(pid, true);
		return "editPaperHeadingPage";
	}

	/**
	 *处理编辑标题 
	 */
	public void doEditPaperHeading(){
		ReturnMsg rm = new ReturnMsg();
		try {
			this.model=paperservice.getPaperWithChildren(pid, true);
			int itemScore[]=model.getItemScoreArr();
			itemScore[questionsType]=score;
			model.setItemScore(StringUtil.arr2Str(itemScore));
			paperservice.saveOrUpdatePaper(model);
			paperservice.alterPaperDetailMess(pid);
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setRel("questionBox");
		JsonUtil.returnMsg(rm);
	}
	/**
	 *到添加试卷试题action
	 */
	public String toAddQuestionPage(){
		return "addQuestionPage";
	}
	
	public void doAddPaperQuestion(){
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.addPaperQuestion(pid,StringUtil.strSplit(ids, ","));
			paperservice.alterPaperDetailMess(pid);
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setRel("questionBox");
		JsonUtil.returnMsg(rm);
	}
	//get/set方法....
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Questions getQuestion() {
		return question;
	}

	public void setQuestion(Questions question) {
		this.question = question;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public int getQuestionsType() {
		return questionsType;
	}

	public void setQuestionsType(int questionsType) {
		this.questionsType = questionsType;
	}
	public List<Paper> getPaperList() {
		return paperList;
	}
	public void setPaperList(List<Paper> paperList) {
		this.paperList = paperList;
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

	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getIscloesd() {
		return iscloesd;
	}

	public void setIscloesd(String iscloesd) {
		this.iscloesd = iscloesd;
	}
	public String getPaperPage() {
		return paperPage;
	}

	public void setPaperPage(String paperPage) {
		this.paperPage = paperPage;
	}
	
}
