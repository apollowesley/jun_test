package com.zb.service.sys;


import java.util.List;

import com.zb.bean.sys.SysMenu;
import com.zb.service.BaseService;

public interface MenuService extends BaseService<SysMenu> {

	/**
	 * 获取所有父菜单
	 * @return
	 */
	List<SysMenu> getAllParentList();

	/**
	 * 获取父菜单下面的子菜单
	 * @param id
	 * @return
	 */
	List<SysMenu> getSubMenuByParentId(String id);

}
