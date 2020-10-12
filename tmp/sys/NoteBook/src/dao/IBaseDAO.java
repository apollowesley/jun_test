package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

public interface IBaseDAO {

	/** 保存或更新对象 */
	public boolean saveOrUpdateObject(Object obj);
	
}
