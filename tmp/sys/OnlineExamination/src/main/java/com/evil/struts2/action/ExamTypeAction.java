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
	 * ȥ�������͹������
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
	 * ����ӿ�������ҳ��
	 * @return
	 */
	public String toAddExamTypePage(){
		return "addExamTypePage";
	}
	/**
	 * ���޸Ŀ�������ҳ��
	 * @return
	 */
	public String toEditExamTypePage(){
		model=examTypeService.getEntity(model.getId());
		return "editExamTypePage";
	}
	/**
	 * ����/�޸Ŀ������
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
	 * ɾ���Ծ�����
	 */
	public  void deleteExamType(){
		ReturnMsg rm = new ReturnMsg();
		try {
			examTypeService.deleteExamType(model);
			rm.setNavTabId("examTypeTab");
		} catch (Exception e) {
			rm.setCallbackType("");
			rm.setMessage("ɾ������ʧ�� ����ԭ��:"+e.getMessage());
		} finally {
			rm.setCallbackType("");
			JsonUtil.returnMsg(rm);
		}
		
	}
	//get/set����.....

}
