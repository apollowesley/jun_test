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

	private String ids;// 选择的问题字符串用","分割
	private String uid;// 当前问题的id
	private String repassword;// 确认密码
	private Map<String, Object> sessionMap; // 接受session参数

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}

	/**
	 * 到显示用户页面(可分页排序多条件 )
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
	 * 批量输出用户
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
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setNavTabId("userTab");
		JsonUtil.returnMsg(rm);
	}

	/**
	 * 到编辑问题页面
	 */
	public String toEditUserPage() {
		if (!ValidateUtil.isNull(uid)) {
			model = userService.getEntity(uid);
		}
		return "editUserPage";
	}
	/**
	 *到添加前台用户界面 
	 */
	public String toAddUserPage(){
		return "addUserPage";
	}

	/**
	 * 保存或更新用户
	 */
	public void doUpdateUser() {
		ReturnMsg rm = new ReturnMsg();
		try {
			if(ValidateUtil.isNull(model.getEmailAddress())||ValidateUtil.isNull(model.getPassword())){
				throw new Exception("信息不能为空");
			}else{
				if(model.getId()==null){
					if(userService.isRegisted(model.getEmailAddress())){
						throw new Exception("邮箱被占用");
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
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("closeCurrent");
		rm.setNavTabId("userTab");
		JsonUtil.returnMsg(rm);
	}
	/**
	 * 处理启用/禁用用户
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
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}
		rm.setCallbackType("");
		rm.setNavTabId("userTab");
		JsonUtil.returnMsg(rm);
	}

	// get/set方法.....
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
