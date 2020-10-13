/**
 * 
 */
package com.autoscript.ui.template.function;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.autoscript.ui.helper.UIPropertyHelper;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;


/**
 * 日期格式化
 * 作者:龙色波
 * 日期:2013-10-25
 */
public class DateFormat implements TemplateMethodModel {

	@Override
	/**
	 * 有两个参数:第一个参数为日期，必须为java.util.Date
     *           第二个参数为格式化字符串	
	 */
	public Object exec(List args) throws TemplateModelException {
		if(args.size()!=2){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","DateFormat",args.size(),2));
		}
		//第一个必须为java.util.Date类型
		Date d;
		if(args.get(0) instanceof Date){
			d = (Date) args.get(0);
			SimpleDateFormat fmt = new SimpleDateFormat((String)args.get(1));
			return fmt.format(d);
		}else{
			throw new TemplateModelException(UIPropertyHelper.getString("exception.invalidateFuncParamType","DateFormat",1,Date.class.getName(),args.get(0).getClass().getName()));
		}
	}
	

}
