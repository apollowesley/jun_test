package com.evil.struts2.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.evil.pojo.ExamType;
import com.evil.service.ExamTypeService;
import com.evil.util.JsonUtil;
import com.evil.util.Page;
import com.evil.util.ReturnMsg;

@Controller("ExamTypeAction")
@Scope("prototype")
public class ExamTypeAction extends BaseAction<ExamType> {
	private static final long serialVersionUID = -6027911005323781184L;
	@Resource
	private ExamTypeService examTypeService;
	
	/**
	 * 去考试类型管理界面
	 * @return
	 */
	public String toExamTypeManagePage(){
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		pageResult = examTypeService.findEntityByPage(page, "");
		return "examTypeManagePage";
	}
	
	/**
	 * 到添加考试类型页面
	 * @return
	 */
	public String toAddExamTypePage(){
		return "addExamTypePage";
	}
	/**
	 * 到修改考试类型页面
	 * @return
	 */
	public String toEditExamTypePage(){
		model=examTypeService.getEntity(model.getId());
		return "editExamTypePage";
	}
	/**
	 * 保存/修改考试类别
	 */
	public void doSaveOrUpdateExamType(){
		ReturnMsg rm = new ReturnMsg();
		try {
			examTypeService.saveOrUpdateEntity(model);
			rm.setCallbackType("closeCurrent");
			rm.setNavTabId("examTypeTab");
		} catch (Exception e) {
			rm.setCallbackType("");
		} finally {
			JsonUtil.returnMsg(rm);
		}
	}
	/**
	 * 删除试卷类型
	 */
	public  void deleteExamType(){
		ReturnMsg rm = new ReturnMsg();
		try {
			examTypeService.deleteExamType(model);
			rm.setNavTabId("examTypeTab");
		} catch (Exception e) {
			rm.setCallbackType("");
			rm.setMessage("删除类型失败 错误原因:"+e.getMessage());
		} finally {
			rm.setCallbackType("");
			JsonUtil.returnMsg(rm);
		}
		
	}
	//get/set方法.....

}
