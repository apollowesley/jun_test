package cn.coder.jdbc;

import java.sql.Connection;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库事务处理对象
 * 
 * @author YYDF
 *
 */
public final class SqlTranction {
	private static final Logger logger = LoggerFactory.getLogger(SqlTranction.class);

	private final Connection conn;
	private final SqlTranction[] tranctions;
	private final SqlSessionBase session;
	private final boolean moreTranctions;

	public SqlTranction(SqlSessionBase session, Connection connection, SqlTranction[] tranctions) throws SQLException {
		this.session = session;
		this.tranctions = tranctions;
		this.moreTranctions = (tranctions != null && tranctions.length > 0);
		this.conn = connection;
		this.conn.setAutoCommit(false);
	}

	public Connection Connection(SqlSessionBase current) {
		if (current.equals(session))
			return this.conn;
		if (moreTranctions) {
			for (SqlTranction tran : tranctions) {
				if (current.equals(tran.getSession())) {
					return tran.Connection();
				}
			}
		}
		throw new NullPointerException("Can not find the session tranction");
	}

	private SqlSessionBase getSession() {
		return this.session;
	}

	private Connection Connection() {
		return this.conn;
	}

	public void commit() {
		try {
			this.conn.commit();
			if (logger.isDebugEnabled())
				logger.debug("Tranction {} commited", this.hashCode());
			if (moreTranctions) {
				for (SqlTranction tran : tranctions) {
					tran.commit();
				}
			}
		} catch (SQLException e) {
			logger.error("Tranction commit faild", e);
		} finally {
			close();
		}
	}

	public void rollback(Exception e) {
		try {
			this.conn.rollback();
			if (logger.isDebugEnabled())
				logger.debug("Tranction " + this.hashCode() + " rollbacked", e);
			if (moreTranctions) {
				for (SqlTranction tran : tranctions) {
					tran.rollback(e);
				}
			}
		} catch (SQLException ex) {
			logger.error("Tranction rollback faild", ex);
		} finally {
			close();
		}
	}

	private void close() {
		try {
			this.conn.setAutoCommit(true);
			this.session.release(this.conn);
			if (logger.isDebugEnabled())
				logger.debug("Tranction {} closed", this.hashCode());
			if (moreTranctions) {
				for (SqlTranction tran : tranctions) {
					tran.close();
				}
			}
		} catch (SQLException e) {
			logger.error("Tranction close faild", e);
		}
	}
}
