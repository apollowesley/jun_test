/**
 * 
 */
package com.autoscript.ui.core.processor.parse.file;

import com.autoscript.ui.model.projecttree.TemplateModel;

/**
 * 中间文件解析器接口
 * 作者:龙色波
 * 日期:2013-10-15
 */
public interface IFileParse {
	/**
	 * 解析并执行文件中指令
	 * @param fileName  中间结果文件名
	 * @param  templateModel 模板模型 
	 * @throws Exception
	 */
	public void parse(String fileName,TemplateModel templateModel) throws Exception;
}
