package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class BaseDAO implements IBaseDAO {

	/** hibernate的会话工厂对象 */
	protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/** 保存或更新对象 */
	public boolean saveOrUpdateObject(Object obj){
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		
		session.saveOrUpdate(obj);
		
		ts.commit();
		session.close();
		
		return true;
	}
	
}
