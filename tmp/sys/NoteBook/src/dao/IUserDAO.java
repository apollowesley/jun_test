package dao;

import model.Users;

public interface IUserDAO {
	/** ��¼��� */
	public Users findUser(Users user);
	/** ͨ���û��������û� */
	public Users findUserByName(String username);
	
}
