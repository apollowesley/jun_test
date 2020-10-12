/**
 * 
 * @auther wuwang
 * @createTime 2014-7-7 下午10:39:22
 */
package com.admin.sysmenu;

import java.util.UUID;

import com.admin.AdminBase;
import com.hibernate.voDao.UsersDAO;

/**
 * 
 * 
 * @author peaches
 */
public class Users extends AdminBase<com.hibernate.voDao.Users, UsersDAO> {

	public String add() {
		// 自动生成一个测试用户
		bean.setUsername(UUID.randomUUID().toString().replace("-", ""));
		bean.setPassword(UUID.randomUUID().toString().replace("-", ""));
		dao.save(bean);
		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setBean()
	 */
	@Override
	public void setBean() {
		bean = new com.hibernate.voDao.Users();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.admin.AdminBase#setDao()
	 */
	@Override
	public void setDao() {
		dao = new UsersDAO();

	}
}
