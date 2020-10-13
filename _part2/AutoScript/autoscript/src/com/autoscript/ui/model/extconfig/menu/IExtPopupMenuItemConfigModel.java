/**
 * 
 */
package com.autoscript.ui.model.extconfig.menu;


/**
 * 扩展弹出式菜单项配置模型接口
 * 作者:龙色波
 * 日期:2013-10-16
 */
public interface IExtPopupMenuItemConfigModel {
	/**
	 * 设置菜单名称
	 * @param name
	 */
	public void setName(String name);
	/**
	 * 获取菜单名称
	 * @return 菜单名称
	 */
	public String getName();
	/**
	 * 设置菜单描述
	 * @param describe
	 */
	public void setDescribe(String describe);
	/**
	 * 获取菜单描述
	 * @return 菜单描述 
	 */
	public String getDescribe();
	/**
	 * 设置快捷键
	 * @param acceleratorKey
	 */
	public void setAcceleratorKey(char acceleratorKey);
	/**
	 * 获取快捷键
	 * @return 快捷键
	 */
	public char getAcceleratorKey();
	/**
	 * 设置菜单图标文件名
	 * @param fileName 菜单图标文件名
	 */
	public void setIconFile(String fileName) throws Exception;
	/**
	 * 获取菜单图标文件名
	 * @return 菜单图标文件名
	 */
	public String getIconFile();
	/**
	 * 设置菜单操作类必须实现的接口类名
	 * @param className 接口完整类名
	 * @throws Exception
	 */
	public void setInterfaceClassName(String  className) throws Exception;
	/**
	 * 获取菜单操作类必须实现的接口类名
	 * @return 菜单操作类必须实现的接口类名
	 */
	public String getInterfaceClassName();
	/**
	 * 设置菜单操作类操作时类名
	 * @param className 菜单操作类操作时类名
	 * @throws Exception
	 */
	public void setImplClassName(String className) throws Exception;
	/**
	 * 获取菜单操作类操作时类名
	 * @return 菜单操作类操作时类名
	 */
	public String getImplClassName();
}
