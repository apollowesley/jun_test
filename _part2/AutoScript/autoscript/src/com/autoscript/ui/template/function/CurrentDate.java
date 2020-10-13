/**
 * 
 */
package com.autoscript.ui.template.function;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.autoscript.ui.helper.UIPropertyHelper;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 当前日期
 * 作者:龙色波
 * 日期:2013-10-25
 */
public class CurrentDate implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 * 就一个参数：日期格式
	 */
	@Override
	public Object exec(List args) throws TemplateModelException {
		if(args.size()!=1){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","CurrentDate",args.size(),1));
		}
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formate  = new SimpleDateFormat((String)args.get(0));
		return formate.format(c.getTime());
	}

}
