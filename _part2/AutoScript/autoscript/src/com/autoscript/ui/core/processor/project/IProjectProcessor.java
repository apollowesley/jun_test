/**
 * 
 */
package com.autoscript.ui.core.processor.project;

import com.autoscript.ui.model.projecttree.TreeRootModel;

/**
 * 工程加工器
 * 作者:龙色波
 * 日期:2013-10-13
 */
public interface IProjectProcessor {
	/**
	 * 加工工程模型
	 * @param projectModel
	 * @throws Exception
	 */
	public void build(TreeRootModel projectModel) throws Exception;
}
