package org.openkoala.businesslog.component;

import java.text.MessageFormat;

/**
 * 日志描述解析器
 * @author xmfang
 *
 */
public class LogDescriptionParser {

	//TODO 日志描述解析，需要改写为日志描述设计一个语法和对应的解析器。或者使用freemarker等现有模版的语法及解析器。
	public String parseDescription(String descriptionTemplate, Object[] argValues) {
		return MessageFormat.format(descriptionTemplate, argValues);
	}
	
}
