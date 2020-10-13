/**
 * 
 */
package com.autoscript.ui.template.function;

import java.util.List;

import com.autoscript.ui.helper.UIPropertyHelper;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 查找字符串
 * 作者:龙色波
 * 日期:2014-4-29
 */
public class Index implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@Override
	public Object exec(List args) throws TemplateModelException {
		//格式: Index(字符串,查找字符串)
		if(args.size()!=2 && args.size()!=3){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","Index",args.size(),"2 or 3"));
		}
		String str;
		String findStr;
		String startPos;
		if(args.size()==2){
			str = (String) args.get(0);
			findStr = (String) args.get(1);
			return str.indexOf(findStr);
		}else if(args.size()==3){
			str = (String) args.get(0);			
			startPos = (String)args.get(1);
			findStr = (String) args.get(2);
			return str.indexOf(findStr,Integer.valueOf(startPos));
		}else{
		    throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","Index",args.size(),2));
	    }
		
	}

}
