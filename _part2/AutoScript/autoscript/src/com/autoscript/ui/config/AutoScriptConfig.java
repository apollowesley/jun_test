package com.autoscript.ui.config;

import java.util.ArrayList;
import java.util.List;

import com.autoscript.ui.model.extconfig.IExtConfigModel;
import com.autoscript.ui.model.extconfig.KBConfigModel;
import com.autoscript.ui.model.extconfig.TemplateFunctionConfigModel;
import com.autoscript.ui.model.extconfig.XmlDataPopupMenuConfigModel;

/**
 * 配置类
 * 作者:龙色波
 * 日期:2013-10-13
 */
public class AutoScriptConfig {
	/**
	 * 工作目录
	 */
	private String workdir;
	/**
	 * 扩展功能配置模型
	 */
	private List<IExtConfigModel> extConfigModels = new ArrayList<IExtConfigModel>();
	public String getWorkdir() {
		return workdir;
	}

	public void setWorkdir(String workdir) {
		this.workdir = workdir;
	}

	public List<IExtConfigModel> getExtConfigModels() {
		return extConfigModels;
	}

	public void setExtConfigModels(List<IExtConfigModel> extConfigModels) {
		this.extConfigModels = extConfigModels;
	}
	/**
	 * 初始化扩展功能配置模型列表
	 */
	public void initExtConfigModel(){
		//xml源数据右键菜单
		XmlDataPopupMenuConfigModel xmlExtModel = new XmlDataPopupMenuConfigModel();
		if(!contains(extConfigModels,xmlExtModel.toString())){	
			extConfigModels.add(xmlExtModel);
		}
		//模板函数扩展
		TemplateFunctionConfigModel templateExtModel = new TemplateFunctionConfigModel();
		if(!contains(extConfigModels,templateExtModel.toString())){
		   extConfigModels.add(templateExtModel);
		}
		//知识库扩展
		KBConfigModel kbConfigModel = new KBConfigModel();
		if(!contains(extConfigModels,kbConfigModel.toString())){
		   extConfigModels.add(kbConfigModel);
		}
	}
	/**
	 * 判断是否包含扩展模型
	 * @param extConfigModels2
	 * @param modelName
	 * @return
	 */
	private boolean contains(List<IExtConfigModel> extConfigModels2,
			String modelName) {
		for(IExtConfigModel model:extConfigModels2){
			if(model.toString().equals(modelName)){
				return true;
			}
		}
		return false;
	}
}
