/**
 * 
 */
package com.autoscript.ui.template.function;

import java.util.List;

import com.autoscript.ui.helper.UIPropertyHelper;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 判断字符串以什么字符结束
 * 作者:龙色波
 * 日期:2013-10-27
 */
public class EndsWith implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@Override
	public Object exec(List args) throws TemplateModelException {
		if(args.size()!=2){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","EndsWith",args.size(),2));
		}
		return ((String)args.get(0)).endsWith((String)args.get(1));
	}

}
