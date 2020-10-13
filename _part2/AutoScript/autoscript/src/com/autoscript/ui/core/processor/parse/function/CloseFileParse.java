/**
 * 
 */
package com.autoscript.ui.core.processor.parse.function;

import java.util.List;

import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;

/**
 * 关闭文件解析器
 * 作者:龙色波
 * 日期:2013-10-14
 */
public class CloseFileParse implements IFunctionParse {

	/* (non-Javadoc)
	 * @see com.autoscript.ui.core.processor.parse.function.IFunctionParse#parseFunctionParmeter(java.lang.String)
	 */
	@Override
	public List<Object> parseFunctionParmeter(String textSegment)
			throws Exception {
		if(!StringHelper.isEmpty(textSegment)){
			String parameter;
			parameter = StringHelper.replaceAll(textSegment, "\t", "").toString();
			parameter = StringHelper.replaceAll(parameter, "\r", "").toString();
			parameter = StringHelper.replaceAll(parameter, "\n", "").toString();
			parameter = parameter.trim();
			if(!StringHelper.isEmpty(parameter)){
				throw new Exception(UIPropertyHelper.getString("exception.invalidateCloseFileParameterNum"));
			}
		}
		return null;
	}

}
