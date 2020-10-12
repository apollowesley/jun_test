package com.evil.struts2.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.evil.pojo.system.Right;
import com.evil.pojo.system.Role;
import com.evil.service.RightService;
import com.evil.service.RoleService;
import com.evil.util.JsonUtil;
import com.evil.util.Page;
import com.evil.util.ReturnMsg;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Controller("RoleAction")
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	private static final long serialVersionUID = 4871673324066365745L;
	@Resource
	private RoleService roleService;
	@Resource
	private RightService rightService;

	private List<Right> noOwnRights = new ArrayList<Right>();
	private String[] OwnRightIds;

	/**
	 * 到角色的管理界面
	 * 
	 * @return
	 */
	public String toRoleManagePage() {
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleName",
				"%" + (model.getRoleName() == null ? "" : model.getRoleName())
						+ "%");
		String sortfield = orderField + " " + orderDirection;
		pageResult = roleService
				.findRolesPage(page, map, sortfield, " id desc");
		return "roleManagePage";
	}

	/**
	 * 到添加角色页面
	 * 
	 * @return
	 */
	public String toAddRolePage() {
		noOwnRights=rightService.findAllEntities();
		return "addRolePage";
	}

	/**
	 * 到修改角色页面
	 * 
	 * @return
	 */
	public String toAlterRolePage() {
		model = roleService.getEntity(model.getId());
		noOwnRights=rightService.findRightsNotInRange(model.getRights());
		return "alterRolePage";
	}

	/**
	 * 保存/更新角色
	 */
	public void doSaveOrUpdateRole() {
		ReturnMsg rm = new ReturnMsg();
		System.out.println(OwnRightIds.length+","+StringUtil.arr2Str(OwnRightIds));
		try {
			roleService.saveOrUpdateRole(model,OwnRightIds);
			rm.setCallbackType("closeCurrent");
			rm.setNavTabId("roleTab");
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败 " + e.getMessage());
			rm.setCallbackType("");
		} finally {
			JsonUtil.returnMsg(rm);
		}
	}

	/**
	 * 批量删除管理员
	 */
	public void bacthDeleteRoles() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if (!ValidateUtil.isNull(ids)) {
				roleService.batchDeleteRoles(StringUtil.strSplit(ids, ","));

			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setNavTabId("roleTab");
		JsonUtil.returnMsg(rm);
	}

	// get/set方法...
	public List<Right> getNoOwnRights() {
		return noOwnRights;
	}

	public void setNoOwnRights(List<Right> noOwnRights) {
		this.noOwnRights = noOwnRights;
	}

	public String[] getOwnRightIds() {
		return OwnRightIds;
	}

	public void setOwnRightIds(String[] ownRightIds) {
		OwnRightIds = ownRightIds;
	}

}
