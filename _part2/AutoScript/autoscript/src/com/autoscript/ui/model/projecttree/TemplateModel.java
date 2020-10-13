/**
 * 
 */
package com.autoscript.ui.model.projecttree;

import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;

/**
 * 模板模型
 * 作者:龙色波
 * 日期:2013-10-9
 */
public class TemplateModel implements ITreeModel {
	private TemplateContentModel contentModel;
	private TemplateCharsetModel charsetModel;
	private String templateName;
	/**
	 * 是否启用
	 */
	private boolean enable = true;
	public TemplateModel(){
		
	}
	public TemplateModel(String templateName){
		this.templateName = templateName;
		contentModel  = new TemplateContentModel();
		charsetModel = new TemplateCharsetModel();
	}
	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.projecttree.ITreeModel#getType()
	 */
	@Override
	public int getType() {
		return TEMPLATE_TYPE;
	}
	public TemplateContentModel getContentModel() {
		return contentModel;
	}
	public void setContentModel(TemplateContentModel contentModel) {
		this.contentModel = contentModel;
	}
	public TemplateCharsetModel getCharsetModel() {
		return charsetModel;
	}
	public void setCharsetModel(TemplateCharsetModel charsetModel) {
		this.charsetModel = charsetModel;
	}
	public String toString(){
		return templateName;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	@Override
	public void verify() throws Exception {
		//校验文件模板名称
		if(StringHelper.isEmpty(templateName)){
			throw new Exception(UIPropertyHelper.getString("exception.template_nam_is_empty"));
		}
		if(contentModel==null){
			throw new Exception(UIPropertyHelper.getString("exception.templdate_content_model_is_empty"));
		}
		contentModel.verify();
		if(charsetModel == null){
			throw new Exception(UIPropertyHelper.getString("exception.charset_model_is_empty"));
		}
		charsetModel.verify();
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
}
