/**
 * 
 */
package com.autoscript.ui.model.projecttree;

import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;

/**
 * 模板内容模型
 * 作者:龙色波
 * 日期:2013-10-9
 */
public class TemplateContentModel implements ITreeModel {
	/**
	 * 模板内容
	 */
	private String content;
	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.projecttree.ITreeModel#getType()
	 */
	@Override
	public int getType() {
		return TEMPLATE_CONTENT_TYPE;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String toString(){
		return UIPropertyHelper.getString("tree.template.content.label");
	}
	@Override
	public void verify() throws Exception {
		if(StringHelper.isEmpty(content)){
			throw new Exception(UIPropertyHelper.getString("exception.template_content_is_empty"));
		}
	}
}
