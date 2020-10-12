package com.evil.struts2.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.evil.pojo.User;
import com.evil.service.UserService;
import com.evil.util.JsonUtil;
import com.evil.util.MD5Util;
import com.evil.util.Page;
import com.evil.util.ReturnMsg;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Controller("UserAction")
@Scope("prototype")
public class UserAction extends BaseAction<User> implements SessionAware {
	private static final long serialVersionUID = -845370001102208846L;

	@Resource
	private UserService userService;

	private String ids;// ѡ��������ַ�����","�ָ�
	private String uid;// ��ǰ�����id
	private String repassword;// ȷ������
	private Map<String, Object> sessionMap; // ����session����

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}

	/**
	 * ����ʾ�û�ҳ��(�ɷ�ҳ��������� )
	 */
	public String toUsersPage() {
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", "1");
		map.put("userName",
				"%" + (model.getUserName() == null ? "" : model.getUserName())
						+ "%");
		String sortfield = orderField + " " + orderDirection;
		pageResult = userService
				.findPageUsers(page, map, sortfield, " id desc");
		return "usersPage";
	}

	/**
	 * ��������û�
	 */
	public void bacthDeleteUsers() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if (!ValidateUtil.isNull(ids)) {
				userService.batchDeleteUsers(StringUtil.strSplit(ids, ","));

			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setNavTabId("userTab");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * ���༭����ҳ��
	 */
	public String toEditUserPage() {
		if (!ValidateUtil.isNull(uid)) {
			model = userService.getEntity(uid);
		}
		return "editUserPage";
	}
	/**
	 *�����ǰ̨�û����� 
	 */
	public String toAddUserPage(){
		return "addUserPage";
	}

	/**
	 * ���������û�
	 */
	public void doUpdateUser() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if(ValidateUtil.isNull(model.getEmailAddress())||ValidateUtil.isNull(model.getPassword())){
				throw new Exception("��Ϣ����Ϊ��");
			}else{
				if(model.getId()==null){
					if(userService.isRegisted(model.getEmailAddress())){
						throw new Exception("���䱻ռ��");
					}
					model.setPassword(MD5Util.encode(model.getPassword()));
				}else{
					if("123456".equals(model.getPassword())){
						model.setPassword(MD5Util.encode(model.getPassword()));
					}
				}
				userService.saveOrUpdateEntity(model);
			}
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setNavTabId("userTab");
		JsonUtil.returnMsg(rm);
	}
	/**
	 * ��������/�����û�
	 */
	public void toggleUserStatus() {
		model = userService.getEntity(model.getId());
		ReturnMsg rm = new ReturnMsg();
		try {
			userService.toggleUserStatus(model);
			User user = (User) sessionMap.get("user");
			if (user != null) {
				if (user.getId().equals(model.getId())) {
					sessionMap.remove("user");
				}
			}

		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("����ʧ��" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setNavTabId("userTab");
		JsonUtil.returnMsg(rm);
	}

	// get/set����.....
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	

}
