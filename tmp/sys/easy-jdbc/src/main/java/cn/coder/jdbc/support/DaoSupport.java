package cn.coder.jdbc.support;

import java.sql.SQLException;

import cn.coder.jdbc.SqlSession;
import cn.coder.jdbc.SqlSessionFactory;
import cn.coder.jdbc.SqlTranction;

public abstract class DaoSupport {
	private static final String DEFAULT_SESSION = "default";
	private SqlSession temp;

	protected SqlSession jdbc() {
		if (temp == null)
			temp = jdbc(DEFAULT_SESSION);
		return temp;
	}

	protected SqlSession jdbc(String sourceName) {
		return SqlSessionFactory.getSession(sourceName);
	}

	protected boolean tran(Run run) {
		return tran(DEFAULT_SESSION, run);
	}

	public boolean exist(Object obj) {
		return jdbc().exist(obj);
	}

	public boolean insert(Object obj) {
		return jdbc().insert(obj);
	}

	public boolean update(Object obj) {
		return jdbc().update(obj);
	}

	public boolean delete(Object obj) {
		return jdbc().delete(obj);
	}

	protected boolean tran(String sourceName, Run run) {
		SqlSession session = jdbc(sourceName);
		SqlTranction tran = null;
		try {
			tran = session.beginTranction();
			run.exec(session);
			tran.commit();
			return true;
		} catch (Exception e) {
			if (tran != null)
				tran.rollback(e);
			return false;
		}
	}

	protected interface Run {
		// 执行事务
		void exec(final SqlSession session) throws SQLException;
	}
}
