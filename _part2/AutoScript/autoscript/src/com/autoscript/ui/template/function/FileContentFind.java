/**
 * 
 */
package com.autoscript.ui.template.function;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.autoscript.ui.helper.FileCtrlUtils;
import com.autoscript.ui.helper.UIPropertyHelper;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 文件内存查找函数
 * 作者:龙色波
 * 日期:2014-5-17
 */
public class FileContentFind implements TemplateMethodModel {

	/* (non-Javadoc)
	 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
	 */
	@Override
	/**
	 * 有两个参数:第一个参数为文件名
     *           第二个参数为被检索的字符串	
	 */
	public Object exec(List args) throws TemplateModelException {
		if(args.size()!=2){
			throw new TemplateModelException(UIPropertyHelper.getString("exception.func_para_number_not_match","FileContentFind",args.size(),2));
		}
		String fileName = (String)args.get(0);
		String searchKey = (String)args.get(1);
		File file = new File(fileName);
		try {
			return FileCtrlUtils.contentRule(file,searchKey);
		} catch (IOException e) {
			throw new TemplateModelException(e.getMessage());
		}
	}
}
