/**
 * 
 */
package com.autoscript.ui.template.function;

import java.util.List;

import com.autoscript.ui.helper.UIPropertyHelper;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 截断字符串
 * 作者:龙色波
 * 日期:2014-4-29
 */
public class SubString implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@Override
	public Object exec(List args) throws TemplateModelException {
		//格式: SubString(被截字符串,开始位置[,结束位置])
		if(args.size()!=2 && args.size()!=3){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","SubString",args.size(),"2 or 3"));
		}
		String str;
		Integer spos;
		Integer epos;
		str = (String) args.get(0);
		spos = Integer.valueOf((String)args.get(1));
		//捕捉越界异常
		try{
			if(args.size()>2){
				epos = Integer.valueOf((String)args.get(2));
				return str.substring(spos, epos);
			}else{
				return str.substring(spos);
			}
		}catch(IndexOutOfBoundsException  e){
			//发生越界时，返回空串
			return "";
		}
		
		
	}

}
