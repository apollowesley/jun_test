package com.evil.struts2.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.evil.pojo.system.AdminUser;
import com.evil.pojo.system.Role;
import com.evil.service.AdminUserService;
import com.evil.service.RoleService;
import com.evil.util.JsonUtil;
import com.evil.util.MD5Util;
import com.evil.util.Page;
import com.evil.util.ReturnMsg;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Controller("AdminUserAction")
@Scope("prototype")
public class AdminUserAction extends BaseAction<AdminUser> {
	private static final long serialVersionUID = -6488511564865010625L;
	@Resource
	private AdminUserService adminUserService;
	@Resource
	private RoleService roleService;

	private String repassword;// 确认密码字段
	private List<Role> noOwnRoles; // 用户不具备的角色

	// 接受用户角色id的集合
	private String[] ownRoleIds;

	/**
	 * 显示adminUser列表
	 * 
	 * @return
	 */
	public String toAdminUserManagePage() {
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "%" + (model.getName() == null ? "" : model.getName())
				+ "%");
		String sortfield = orderField + " " + orderDirection;
		pageResult = adminUserService.findAdminUserPage(page, map, sortfield,
				" id desc");
		return "adminUserManagePage";
	}

	/**
	 * 到添加管理员界面
	 * 
	 * @return
	 */
	public String toAddAdminUserPage() {
		return "addAdminUserPage";
	}

	/**
	 * 到修改管理员界面
	 * 
	 * @return
	 */
	public String toAlterAdminUserPage() {
		model = adminUserService.getEntity(model.getId());
		return "alterAdminUserPage";
	}

	/**
	 * 保存/更新管理员
	 */
	public void doSaveOrUpdateAdminUser() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if (ValidateUtil.isNull(model.getName())
					|| ValidateUtil.isNull(model.getPassword())) {
				throw new Exception("信息不能为空");
			} else {
				if (model.getId() == null) {
					if (!repassword.equals(model.getPassword())) {
						throw new Exception("两次的密码不能为空");
					}
					if (adminUserService.isNameOccupy(model.getName())) {
						throw new Exception("用户名被占用");
					}
					model.setPassword(MD5Util.encode(model.getPassword()));
				} else {
					if ("123456".equals(model.getPassword())) {
						model.setPassword(MD5Util.encode(model.getPassword()));
					}
				}
				adminUserService.saveOrUpdateEntity(model);
				rm.setCallbackType("closeCurrent");
				rm.setNavTabId("adminUserTab");
			}
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("更新管理员失败\n" + e.getMessage());
			rm.setCallbackType("");
		} finally {
			JsonUtil.returnMsg(rm);
		}

	}

	/**
	 * 批量删除管理员
	 */
	public void bacthDeleteAdminUsers() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if (!ValidateUtil.isNull(ids)) {
				adminUserService.batchDeleteAdminUsers(StringUtil.strSplit(ids,
						","));

			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setNavTabId("adminUserTab");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * 到分配角色页面
	 * 
	 * @return
	 */
	public String toAssignRolesPage() {
		model = adminUserService.getEntity(model.getId());
		noOwnRoles = roleService.findRolesNotInRange(model.getRoles());
		return "assignRolesPage";
	}

	/**
	 * 处理更新管理员授权
	 */
	public void doUpdateAmdinUserAuthorize() {
		ReturnMsg rm = new ReturnMsg();
		try {
			adminUserService.updateAmdinUserAuthorize(model,ownRoleIds);
			rm.setCallbackType("closeCurrent");
			rm.setNavTabId("adminUserTab");
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
			rm.setCallbackType("");
		}
		JsonUtil.returnMsg(rm);
	}

	// /get/set方法.......
	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public List<Role> getNoOwnRoles() {
		return noOwnRoles;
	}

	public void setNoOwnRoles(List<Role> noOwnRoles) {
		this.noOwnRoles = noOwnRoles;
	}

	public String[] getOwnRoleIds() {
		return ownRoleIds;
	}

	public void setOwnRoleIds(String[] ownRoleIds) {
		this.ownRoleIds = ownRoleIds;
	}

}
