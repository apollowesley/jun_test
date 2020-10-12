package cn.coder.jdbc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.jdbc.core.DataSourceConfig;
import cn.coder.jdbc.core.JdbcDataSource;
import cn.coder.jdbc.util.ObjectUtils;

public final class SqlSessionFactory {
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionFactory.class);

	private static final HashMap<String, DataSource> sourceMap = new HashMap<>();
	private static final String DEFAULT_SESSION = "default";

	public static SqlSession getSession() {
		return getSession(DEFAULT_SESSION);
	}

	public static SqlSession getSession(String sourceName) {
		DataSource source = sourceMap.get(sourceName);
		if (source == null)
			throw new NullPointerException("Can not find the datasource '" + sourceName + "'");
		return new SqlSession(source);
	}

	public synchronized static void createSessions() {
		Properties properties = ObjectUtils.loadProperties("jdbc.properties");
		if (properties == null)
			throw new NullPointerException("The jdbc.properties file not found");
		try {
			String allDb = DEFAULT_SESSION + "," + properties.getProperty("jdbc.all", "");
			String[] sourceArray = allDb.split(",");
			JdbcDataSource ds;
			for (String sourceName : sourceArray) {
				ds = new JdbcDataSource();
				ds.createPool(new DataSourceConfig(sourceName, properties));
				sourceMap.put(sourceName, ds);
			}
		} catch (SQLException e) {
			logger.error("Create session faild", e);
			throw new NullPointerException("Create session faild");
		}
	}

	public synchronized static SqlSession createSession(DataSource ds) {
		sourceMap.put("default", ds);
		SqlSession session = new SqlSession(ds);
		if (logger.isDebugEnabled())
			logger.debug("Sql session created:{}", session.hashCode());
		return session;
	}

	public synchronized static void destory() {
		Set<String> sources = sourceMap.keySet();
		DataSource source;
		for (String sourceName : sources) {
			source = sourceMap.get(sourceName);
			if (source instanceof JdbcDataSource) {
				((JdbcDataSource) source).close();
			}
		}
		sourceMap.clear();
	}
}
