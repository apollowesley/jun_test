/**
 * 
 */
package com.autoscript.ui.model.extconfig.kb;

/**
 * 知识库模型接口
 * 作者:龙色波
 * 日期:2013-11-12
 */
public interface IKBItemModel {
	/**
	 * 设置知识库类型
	 * @param type 类型
	 */
	public void setType(String type);
	/**
	 * 获取知识库类型
	 * @return
	 */
	public String getType();
	/**
	 * 设置关键字或函数
	 * @param keyFun
	 */
	public void setKeyFun(String keyFun);
	/**
	 * 获取关键字或函数
	 * @return
	 */
	public String getKeyFun();
	/**
	 * 设置语法
	 * @param syntax
	 */
	public void setSyntax(String syntax);
	/**
	 * 获取语法
	 * @return
	 */
	public String getSyntax();
	/**
	 * 设置描述
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
	 */
	public void verify() throws Exception;
}
