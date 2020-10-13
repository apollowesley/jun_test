/**
 * 
 */
package com.autoscript.ui.model.projecttree;

/**
 * 数模型接口
 * 作者:龙色波
 * 日期:2013-10-9
 */
public interface ITreeModel {
	//模型类型
	/**
	 * 根节点
	 */
	public static final int ROOT_TYPE=0;
	/**
	 * XML源数据
	 */
	public static final int XML_DATA_TYPE=1;
	/**
	 * 模板
	 */
	public static final int TEMPLATE_TYPE=2;
	/**
	 * 模板内容
	 */
	public static final int TEMPLATE_CONTENT_TYPE=3;
	/**
	 * 模板字符集
	 */
	public static final int TEMPLATE_CHARSET_TYPE=4;
	/**
	 * 获取节点类型
	 * @return
	 */
	public int getType();
	/**
	 * 模型对应节点名称
	 * @return
	 */
	public String toString();
	/**
	 * 校验模型
	 * @throws Exception
	 */
	public void verify() throws Exception;
}
