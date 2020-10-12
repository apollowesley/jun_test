package com.evil.struts2.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.evil.pojo.system.TypeDictionary;
import com.evil.service.TypeDictionaryService;
import com.evil.util.DictionaryUtil;
import com.evil.util.JsonUtil;
import com.evil.util.ReturnMsg;

@Controller("DictionaryAction")
@Scope("prototype")
public class DictionaryAction extends BaseAction<TypeDictionary> {
	private static final long serialVersionUID = -845370001102208846L;

	@Resource
	private TypeDictionaryService typeDictionaryService;
	
	/**
	 * 到试卷的分类信息页面
	 */
	public  String classifyTag(){
		 return "classifyTagPage";
	}
	/**
	 * 删除试卷的分类信息
	 */
	public void deleteClassify(){
		ReturnMsg rm = new ReturnMsg();
		try {
			typeDictionaryService.deleteClassifyByPid(model.getId());
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("删除失败");
		}
		DictionaryUtil.DictionaryWrite(typeDictionaryService.findDictionaryByType(TypeDictionary.PAPER_TYPE));
		rm.setRel("classifyTab");
		JsonUtil.returnMsg(rm);
	}
	
	/**
	 * 到添加试卷分类信息页面
	 * @return
	 */
	public String toAddClassifyPage(){
		return "editClassifyPage";
	}
	/**
	 * 保存试卷分类
	 */
	public void savaOrUpdateClassify(){
		ReturnMsg rm = new ReturnMsg();
		try {
			if(model.getId()==null){
				model.setType(TypeDictionary.PAPER_TYPE);
				model.setValue(typeDictionaryService.getMaxValueByType(TypeDictionary.PAPER_TYPE)+1);
			}
			typeDictionaryService.saveOrUpdateEntity(model);
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败");
		}
		DictionaryUtil.DictionaryWrite(typeDictionaryService.findDictionaryByType(TypeDictionary.PAPER_TYPE));
		rm.setCallbackType("closeCurrent");
		rm.setNavTabId("classifyTab");
		JsonUtil.returnMsg(rm);
		
	}
	
	public String toUpdateClassify(){
		model=typeDictionaryService.getEntity(model.getId());
		return "editClassifyPage";
	}
	
	
	
	

}
