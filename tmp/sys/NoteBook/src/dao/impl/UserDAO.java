package dao.impl;

import model.Users;

import org.hibernate.Query;
import org.hibernate.Session;

import dao.BaseDAO;
import dao.IUserDAO;

public class UserDAO extends BaseDAO implements IUserDAO {
	
	/** 登录检查 */
	public Users findUser(Users user){
		Session session = sessionFactory.getCurrentSession();	//sessionFactory.openSession();
//		Transaction ts = session.beginTransaction();
		
		//OK，推荐用这种方式
		Query query = session.createQuery("from Users as user where user.username=:username and user.password=:password")
				.setProperties(user);
				
		// 查询出来只有一条数据的这种用uniqueResult（）比list（）快
		Users user1 = (Users) query.uniqueResult();	
		
//		ts.commit();
//		session.close();

		return user1;
	}
	
	/** 通过用户名查找用户 */
	public Users findUserByName(String username){
		Session session = sessionFactory.getCurrentSession();
//		Transaction ts = session.beginTransaction();
		
		final String hql = "from Users as u where u.username=:username";
		Query query = session.createQuery(hql)
				.setString("username", username);
		Users user1 = (Users) query.uniqueResult();	
		
//		ts.commit();
//		session.close();
				
		return user1;
	}
	
}
