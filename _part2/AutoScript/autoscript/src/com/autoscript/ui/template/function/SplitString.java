/**
 * 
 */
package com.autoscript.ui.template.function;

import java.util.ArrayList;
import java.util.List;

import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 字符串拆分函数
 * 作者:龙色波
 * 日期:2014-5-17
 */
public class SplitString implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@Override
	public Object exec(List args) throws TemplateModelException {
		//格式: SplitString(字符串,分隔串)
		if(args.size()!=2 ){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","SplitString",args.size(),"2"));
		}
		String str;
		String splitstr;
		str = (String) args.get(0);
		splitstr = (String)args.get(1);
	
		String splitArray[] = StringHelper.splitString(str, splitstr);
		List<String> retList = new ArrayList<String>();
		if(splitArray!=null){
			for(String val:splitArray){
				retList.add(val);
			}
		}
		return retList;
	}

}
