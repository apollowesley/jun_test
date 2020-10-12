package service.impl;

import org.springframework.transaction.annotation.Transactional;

import model.Users;
import service.BaseService;
import service.IUserService;
import dao.IUserDAO;

//@Transactional
public class UserService extends BaseService implements IUserService {

	private IUserDAO userDAO;
	
	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/** ��¼��� */
	public Users findUser(Users user){		
		return userDAO.findUser(user);
	}
	
	/** ͨ���û��������û� */
	public Users findUserByName(String username){
		if(null != username && !"".equals(username)){
			return userDAO.findUserByName(username);
		}
		return null;
	}
	
}
