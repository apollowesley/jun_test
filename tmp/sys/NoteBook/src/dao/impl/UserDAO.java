package dao.impl;

import model.Users;

import org.hibernate.Query;
import org.hibernate.Session;

import dao.BaseDAO;
import dao.IUserDAO;

public class UserDAO extends BaseDAO implements IUserDAO {
	
	/** ��¼��� */
	public Users findUser(Users user){
		Session session = sessionFactory.getCurrentSession();	//sessionFactory.openSession();
//		Transaction ts = session.beginTransaction();
		
		//OK���Ƽ������ַ�ʽ
		Query query = session.createQuery("from Users as user where user.username=:username and user.password=:password")
				.setProperties(user);
				
		// ��ѯ����ֻ��һ�����ݵ�������uniqueResult������list������
		Users user1 = (Users) query.uniqueResult();	
		
//		ts.commit();
//		session.close();

		return user1;
	}
	
	/** ͨ���û��������û� */
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
