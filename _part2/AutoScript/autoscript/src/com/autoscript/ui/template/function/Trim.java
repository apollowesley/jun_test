/**
 * 
 */
package com.autoscript.ui.template.function;

import java.util.List;

import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 截断前后空格及回车
 * 作者:龙色波
 * 日期:2014-5-18
 */
public class Trim implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	/**
	 * 有一个参数:被处理的字符串	
	 */
	@Override
	public Object exec(List args) throws TemplateModelException {
		if(args.size()!=1){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","Trim",args.size(),1));
		}
		String val = (String)args.get(0);
		if(val!=null){
			val = StringHelper.replaceAll(val, "\r", "").toString();
			val = StringHelper.replaceAll(val, "\n", "").toString();
			return val.trim();
		}
		return null;
	}

}
