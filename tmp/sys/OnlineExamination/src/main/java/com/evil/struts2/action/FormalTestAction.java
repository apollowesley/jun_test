package com.evil.struts2.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.Paper;
import com.evil.service.PaperService;
import com.evil.util.JsonUtil;
import com.evil.util.Page;
import com.evil.util.ReturnMsg;

@Component("FormalTestAction")
@Scope("prototype")
public class FormalTestAction extends BaseAction<Paper> {
	private static final long serialVersionUID = 4916251175582966524L;


	@Resource(name = "PaperService")
	private PaperService paperservice;

	/**
	 * ��ת���μӲμӿ���ҳ��
	 */
	public String toFormalTestManangePage() {
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("formalTest",true);  //��ʽ����
		pageResult = paperservice.findPagePaper(page, map," id desc");
		return "formalTestManangePage";
	}
	/**
	 * ���޸���ʽ���Խ���
	 * @return
	 */
	public String toUpdateFormalTestPage(){
		this.model = paperservice.findById(model.getId());
		return "editFormalTestPage";
		
	}
	
	/**
	 * ������ʽ����
	 */
	public void doUpdateFormalTest(){
		ReturnMsg rm = new ReturnMsg();
		Paper paper = null;
		if(model.getId()!=null){
			paper=paperservice.findById(model.getId());
		}else{
			paper=new Paper();
		}
		paper.setTitle(model.getTitle());
		paper.setExanTime(model.getExanTime());
		paper.setStartTime(model.getStartTime());
		paper.setEndTime(model.getEndTime());
		paper.setFormalTest(true);
		try {
			paperservice.saveOrUpdatePaper(paper);

		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setNavTabId("formalTestTab");
		JsonUtil.returnMsg(rm);
		
	}


	//get/set����....
	
}
