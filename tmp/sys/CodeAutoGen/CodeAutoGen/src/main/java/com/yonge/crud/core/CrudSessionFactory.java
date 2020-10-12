package com.yonge.crud.core;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonge.crud.core.db.ConnectionFactory;

public class CrudSessionFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CrudSessionFactory.class);

	private CrudSession session;

	private Connection connection;

	private String catalog;

	private String schema;

	private Configuration config;

	CrudSessionFactory(Configuration config) {
		LOGGER.debug("数据库的连接信息：url={} user={} password={}", config.getUrl(),
				config.getUsername(), config.getPassword());
		try {
			this.config = config;
			this.catalog = config.getCatalog();
			this.schema = config.getSchema();
			connection = ConnectionFactory.getInstance().getConnection(
					config.getDriverClass(), config.getUrl(),
					config.getUsername(), config.getPassword());
			session = new CrudSession(connection, catalog, schema, this);
		} catch (SQLException e) {
			LOGGER.warn("数据库连接创建失败", e);
		}
	}

	/**
	 * 开启一个会话
	 */
	public synchronized CrudSession openSession() {
		if (session == null) {
			session = new CrudSession(connection, catalog, schema, this);
		}
		return session;
	}

	/**
	 * 获取元数据
	 * 
	 * @return
	 */
	public Configuration getMetadata() {
		return config;
	}
}
