package com.evil.struts2.frontendAction;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.User;
import com.evil.service.UserService;
import com.evil.struts2.UserAware;
import com.evil.struts2.action.BaseAction;
import com.evil.util.JsonUtil;
import com.evil.util.MD5Util;
import com.evil.util.ValidateUtil;

@Component("FrontendUserAction")
@Scope("prototype")
public class FrontendUserAction extends BaseAction<User> implements UserAware,SessionAware {

	private static final long serialVersionUID = 1L;

	// ע��UserService
	@Resource(name = "UserService")
	private UserService userService;

	private String newpass1;
	private String newpass2;

	private User user;
	private Map<String, Object> session;
	
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session=arg0;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * ���޸��û��������
	 * 
	 * @return
	 */
	public String toChangepassPage() {
		return "changepassPage";
	}
	/**
	 * �����޸�����
	 */
	public void doChangepass() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!ValidateUtil.isNull(model.getPassword())
				&& !ValidateUtil.isNull(newpass1)
				&& !ValidateUtil.isNull(newpass2)){
			if (user.getPassword().equals(MD5Util.encode(model.getPassword()))) {
				if (newpass1.equals(newpass2)) {
					user.setPassword(MD5Util.encode(newpass1));
					userService.saveOrUpdateEntity(user);
					map.put("message", "�����޸ĳɹ�");
				} else {
					map.put("message", "�������벻ƥ��");
				}
			} else {
				map.put("message", "�����벻ƥ��");
			}
		}else{
			map.put("message", "������Ϣ����Ϊ��");
		}
		JsonUtil.writeJsonData(map);
	}
	
	/**
	 * ���û���Ϣ����
	 * 
	 * @return
	 */
	public String toUserinfoPage() {
		return "userinfoPage";
	}
	/**
	 * �����޸��û���Ϣ
	 */
	public void doUpdateUserinfo(){
		try {
			user.setUserName(model.getUserName());
			user.setUserAge(model.getUserAge());
			user.setUserUnit(model.getUserUnit());
			userService.saveOrUpdateEntity(user);
			session.put("user", user);
			JsonUtil.writeJsonData("�޸ĳɹ�");;
		} catch (Exception e) {
			JsonUtil.writeJsonData("�޸�ʧ�� ԭ��:"+e.getMessage());
			e.printStackTrace();
		}
		
	}

	// get/set����...
	
	public String getNewpass1() {
		return newpass1;
	}
	
	public void setNewpass1(String newpass1) {
		this.newpass1 = newpass1;
	}
	public String getNewpass2() {
		return newpass2;
	}

	public void setNewpass2(String newpass2) {
		this.newpass2 = newpass2;
	}

}
