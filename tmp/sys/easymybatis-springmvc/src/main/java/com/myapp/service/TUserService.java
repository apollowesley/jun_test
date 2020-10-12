package com.myapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.common.bean.BaseService;
import com.myapp.common.exception.SysException;
import com.myapp.dao.TUserDao;
import com.myapp.entity.TUser;

@Service
@Transactional
public class TUserService extends BaseService<TUserDao, TUser> {
	
	public void updateTran(TUser user) {
		try {
			user.setUsername("王五02");
			this.updateIgnoreNull(user);
			int i = 1/0;
		}catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}
	
	public TUser selectByName(String username) {
		return this.getDao().selectByName(username);
	}
}
