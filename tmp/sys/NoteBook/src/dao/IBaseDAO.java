package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public interface IBaseDAO {

	/** �������¶��� */
	public boolean saveOrUpdateObject(Object obj);
	
}
