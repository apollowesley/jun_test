package dao;

import model.Users;

public interface IUserDAO {
	/** 登录检查 */
	public Users findUser(Users user);
	/** 通过用户名查找用户 */
	public Users findUserByName(String username);
	
}
