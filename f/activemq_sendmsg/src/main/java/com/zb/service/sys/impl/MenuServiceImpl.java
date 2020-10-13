package com.zb.service.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.bean.sys.SysMenu;
import com.zb.mapper.sys.MenuMapper;
import com.zb.service.BaseServiceImpl;
import com.zb.service.sys.MenuService;

import tk.mybatis.mapper.entity.Example;

@Service("menuServiceImpl")
public class MenuServiceImpl extends BaseServiceImpl<SysMenu> implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<SysMenu> getAllParentList() {
		Example example = new Example(SysMenu.class);
		example.createCriteria().andEqualTo("parentId", 0);
		return menuMapper.selectByExample(example);
	}

	@Override
	public List<SysMenu> getSubMenuByParentId(String id) {
		Example example = new Example(SysMenu.class);
		example.createCriteria().andEqualTo("parentId", id);
		return menuMapper.selectByExample(example);
	}
}
