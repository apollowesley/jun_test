package org.apache.center.web.controller.systemmanage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.playframework.web.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.kisso.annotation.Permission;


import org.apache.center.model.Role;
import org.apache.center.service.RoleService;
import org.apache.center.service.UserRoleService;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.config.annotation.Reference;
	
import org.apache.playframework.domain.EasyuiJsonResult;
import org.apache.playframework.mybatisplus.mapper.EntityWrapper;


/**
 * @author willenfoo
 */
@Controller
@RequestMapping("role/")
public class RoleController extends BaseController {
	
	private static String VIEWS_PATH = "systemmanage/role/";
	
	@Reference(registry="centerRegistry")
	private RoleService roleService; //服务层

	@Reference(registry="centerRegistry")
	private UserRoleService userRoleService; //服务层
	
    /**
	 * 跳转到查询页面
	 * @return
	 */
	@RequestMapping(value="toFind")
	@Permission("role")
	public String toFind() {
		return VIEWS_PATH+"find";
	}
	
	/**
	 *  查询数据
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "find")
	@ResponseBody
	@Permission("role_find")
	public Object find(Role role) {
		EntityWrapper<Role> wrapper = new EntityWrapper<Role>(role);
		String nameLike = getParameter("nameLike");
		if (StringUtils.isNotBlank(nameLike)) {
			wrapper.like("name", nameLike);
		}
		Page<Role> page = getEasyuiPage();
		return EasyuiJsonResult.getSuccessResult(roleService.selectPage(page , wrapper));
	}

    /**
     * 跳转到新增页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toAdd")
	@Permission("role_add")
	public String toAdd(ModelMap modelMap) {
	    modelMap.put("role", new Role());
		return VIEWS_PATH+"edit";
	}
	
	/**
     * 跳转到更新页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toUpdate")
	@Permission("role_update")
	public String toUpdate(@RequestParam Long id,ModelMap modelMap) {
	    Role role = roleService.selectById(id);
	    modelMap.put("role", role);
		return VIEWS_PATH+"edit";
	}
	
	/**
	 * 添加
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "add")
	@ResponseBody
	@Permission("role_add")
	public Object add(Role role) {
		return getResult(roleService.insert(role));
	}
	
	/**
	 * 更新
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	@Permission("role_update")
	public Object update(Role role) {
		return getResult(roleService.updateById(role));
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	@Permission("role_delete")
	public Object delete(@RequestParam("id") Long id) {
	    Role role = new Role();
	    role.setId(id);
	    role.setIsDelete("Y");
		return getResult(roleService.updateById(role));
	} 
	 
	/**
	 * 跳转到 分配角色  页面
	 * @return
	 */
	@RequestMapping(value = "toAssignRole")
	@Permission("user_assign_role")
	public String toAssignRole() {
		return VIEWS_PATH + "assignRole";
	}
	
	/**
	 * 根据用户id查询该用户的角色
	 * @param userId
	 * @return
	 */
	@Permission("user_assign_role")
	@RequestMapping(value = "findByUserId")
	@ResponseBody
	public Object findByUserId(@RequestParam("userId") Long userId) {
		return EasyuiJsonResult.getSuccessResult(roleService.selectByUserId(userId));
	}
	
	/**
	 * 根据角色id跟用户id删除  关连关系
	 * @param roleId
	 * @param userId
	 * @return
	 */
	@Permission("user_assign_role")
	@RequestMapping(value = "deleteUserRole")
	@ResponseBody
	public Object deleteUserRole(@RequestParam("roleId") Long roleId,
			@RequestParam("userId") Long userId) {
		return getResult(userRoleService.deleteUserRole(userId, roleId));
	}
	
	/**
	 * 根据角色id跟用户id 添加 关连关系
	 * @param roleId
	 * @param userId
	 * @return
	 */
	@Permission("user_assign_role")
	@RequestMapping(value = "addUserRole")
	@ResponseBody
	public Object addUserRole(@RequestParam("roleId") Long roleId,
			@RequestParam("userId") Long userId) {
		return getResult(userRoleService.addUserRole(userId, roleId));
	}
	
	
}