/**
 * 
 */
package com.autoscript.ui.template.function;

import java.util.List;

import com.autoscript.ui.helper.UIPropertyHelper;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 判断字符串以什么字符结束 忽略大小写
 * 作者:龙色波
 * 日期:2013-11-3
 */
public class EndsWithIgnoreCase implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@Override
	public Object exec(List args) throws TemplateModelException {
		if(args.size()!=2){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","EndsWithIgnoreCase",args.size(),2));
		}
		String val1 = ((String)args.get(0)).toLowerCase();
		String val2 = ((String)args.get(1)).toLowerCase();
		return val1.endsWith(val2);
	}

}
