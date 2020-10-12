package com.freedom.mysql.myrwsplit.runnable;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;

import com.freedom.mysql.myrwsplit.helper.LoggerHelper;
import com.freedom.mysql.myrwsplit.helper.SqlSessionFactoryHelper;

@SuppressWarnings("rawtypes")
public abstract class MyRwSplitTransactionRunnable<T> extends BaseRunnable {
	private static final boolean AUTO_COMMIT = false;
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(BaseRunnable.class);

	protected SqlSession sqlSession = null;

	public MyRwSplitTransactionRunnable() {
		sqlSession = SqlSessionFactoryHelper.getSqlSessionFactory().openSession(AUTO_COMMIT);
	}

	public MyRwSplitTransactionRunnable(ExecutorType execType) {
		sqlSession = SqlSessionFactoryHelper.getSqlSessionFactory().openSession(execType, AUTO_COMMIT);
	}

	public MyRwSplitTransactionRunnable(TransactionIsolationLevel level) {
		sqlSession = SqlSessionFactoryHelper.getSqlSessionFactory().openSession(level);
	}

	//不是自动提交
	public MyRwSplitTransactionRunnable(ExecutorType execType, TransactionIsolationLevel level) {
		sqlSession = SqlSessionFactoryHelper.getSqlSessionFactory().openSession(execType, level);
	}

	public abstract T execute(SqlSession sqlSession) throws Exception;

	@Override
	public T run() throws Exception {
		try {
			T result = execute(sqlSession);
			sqlSession.commit();
			return result;
		} catch (Exception e) {
			sqlSession.rollback();
			LOGGER.error(e.toString());
			throw e;
		} finally {
			if (null != sqlSession) {
				sqlSession.close();
			}
		}
	}

}
