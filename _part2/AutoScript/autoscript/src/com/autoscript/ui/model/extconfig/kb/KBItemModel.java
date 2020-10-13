/**
 * 
 */
package com.autoscript.ui.model.extconfig.kb;

import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;

/**
 * 知识库模型实现
 * 作者:龙色波
 * 日期:2013-11-12
 */
public class KBItemModel implements IKBItemModel {
	private String describe;
	private String keyFun;
	private String syntax;
	private String type;

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.kb.IKBModel#getDescribe()
	 */
	@Override
	public String getDescribe() {
		return describe;
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.kb.IKBModel#getKeyFun()
	 */
	@Override
	public String getKeyFun() {
		// TODO Auto-generated method stub
		return keyFun;
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.kb.IKBModel#getSyntax()
	 */
	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return syntax;
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.kb.IKBModel#getType()
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.kb.IKBModel#setDescribe(java.lang.String)
	 */
	@Override
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.kb.IKBModel#setKeyFun(java.lang.String)
	 */
	@Override
	public void setKeyFun(String keyFun) {
		this.keyFun = keyFun;

	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.kb.IKBModel#setSyntax(java.lang.String)
	 */
	@Override
	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.kb.IKBModel#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		this.type = type;

	}

	@Override
	public void verify() throws Exception {
		if(StringHelper.isEmpty(type)){
			throw new Exception(UIPropertyHelper.getString("exception.KBType_is_empty"));
		}
		if(StringHelper.isEmpty(keyFun)){
			throw new Exception(UIPropertyHelper.getString("exception.KBKeyFun_is_empty"));
		}
		if(StringHelper.isEmpty(syntax)){
			throw new Exception(UIPropertyHelper.getString("exception.KBSyntax_is_empty"));
		}
		if(StringHelper.isEmpty(describe)){
			throw new Exception(UIPropertyHelper.getString("exception.KBdescribe_is_empty"));
		}
	}
	public String toString(){
		return keyFun;
	}
}
