package service;

import model.Users;


public interface IUserService {

	/** ��¼��� */
	Users findUser(Users user);
	/** ͨ���û��������û� */
	Users findUserByName(String username);
}
