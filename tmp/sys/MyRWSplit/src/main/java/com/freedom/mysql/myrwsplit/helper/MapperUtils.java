package com.freedom.mysql.myrwsplit.helper;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.SQLException;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

public class MapperUtils {
	static class MapperInvoker<T> implements InvocationHandler, Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5556427256761687656L;
		private T mapper;
		private SqlSession sqlSession;
		private boolean closed = false;

		public MapperInvoker(T m, SqlSession s) {
			mapper = m;
			sqlSession = s;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (closed) {
				throw new SQLException("this mapper is closed...");
			}
			// 执行
			Object result = method.invoke(mapper, args);
			// 关闭
			sqlSession.close();// 考虑到读写分离,这里必须关闭--->释放连接到连接池
			sqlSession=null;//help GC
			closed = true;
			return result;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getMapper(Class<T> clazz) {
		SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSessionFactory().openSession(true);
		T mapper = sqlSession.getMapper(clazz);
		T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
				new MapperInvoker(mapper, sqlSession));
		return proxy;
	}

	// 增加其它的参数
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getMapper(ExecutorType execType, Class<T> clazz) {
		SqlSession sqlSession = SqlSessionFactoryHelper.getSqlSessionFactory().openSession(execType, true);
		T mapper = sqlSession.getMapper(clazz);
		T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
				new MapperInvoker(mapper, sqlSession));
		return proxy;
	}

}
