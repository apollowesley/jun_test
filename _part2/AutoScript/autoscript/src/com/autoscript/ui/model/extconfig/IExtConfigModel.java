/**
 * 
 */
package com.autoscript.ui.model.extconfig;

/**
 * 扩展配置模型
 * 作者:龙色波
 * 日期:2013-10-16
 */
public interface IExtConfigModel {
	/**
	 * 模型展示名称
	 * @return
	 */
	public  String toString();
	/**
	 * 校验配置
	 * @throws Exception
	 */
	public void verify() throws Exception;
}
