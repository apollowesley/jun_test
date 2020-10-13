/**
 * 
 */
package com.autoscript.ui.model.projecttree;

import java.util.List;

import com.autoscript.ui.helper.UIPropertyHelper;

/**
 * 树根节点模型
 * 作者:龙色波
 * 日期:2013-10-9
 */
public class TreeRootModel implements ITreeModel {
	private XmlDataModel xmlDataModel;
	private List<TemplateModel> templateModels;
	@Override
	public int getType() {
		return ITreeModel.ROOT_TYPE;
	}
	public String toString(){
		return UIPropertyHelper.getString("tree.root.label");
	}
	public XmlDataModel getXmlDataModel() {
		return xmlDataModel;
	}
	public void setXmlDataModel(XmlDataModel xmlDataModel) {
		this.xmlDataModel = xmlDataModel;
	}
	public List<TemplateModel> getTemplateModels() {
		return templateModels;
	}
	public void setTemplateModels(List<TemplateModel> templateModels) {
		this.templateModels = templateModels;
	}
	public boolean isExistTemplate(String templateName) {
		if(templateModels!=null){
			for(TemplateModel model:templateModels){
				if(model.getTemplateName().equalsIgnoreCase(templateName)){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
	@Override
	public void verify() throws Exception {
		if(xmlDataModel==null){
			throw new Exception(UIPropertyHelper.getString("exception.xmldatanodeisempty"));
		}
		xmlDataModel.verify();
		//必须存在启用的模板
		if(templateModels==null||templateModels.size()==0){
			throw new Exception(UIPropertyHelper.getString("exception.template_is_empty"));
		}
		int i=0;
		for(TemplateModel template:templateModels){
			if(template.isEnable()){
				i = i+1;
				template.verify();
			}
		}
		if(i==0){
			throw new Exception(UIPropertyHelper.getString("exception.at_last_one_template_is_enable"));
		}
	}
	
}
