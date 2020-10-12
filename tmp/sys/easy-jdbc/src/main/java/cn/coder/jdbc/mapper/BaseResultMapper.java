package cn.coder.jdbc.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.jdbc.core.EntityWrapper;
import cn.coder.jdbc.core.EntityWrapper.SQLType;
import cn.coder.jdbc.core.BeanMapping;
import cn.coder.jdbc.support.ResultMapper;
import cn.coder.jdbc.util.JdbcUtils;

public abstract class BaseResultMapper<T> implements ResultMapper<T> {
	private static final Logger logger = LoggerFactory.getLogger(BaseResultMapper.class);

	private final String sql;
	private final Object[] objs;
	private final boolean withKey;

	private static final HashMap<Integer, BeanMapping> resultMappings = new HashMap<>();
	private static final HashMap<String, EntityWrapper> entityWrappers = new HashMap<>();

	public BaseResultMapper(String sql, Object[] data) {
		this(sql, data, false);
	}

	public BaseResultMapper(String sql, Object[] data, boolean withKey) {
		this.sql = sql;
		this.objs = data;
		this.withKey = withKey;
	}

	@Override
	public Statement makeStatement(Connection conn) throws SQLException {
		PreparedStatement stmt;
		if (withKey)
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		else
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		return stmt;
	}

	@Override
	public T doStatement(Statement stmt) throws SQLException {
		PreparedStatement _stmt = (PreparedStatement) stmt;
		JdbcUtils.bindArgs(_stmt, objs);
		return doPreparedStatement(_stmt);
	}

	public abstract T doPreparedStatement(PreparedStatement stmt) throws SQLException;

	protected synchronized static BeanMapping getBeanMapping(Class<?> target, int hash, ResultSetMetaData metaData)
			throws SQLException {
		BeanMapping mapping;
		if (resultMappings.containsKey(hash)) {
			mapping = resultMappings.get(hash);
			logger.debug("Load result mappings from cache");
		} else {
			mapping = JdbcUtils.buildFiledMappings(target, metaData);
			resultMappings.put(hash, mapping);
			logger.debug("Build result mappings from meta data");
		}
		return mapping;
	}

	protected synchronized static EntityWrapper getEntityWrapper(Object data, SQLType type) {
		String clazzName = data.getClass().getName();
		EntityWrapper wrapper;
		if (entityWrappers.containsKey(clazzName)) {
			logger.debug("Load entity wrapper from cache");
			wrapper = entityWrappers.get(clazzName);
		} else {
			logger.debug("Build entity wrapper");
			wrapper = new EntityWrapper(data.getClass());
			entityWrappers.put(clazzName, wrapper);
		}
		return wrapper.setObject(data, type);
	}
}
