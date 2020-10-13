/**
 * 
 */
package com.autoscript.ui.core.processor.parse.function;

import java.util.List;

/**
 * 函数语法解析器接口
 * 作者:龙色波
 * 日期:2013-10-14
 */
public interface IFunctionParse {
	/**
	 * 解析方法参数
	 * @param textSegment 原始文本段
	 * @return 返回参数值列表  如果该方法没有参数，则为返回null
	 * @throws Exception
	 */
	public List<Object> parseFunctionParmeter(String textSegment) throws Exception;
}
