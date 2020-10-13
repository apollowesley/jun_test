/**
 * 
 */
package com.autoscript.ui.model.extconfig.menu;


/**
 * xml数据右键菜单项扩展模型
 * 作者:龙色波
 * 日期:2013-10-16
 */
public class XmlDataPopupMenuItemConfigModel extends
		AbstractExtPopupMenuItemConfigModel {

	public XmlDataPopupMenuItemConfigModel(){
		setInterfaceClassName("com.autoscript.extinterface.xmldata.IMakeXmlData");
	}

}
