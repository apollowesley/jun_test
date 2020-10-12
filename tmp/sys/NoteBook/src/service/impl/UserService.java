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

	/** 登录检查 */
	public Users findUser(Users user){		
		return userDAO.findUser(user);
	}
	
	/** 通过用户名查找用户 */
	public Users findUserByName(String username){
		if(null != username && !"".equals(username)){
			return userDAO.findUserByName(username);
		}
		return null;
	}
	
}
