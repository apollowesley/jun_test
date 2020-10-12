package org.nature4j.framework.proxy;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import org.nature4j.framework.annotation.Tx;
import org.nature4j.framework.db.ConnectionManager;
import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyServiceInvocation implements MethodInterceptor {

	private static Logger LOGGER = LoggerFactory.getLogger(ProxyServiceInvocation.class);

	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		if (method.getName().equals("finalize")) {
			method.invoke(proxy, args);
		}
		return invokeMethod(proxy, method, args, methodProxy);
	}

	private Object invokeMethod(Object proxy, Method method, Object[] args, MethodProxy methodProxy)
			throws SQLException, Throwable {
		Object returnValue = null; 
		String[] dbs = ConfigHelper.getDb();
		int dbsLength = dbs.length;
		Connection[] conns = new Connection[dbsLength];
		for (int i = 0; i < dbsLength; i++) {
			conns[i] = ConnectionManager.getSequenceConn(dbs[i]);
		}
		try {
			if (method.isAnnotationPresent(Tx.class)) {
				for (int i = 0; i < dbsLength; i++) {
					Tx tx = method.getAnnotation(Tx.class);
					int level = tx.level();
					if (level == -1) {
						level = ConfigHelper.getJdbcDefaultTransactionIsolation(dbs[i]);
					}
					conns[i].setAutoCommit(false);
					conns[i].setTransactionIsolation(level);
				}
				returnValue = methodProxy.invokeSuper(proxy, args);
				for (int i = 0; i < dbsLength; i++) {
					conns[i].commit();
				}
			} else {
				returnValue = methodProxy.invokeSuper(proxy, args);
			}
		} catch (Exception e) {
			for (int i = 0; i < dbsLength; i++) {
				conns[i].rollback();
			}
			LOGGER.error("invoke method " + method.getName() + " error");
			throw new RuntimeException(e);
		} finally {
			for (int i = 0; i < dbsLength; i++) {
				ConnectionManager.closeSequenceConn(dbs[i]);
			}
		}
		return returnValue;
	}

}
