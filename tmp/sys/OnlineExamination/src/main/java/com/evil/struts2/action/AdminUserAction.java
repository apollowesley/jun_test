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

	private String repassword;// ȷ�������ֶ�
	private List<Role> noOwnRoles; // �û����߱��Ľ�ɫ

	// �����û���ɫid�ļ���
	private String[] ownRoleIds;

	/**
	 * ��ʾadminUser�б�
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
	 * ����ӹ���Ա����
	 * 
	 * @return
	 */
	public String toAddAdminUserPage() {
		return "addAdminUserPage";
	}

	/**
	 * ���޸Ĺ���Ա����
	 * 
	 * @return
	 */
	public String toAlterAdminUserPage() {
		model = adminUserService.getEntity(model.getId());
		return "alterAdminUserPage";
	}

	/**
	 * ����/���¹���Ա
	 */
	public void doSaveOrUpdateAdminUser() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if (ValidateUtil.isNull(model.getName())
					|| ValidateUtil.isNull(model.getPassword())) {
				throw new Exception("��Ϣ����Ϊ��");
			} else {
				if (model.getId() == null) {
					if (!repassword.equals(model.getPassword())) {
						throw new Exception("���ε����벻��Ϊ��");
					}
					if (adminUserService.isNameOccupy(model.getName())) {
						throw new Exception("�û�����ռ��");
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
			rm = ReturnMsg.ERRORMsg("���¹���Աʧ��\n" + e.getMessage());
			rm.setCallbackType("");
		} finally {
			JsonUtil.returnMsg(rm);
		}

	}

	/**
	 * ����ɾ������Ա
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
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setNavTabId("adminUserTab");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * �������ɫҳ��
	 * 
	 * @return
	 */
	public String toAssignRolesPage() {
		model = adminUserService.getEntity(model.getId());
		noOwnRoles = roleService.findRolesNotInRange(model.getRoles());
		return "assignRolesPage";
	}

	/**
	 * ������¹���Ա��Ȩ
	 */
	public void doUpdateAmdinUserAuthorize() {
		ReturnMsg rm = new ReturnMsg();
		try {
			adminUserService.updateAmdinUserAuthorize(model,ownRoleIds);
			rm.setCallbackType("closeCurrent");
			rm.setNavTabId("adminUserTab");
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
			rm.setCallbackType("");
		}
		JsonUtil.returnMsg(rm);
	}

	// /get/set����.......
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
