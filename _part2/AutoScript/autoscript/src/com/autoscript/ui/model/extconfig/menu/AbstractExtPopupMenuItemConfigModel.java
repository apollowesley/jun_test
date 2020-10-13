/**
 * 
 */
package com.autoscript.ui.model.extconfig.menu;

import org.apache.log4j.Logger;

import com.autoscript.ui.helper.ClassHelper;
import com.autoscript.ui.helper.FileCtrlUtils;
import com.autoscript.ui.helper.StringHelper;
import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.logger.UILogger;

/**
 * 抽象右键菜单项扩展模型
 * 作者:龙色波
 * 日期:2013-10-16
 */
public abstract class AbstractExtPopupMenuItemConfigModel implements IExtPopupMenuItemConfigModel {
	protected Logger logger = UILogger.getLogger(AbstractExtPopupMenuItemConfigModel.class);
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单描述
	 */
	private String describe;
	/**
	 * 快捷键
	 */
	private char acceleratorKey;
	/**
	 * 图标文件名
	 */
	private String  iconFile;
	/**
	 * 必须实现接口类名
	 */
	private String interfaceClassName;
	/**
	 * 操作类名
	 */
	private String implClassName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public char getAcceleratorKey() {
		return acceleratorKey;
	}
	public void setAcceleratorKey(char acceleratorKey) {
		this.acceleratorKey = acceleratorKey;
	}
	public String getIconFile() {
		return iconFile;
	}
	public void setIconFile(String iconFile) {
		this.iconFile = iconFile;
	}
	public String getInterfaceClassName() {
		return interfaceClassName;
	}
	public void setInterfaceClassName(String interfaceClassName) {
		this.interfaceClassName = interfaceClassName;
	}
	public String getImplClassName() {
		return implClassName;
	}
	public void setImplClassName(String implClassName) {
		this.implClassName = implClassName;
	}
	public void verify() throws Exception{
		//菜单名不能为空
		if(StringHelper.isEmpty(name)){
			throw new Exception(UIPropertyHelper.getString("exception.menuname_is_empty"));
		}
		//图标文件必须存在
		if(!StringHelper.isEmpty(iconFile) && !FileCtrlUtils.isExists(iconFile)){
			throw new Exception(UIPropertyHelper.getString("exception.file_not_exist", iconFile));
		}
		//接口类必须存在
		if(StringHelper.isEmpty(interfaceClassName)){
			throw new Exception(UIPropertyHelper.getString("exception.interfaceClassName_is_empty"));
		}
		try{
			Class.forName(interfaceClassName, false, this.getClass().getClassLoader());
		}catch(LinkageError e){
			logger.error(e.getMessage(), e);
			throw new Exception(UIPropertyHelper.getString("exception.loadClassFail",interfaceClassName, e.getMessage()));
		}catch(ClassNotFoundException  e1){
			logger.error(e1.getMessage(), e1);
			throw new Exception(UIPropertyHelper.getString("exception.loadClassFail",interfaceClassName, e1.getMessage()));
		}
		//操作类必须存在
		if(StringHelper.isEmpty(implClassName)){
			throw new Exception(UIPropertyHelper.getString("exception.operationClassName_is_empty"));
		}
		Class<?> implClass=null;
		try{
			implClass = Class.forName(implClassName);
		}catch(LinkageError e){
			logger.error(e.getMessage(), e);
			throw new Exception(UIPropertyHelper.getString("exception.loadClassFail",implClassName, e.getMessage()));
		}catch(ClassNotFoundException  e1){
			logger.error(e1.getMessage(), e1);
			throw new Exception(UIPropertyHelper.getString("exception.loadClassFail",implClassName, e1.getMessage()));
		}
		//操作类必须实现接口类
		if(implClass!=null){
			if(!ClassHelper.isImplInterface(implClass, interfaceClassName)){
				throw new Exception(UIPropertyHelper.getString("exception.implClassMustImplInterface", implClassName,interfaceClassName));
			}
		}
	}
}
