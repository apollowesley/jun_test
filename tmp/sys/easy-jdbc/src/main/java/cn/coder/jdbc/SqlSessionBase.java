package cn.coder.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.jdbc.core.DataSourceConfig;
import cn.coder.jdbc.core.JdbcDataSource;
import cn.coder.jdbc.support.ResultMapper;
import cn.coder.jdbc.util.JdbcUtils;

public abstract class SqlSessionBase {
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionBase.class);

	private static final SqlTranction[] EMPTY_ARRAY = new SqlTranction[0];
	private final ThreadLocal<SqlTranction> tl = new ThreadLocal<>();
	private final DataSource ds;
	private DataSourceConfig config;

	public SqlSessionBase(DataSource ds2) {
		this.ds = ds2;
		if (ds2 instanceof JdbcDataSource) {
			this.config = ((JdbcDataSource) ds2).getDataSourceConfig();
		}
	}

	public SqlTranction beginTranction() throws Exception {
		return beginTranction(EMPTY_ARRAY);
	}

	public SqlTranction beginTranction(SqlTranction... tranctions) throws Exception {
		if (tl.get() != null)
			throw new RuntimeException("The tranction has already exist");
		SqlTranction tran = new SqlTranction(this, ds.getConnection(), tranctions);
		tl.set(tran);
		logger.debug("Begin tranction:{}", tran.hashCode());
		return tran;
	}

	public void release(Connection con) {
		logger.debug("Connection releasing");
		if (ds instanceof JdbcDataSource) {
			((JdbcDataSource) ds).releaseConnection(con);
		}
		tl.remove();
	}

	protected <T> T doExecute(ResultMapper<T> mapper) {
		SqlTranction tran = null;
		Connection conn = null;
		Statement stmt = null;
		try {
			tran = tl.get();
			if (tran == null)
				conn = ds.getConnection();
			else {
				if (logger.isDebugEnabled())
					logger.debug("Run with tranction:{}", tran.hashCode());
				conn = tran.Connection(this);
			}
			stmt = mapper.makeStatement(conn);
			applySettings(stmt);
			T result = mapper.doStatement(stmt);
			handleWarnings(stmt);
			return result;
		} catch (SQLException ex) {
			if (stmt != null)
				logger.error("Error:[" + stmt.toString() + "]", ex);
			throw new RuntimeException("Execute faild:", ex);
		} finally {
			JdbcUtils.closeStatement(stmt);
			if (tran == null && ds instanceof JdbcDataSource) {
				((JdbcDataSource) ds).releaseConnection(conn);
			}
		}
	}

	private void applySettings(Statement stmt) throws SQLException {
		if (config != null)
			stmt.setQueryTimeout(config.getQueryTimeout());
	}

	private void handleWarnings(Statement stmt) throws SQLException {
		// Do Nothing
	}

	public void clear() {
		if (ds instanceof JdbcDataSource) {
			((JdbcDataSource) ds).close();
		}
	}

}
