package com.evil.struts2.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.evil.pojo.Exam;
import com.evil.pojo.ExamType;
import com.evil.pojo.Paper;
import com.evil.service.ExamService;
import com.evil.service.ExamTypeService;
import com.evil.service.PaperService;
import com.evil.util.JsonUtil;
import com.evil.util.Page;
import com.evil.util.ReturnMsg;
import com.evil.util.ValidateUtil;

@Controller("ExamAction")
@Scope("prototype")
public class ExamAction extends BaseAction<Exam> {
	private static final long serialVersionUID = -6027911005323781184L;
	@Resource
	private ExamService examService;
	@Resource
	private ExamTypeService examTypeService;
	@Resource
	private PaperService paperService;

	private List<ExamType> examTypes; // 考试类型的集合
	private List<Paper> papers;  // 试卷的集合
	private String examTypeId; // 考试类型的id
	private String paperId;
	
	/**
	 * 到考试管理页面
	 * @return
	 */
	public String toExamManagePage() {
		examTypes = examTypeService.findAllEntities();
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		if (model != null) {
			if (model.getExamName() != null) {
				map.put("examName", "%" + model.getExamName() + "%");
			}
		}
		if (!ValidateUtil.isNull(examTypeId)) {
			map.put("examtypeId", examTypeId);
		}
		String sortfield =null;
		if (!ValidateUtil.isNull(orderField)
				&& !ValidateUtil.isNull(orderDirection)) {
			 sortfield = orderField + " " + orderDirection;
		}
		pageResult = examService.findExamPage(page, map, sortfield," id desc");
		return "examManagePage";
	}
	/**
	 * 到修改考试界面
	 * @return
	 */
	public String toUpdateExamPage(){
		examTypes = examTypeService.findAllEntities();
		papers=paperService.findAllPaper();
		model=examService.getEntity(model.getId());
		return "updateExamPage";
	}
	/**
	 * 到添加考试界面
	 * @return
	 */
	public String toAddExamPage(){
		examTypes = examTypeService.findAllEntities();
		papers=paperService.findAllPaper();
		return "AddExamPage";
	}
	
	public void doSaveOrUpdateExam(){
		ReturnMsg rm = new ReturnMsg();
		try {
			ExamType examType=new ExamType();
			examType.setId(examTypeId);
			Paper paper=new Paper();
			paper.setId(paperId);
			model.setExamType(examType);
			model.setPaper(paper);
			examService.saveOrUpdateEntity(model);
			rm.setCallbackType("closeCurrent");
			rm.setNavTabId("examTab");
		} catch (Exception e) {
			rm=ReturnMsg.ERRORMsg("操作失败 原因:"+e.getMessage());
			rm.setCallbackType("");
		} finally {
			JsonUtil.returnMsg(rm);
		}
		
	}
	
	public void deleteExam(){
		ReturnMsg rm = new ReturnMsg();
		try {
			examService.deleteExam(model);
			rm.setNavTabId("examTab");
		} catch (Exception e) {
			rm=ReturnMsg.ERRORMsg("操作失败 原因:"+e.getMessage());
		} finally {
			rm.setCallbackType("");
			JsonUtil.returnMsg(rm);
		}
	}

	// get/set方法.....
	public List<ExamType> getExamTypes() {
		return examTypes;
	}

	public void setExamTypes(List<ExamType> examTypes) {
		this.examTypes = examTypes;
	}

	public String getExamTypeId() {
		return examTypeId;
	}
	public void setExamTypeId(String examTypeId) {
		this.examTypeId = examTypeId;
	}
	public List<Paper> getPapers() {
		return papers;
	}
	public void setPapers(List<Paper> papers) {
		this.papers = papers;
	}
	public String getPaperId() {
		return paperId;
	}
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	

}
