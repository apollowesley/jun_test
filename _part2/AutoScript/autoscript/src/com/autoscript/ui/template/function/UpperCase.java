/**
 * 
 */
package com.autoscript.ui.template.function;

import java.util.List;

import com.autoscript.ui.helper.UIPropertyHelper;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 字符串转换为大写
 * 作者:龙色波
 * 日期:2013-10-25
 */
public class UpperCase implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@Override
	public Object exec(List args) throws TemplateModelException {
		if(args.size()!=1){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","UpperCase",args.size(),1));
		}
		return ((String)args.get(0)).toUpperCase();
	}

}
