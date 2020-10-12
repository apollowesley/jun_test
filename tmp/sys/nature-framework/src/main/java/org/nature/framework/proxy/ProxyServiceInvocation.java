package org.nature.framework.proxy;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import org.nature.framework.annotation.Tx;
import org.nature.framework.helper.ConfigHelper;
import org.nature.framework.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyServiceInvocation implements MethodInterceptor{

	private static Logger LOGGER = LoggerFactory.getLogger(ProxyServiceInvocation.class);

	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		Object returnValue = null;
			returnValue = invokeMethod(proxy, method, args, methodProxy, returnValue);
		return returnValue;
	}

	private Object invokeMethod(Object proxy, Method method, Object[] args, MethodProxy methodProxy, Object returnValue)
			throws SQLException, Throwable {
		Connection conn = DatabaseHelper.getConn();
		try {
			if (method.isAnnotationPresent(Tx.class)) {
				Tx tx = method.getAnnotation(Tx.class);
				int level = tx.level();
				if (level == -1) {
					level = ConfigHelper.getJdbcDefaultTransactionIsolation();
				}
				conn.setAutoCommit(false);
				conn.setTransactionIsolation(level);
				returnValue = methodProxy.invokeSuper(proxy, args);
				conn.commit();
			} else {
				returnValue = methodProxy.invokeSuper(proxy, args);
			}
		} catch (Exception e) {
			conn.rollback();
		}finally {
			DatabaseHelper.closeConn(conn);
		}
		return returnValue;
	}

}
