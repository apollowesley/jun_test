/**
 * 
 */
package com.autoscript.ui.model.extconfig.function;

/**
 * FreeMark扩展函数配置接口
 * 作者:龙色波
 * 日期:2013-10-24
 */
public interface IFunctionConfigModel {
	/**
	 * 设置函数名称 在FreeMark模板中使用的函数名
	 * @param funName
	 */
	public void setName(String funName);
	/**
	 * 获取函数名称
	 * @return
	 */
	public String getName();
	/**
	 * 设置TemplateMethodModel的实现类 
	 * @param className
	 */
	public void setFunctionClass(String className);
	/**
	 * 获取TemplateMethodModel的实现类
	 * @return
	 */
	public String getFunctionClass();
	/**
	 * 设置描述  通常为方法使用说明
	 * @param describe
	 */
	public void setDescribe(String describe);
	/**
	 * 获取描述
	 * @return
	 */
	public String getDescribe();
	/**
	 * 校验
	 * @throws Exception 
	 */
	public void verify() throws Exception;
}
