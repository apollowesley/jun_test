package com.antdsp.web.rest.sys;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antdsp.web.dto.MenuTree;
import com.antdsp.common.AntdspResponse;
import com.antdsp.common.annotation.OperationLog;
import com.antdsp.dao.jpa.system.MenuJpa;
import com.antdsp.data.entity.User;
import com.antdsp.data.entity.system.SystemMenu;
import com.antdsp.data.entityeenum.MenuType;

@RestController
@RequestMapping("/system/menu")
public class SystemMenuApi {
	
	@Autowired
	private MenuJpa menuJpa;
	
	@GetMapping("")
	@OperationLog(name="查询菜单列表")
	@RequiresPermissions(value= {"menu:list"})
	public MenuTree list() {
		
		MenuTree rootMenu = new MenuTree();
		rootMenu.setId(0L);
		rootMenu.setName("antdsp");
		rootMenu.setPath("/");
		
		List<SystemMenu> allMenus = this.menuJpa.findAll();
		this.child(rootMenu, allMenus);
		return rootMenu;
	}
	
	@PostMapping("")
	@Transactional
	@OperationLog(name="添加菜单")
	@RequiresPermissions(value= {"menu:add"})
	public AntdspResponse add(@RequestBody MenuTree menu) {
		
		SystemMenu data = new SystemMenu();
		this.dtoToData(menu, data);
		data.setCreator("");
		data.setModifier("");
		data.onPreInsert();
		data.setId(null);
		this.menuJpa.save(data);
		return AntdspResponse.success();
	}
	
	@PutMapping("")
	@Transactional
	@OperationLog(name="修改菜单")
	@RequiresPermissions(value= {"menu:update"})
	public AntdspResponse update(@RequestBody MenuTree menu) {
		
		SystemMenu oldMenu = this.menuJpa.findById(menu.getId()).orElse(null);
		
		if(oldMenu == null) {
			return AntdspResponse.error("操作失败，未找到菜单信息");
		}
		
		this.dtoToData(menu, oldMenu);
		oldMenu.onPreUpdate();
		this.menuJpa.save(oldMenu);
		return AntdspResponse.success();
	}
	
	@DeleteMapping("/{id:\\d+}")
	@OperationLog(name="删除菜单")
	@RequiresPermissions(value= {"menu:delete"})
	public AntdspResponse delete(@PathVariable("id") Long id) {
		
		SystemMenu oldMenu = this.menuJpa.findById(id).orElse(null);
		if(oldMenu == null) {
			return AntdspResponse.error("未找到操作对象");
		}
		
		this.menuJpa.delete(oldMenu);
		return AntdspResponse.success();
		
	}
	/**
	 * 获取当前登录人的路由信息
	 * @return
	 */
	@GetMapping("/route")
	public MenuTree queryRouteMenu() {
		
		Subject subject = SecurityUtils.getSubject();
		User current = (User) subject.getPrincipal();
		
		List<SystemMenu> allMenuAntButtons = this.menuJpa.findPermissMenus(current.getId());
		List<SystemMenu> allMenus = allMenuAntButtons.stream().filter(item->MenuType.MENU.equals(item.getMenuType())).collect(Collectors.toList());
		
		MenuTree rootMenu = new MenuTree();
		rootMenu.setId(0L);
		rootMenu.setName("antdsp");
		rootMenu.setPath("/");
		this.child(rootMenu, allMenus);
		return rootMenu;
		
	}
	
	
	private void dtoToData(MenuTree menu , SystemMenu systemenu) {
		systemenu.setIcon(menu.getIcon());
		systemenu.setMenuName(menu.getName());
		systemenu.setMenuType(MenuType.valueOf(menu.getType()));
		systemenu.setParentId(menu.getParentId());
		systemenu.setPermission(menu.getPermission());
		systemenu.setUrl(menu.getPath());
		systemenu.setHideInMenu(menu.isHideInMenu());
	}
	
	private void child(MenuTree parentMenu , List<SystemMenu> allMenus) {
		List<SystemMenu> childMenus = allMenus.stream().filter(item->
			parentMenu.getId().equals(item.getParentId())).collect(Collectors.toList());
		allMenus.removeAll(childMenus);
		childMenus.forEach(item->{
			MenuTree menu = new MenuTree();
			menu.setId(item.getId());
			menu.setIcon(item.getIcon());
			menu.setName(item.getMenuName());
			menu.setParentId(item.getParentId());
			menu.setPath(item.getUrl());
			menu.setPermission(item.getPermission());
			menu.setType(item.getMenuType().name());
			menu.setHideInMenu(item.isHideInMenu());
			
			this.child(menu , allMenus);
			
			parentMenu.appendChildren(menu);
		});
		
	}
	
}
