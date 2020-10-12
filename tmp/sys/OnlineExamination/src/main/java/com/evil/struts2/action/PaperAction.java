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

	private List<Paper> paperList; // �����Ծ��б�

	private List<Questions> questionList; // ������Ŀ�б�

	private String pid;// ����Ծ��id
	private String qid;// ��������id
	
	private Questions question;//���ﵥ������

	private String jsonResult;// ����json���ݵķ���
	private String paperName;// ���ڽ����Ծ������
	private String ids;// ����Ծ��id�� (����ɾ��)
	private String iscloesd;// ����û�ѡ���״̬
	private int questionsType;// ���������
	private int score;//ÿ������ķ�ֵ
	private String paperPage;//���Ʒ��ص�ҳ��


	@Resource(name = "PaperService")
	private PaperService paperservice;

	/**
	 * ����Ծ����������
	 */
	public String findQuestionsByPaperId() {
		Paper paper = paperservice.getPaperWithChildren(pid, false);
		this.model = paper;
		questionList = new ArrayList<Questions>(model.getQuestions());
		System.out.println(paperPage);
		return paperPage.trim();
	}


	/**
	 * ����������ѯ��Ӧ���Ծ�
	 */
	public String findConditionPaper() {
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		iscloesd = ValidateUtil.isNull(iscloesd) ? null : iscloesd;
		map.put("title", "%" + model.getTitle() + "%");
		map.put("cloesd", iscloesd);
		map.put("formalTest",false);  //������ʽ����
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
	 * ɾ��ָ�����Ծ�
	 */
	public void deletePaPer() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.deletePaPer(pid);
			rm.setNavTabId("paperTab");
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("ɾ��ʧ��");
		}finally{
			JsonUtil.returnMsg(rm);
		}
	}

	/**
	 * ����ɾ������Ծ�
	 */
	public void bacthDeletePaper() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.batchDeletePaper(ids);
			rm.setForwardUrl("PaperAction_toAllPapersPage");
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("ɾ��ʧ��" + e.getMessage());
		}
		JsonUtil.returnMsg(rm);
	}

	/**
	 * ����/�ر��Ծ�
	 */
	public void toggleStatus() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.toggleStatus(pid);
			rm.setCallbackType("");
			rm.setNavTabId("paperTab");
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		JsonUtil.returnMsg(rm);
	}

	public String toAlerPaperPage() {
		this.model = paperservice.findById(pid);
		return "toalerPaperPage";
	}
	/**
	 * ������ֶ��Ծ�ҳ��
	 */
	public String newPaper(){
		return "toalerPaperPage";
	}

	/**
	 * �����Ծ�
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
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setNavTabId("paperTab");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * ������Ծ�ҳ��
	 */
	public String toDesignPaperPage() {
		findPaperQuestionsByType();
		return "designPaperPage";
	}

	/**
	 * ��ʾ��ͬ�Ծ�Ĳ�ͬ�͵�����
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
	 * ����Ծ�ָ�����͵�����
	 */
	public void clearQuestion() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.clearQuestion(pid, questionsType);
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setRel("questionBox");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * ɾ����������
	 */
	public void deleteQuestion() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.deleteQuestion(pid,qid);
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("ɾ��ʧ��" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setRel("questionBox");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * ɾ���������
	 */
	public void batchDeleteQuestions() {
		ReturnMsg rm = new ReturnMsg();
		try {
			paperservice.batchDeleteQuestion(pid,StringUtil.strSplit(ids, ","));
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("ɾ��ʧ��" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setRel("questionBox");
		JsonUtil.returnMsg(rm);
	}
	
	/**
	 * ���༭�Ծ�Ĵ����ҳ��
	 */
	public String toEditPaperHeadingPage(){
		this.model=paperservice.getPaperWithChildren(pid, true);
		return "editPaperHeadingPage";
	}

	/**
	 *����༭���� 
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
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setRel("questionBox");
		JsonUtil.returnMsg(rm);
	}
	/**
	 *������Ծ�����action
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
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setRel("questionBox");
		JsonUtil.returnMsg(rm);
	}
	//get/set����....
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
