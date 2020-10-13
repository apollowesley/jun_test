/**
 * 
 */
package com.autoscript.ui.model.extconfig;

import java.util.ArrayList;
import java.util.List;

import com.autoscript.ui.helper.UIPropertyHelper;
import com.autoscript.ui.model.extconfig.menu.XmlDataPopupMenuItemConfigModel;

/**
 * Xml源数据弹出菜单模型
 * 作者:龙色波
 * 日期:2013-10-16
 */
public class XmlDataPopupMenuConfigModel implements IExtConfigModel {
	/**
	 * 菜单项配置列表
	 */
	List<XmlDataPopupMenuItemConfigModel> menuItems = new ArrayList<XmlDataPopupMenuItemConfigModel>();
	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.extconfig.IExtConfigModel#getModelName()
	 */
	@Override
	public  String toString() {
		return UIPropertyHelper.getString("dialog.config.xmldatapopupmenuext");
	}

	/* (non-Javadoc)
	 * @see com.autoscript.ui.model.extconfig.IExtConfigModel#verify()
	 */
	@Override
	public void verify() throws Exception {
		if(menuItems!=null){
			for(XmlDataPopupMenuItemConfigModel menuItem:menuItems){
				menuItem.verify();
			}
		}
	}

	public List<XmlDataPopupMenuItemConfigModel> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<XmlDataPopupMenuItemConfigModel> menuItems) {
		this.menuItems = menuItems;
	}
	/**
	 * 将菜单项列表转换为两级数组
	 * @return
	 */
	public String[][] toArray() {
		String data[][] = new String[menuItems.size()][5];
		XmlDataPopupMenuItemConfigModel model;
		for(int i=0;i<menuItems.size();i++){
			model = menuItems.get(i);
			data[i][0] = model.getName();
			data[i][1] = model.getDescribe();
			data[i][2] = String.valueOf(model.getAcceleratorKey());
			data[i][3] = model.getIconFile();
			data[i][4] = model.getImplClassName();
		}
		return data;
	}

}
