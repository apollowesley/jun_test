/**
 * 
 */
package com.autoscript.ui.core.processor.parse.function;

import java.util.ArrayList;
import java.util.List;

import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;

/**
 * 创建文本的函数解析器
 * 作者:龙色波
 * 日期:2013-10-14
 */
public class CreateTextFileParse implements IFunctionParse {

	/* (non-Javadoc)
	 * @see com.autoscript.ui.core.processor.parse.function.IFunctionParse#parseFunctionParmeter(java.lang.String)
	 */
	@Override
	public List<Object> parseFunctionParmeter(String textSegment)
			throws Exception {
		String parameters[];
		parameters = StringHelper.split(textSegment, ',');
		
		//判断参数个数是否正确,格式为:createTextFile(“c:\\1.txt",true)
		
		if(parameters.length!=2){
			throw new Exception(UIPropertyHelper.getString("exception.invalidateCreateFileParameterNum", parameters.length));
		}
		//每个参数截断前后空格
		for(int i=0;i<parameters.length;i++){
			parameters[i]=parameters[i].trim();
			//前三个字符串，必须以"开始和结束
			if(i<parameters.length-1){
				if(!parameters[i].startsWith("\"")||!parameters[i].endsWith("\"")){
					throw new Exception(UIPropertyHelper.getString("exception.invalidateParameterFmtMissQuotationMark", parameters[i]));
				}
			}
		}
		//最后参数必须为布尔型
		if(!parameters[1].equalsIgnoreCase("true")&& !parameters[1].equalsIgnoreCase("false")){
			throw new Exception(UIPropertyHelper.getString("exception.invalidateParameterType", parameters[1],"Boolean"));
		}
		List<Object> retParameters = new ArrayList<Object>();
		for(int i=0;i<parameters.length;i++){
			if(i<parameters.length-1){
				//去掉引号
				retParameters.add(parameters[i].replaceAll("\"", ""));
			}else{
				retParameters.add(Boolean.valueOf(parameters[i]));
			}
		}
		return retParameters;
	}
}
