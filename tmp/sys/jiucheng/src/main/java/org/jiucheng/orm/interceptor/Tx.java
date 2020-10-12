package org.jiucheng.orm.interceptor;

import java.sql.SQLException;

import org.jiucheng.aop.AopException;
import org.jiucheng.aop.Interceptor;
import org.jiucheng.aop.MethodInvocation;
import org.jiucheng.exception.UncheckedException;
import org.jiucheng.orm.DataAccessException;
import org.jiucheng.plugin.db.ManagerConnection;

public class Tx implements Interceptor {
	
	public Object invoke(MethodInvocation mi) throws Throwable {
		ManagerConnection.setAutoCommit(Boolean.FALSE);
		try {
			Object r = mi.proceed();
			ManagerConnection.commit();
			return r;
		}catch(AopException ae) {
			Throwable t = ae.getCause();
			if(t instanceof DataAccessException || t instanceof SQLException) {
			    ManagerConnection.rollback();
			}
			throw new UncheckedException(t);
		}finally {
		    ManagerConnection.setAutoCommit(Boolean.TRUE);
			ManagerConnection.close();
			ManagerConnection.clear();
		}
	}
}
