package com.evil.pojo.system;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.evil.pojo.BaseEntity;

public class AdminUser extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name; // �û���
	private String password; // ����

	private long[] rightSum; // ����Ȩ�޵��ܺ�

	private boolean superAdmin; // �Ƿ��ǳ�������Ա

	// �����޸�ע��ʱ��
	private Date regdate = new Date();

	// ��ʾUser��Role�Ķ�Զ��ӳ��ļ���
	private Set<Role> roles = new HashSet<Role>();

	// get/set����....
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long[] getRightSum() {
		return rightSum;
	}

	public void setRightSum(long[] rightSum) {
		this.rightSum = rightSum;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	// ����...
	/**
	 * �������н�ɫ��Ȩ��
	 */
	public void calculateRightSum() {
		int pos = 0;
		long code = 0;
		for (Role role : roles) {
			// �ж��Ƿ��ǳ�������Ա
			if ("-1".equals(role.getRoleValue())) {
				superAdmin = true;
				roles = null;
				return;
			}
			for (Right right : role.getRights()) {
				pos = right.getRightPos();
				code = right.getRightCode();
				rightSum[pos] = rightSum[pos] | code;
			}
		}

		// �ͷ���Դ
		roles = null;
	}

	/**
	 * �жϸ��û��Ƿ�߱���Ȩ��
	 */
	public boolean hasRight(Right right) {
		int pos = right.getRightPos();
		long code = right.getRightCode();
		return (rightSum[pos] & code) != 0;
	}

}
