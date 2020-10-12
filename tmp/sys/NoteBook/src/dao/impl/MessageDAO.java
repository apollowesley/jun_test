package dao.impl;

import java.util.List;

import model.Message;
import model.Users;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.BaseDAO;
import dao.IMessageDAO;

public class MessageDAO extends BaseDAO implements IMessageDAO {	
	
	/** ��ѯ���û����յ���������Ϣ */
	public List<Message> showMessage(Users user){
		Session session = sessionFactory.getCurrentSession();
//		Transaction ts = session.beginTransaction();
		
		Query query = session.createQuery("from Message where getter.userid=:userid")
				.setProperties(user);
		
		List<Message> messages = (List<Message>)query.list();	
		
//		ts.commit();
//		session.close();
		
		return messages;
	}
	
}
