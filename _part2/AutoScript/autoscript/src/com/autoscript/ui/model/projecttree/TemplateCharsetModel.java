/**
 * 
 */
package com.autoscript.ui.model.projecttree;

import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;

/**
 * 模板字符集模型
 * 作者:龙色波
 * 日期:2013-10-9
 */
public class TemplateCharsetModel implements ITreeModel {
	/**
	 * 字符集
	 */
	private String charset;
	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.projecttree.ITreeModel#getType()
	 */
	@Override
	public int getType() {
		return TEMPLATE_CHARSET_TYPE;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String toString(){
		return UIPropertyHelper.getString("tree.template.characterset.lable");
	}
	@Override
	public void verify() throws Exception {
		//字符集不能为空
		if(StringHelper.isEmpty(charset)){
			throw new Exception(UIPropertyHelper.getString("exception.charset_is_empty"));
		}
	}
}
