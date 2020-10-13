/**
 * 
 */
package com.autoscript.ui.template.function;

import java.util.List;

import com.autoscript.ui.helper.UIPropertyHelper;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 替换字符串
 * 作者:龙色波
 * 日期:2013-10-25
 */
public class Replace implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@Override
	public Object exec(List args) throws TemplateModelException {
		if(args.size()!=3){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","Replace",args.size(),3));
		}
		String val;
		val = (String) args.get(0);
		return val.replaceAll((String)args.get(1), (String)args.get(2));
	}

}
