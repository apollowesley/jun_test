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
 * 单词首字符变大写
 * 作者:龙色波
 * 日期:2013-10-24
 */
public class WordFirstCharUpper implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 * 一共两个参数，顺序为：参数值，单词分隔符
	 */
	@Override
	public Object exec(List args) throws TemplateModelException {
		if(args.size()!=2){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","WordFirstCharUpper",args.size(),2));
		}
		String words = (String)args.get(0);
		String split = (String)args.get(1);
		if(words==null||split==null){
			return words;
		}
		String wordArray[];
		wordArray = StringHelper.splitString(words, split);
		StringBuilder buff = new StringBuilder();
		boolean endWithSplit;
		endWithSplit = words.endsWith(split);
		for(int i=0;i<wordArray.length;i++){
			if(i==wordArray.length-1){
				if(!endWithSplit){
					buff.append(StringHelper.toFirstUpper(wordArray[i]));
				}else{
					buff.append(StringHelper.toFirstUpper(wordArray[i])).append(split);
				}
			}else{
				buff.append(StringHelper.toFirstUpper(wordArray[i])).append(split);
			}
		}
		return buff.toString();
	}

}
