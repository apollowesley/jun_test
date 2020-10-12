package org.jiucheng.orm.interceptor;

import org.jiucheng.aop.Interceptor;
import org.jiucheng.aop.MethodInvocation;
import org.jiucheng.plugin.db.ManagerConnection;

public class Close implements Interceptor {
	
	public Object invoke(MethodInvocation mi) throws Throwable {
		try{
			Object r = mi.proceed();
			return r;
		}finally {
		    ManagerConnection.close();
		    ManagerConnection.clear();
		}
	}
}
