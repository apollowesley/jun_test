package service;

import model.Users;


public interface IUserService {

	/** 登录检查 */
	Users findUser(Users user);
	/** 通过用户名查找用户 */
	Users findUserByName(String username);
}
