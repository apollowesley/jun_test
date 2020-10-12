package com.yonge.crud.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonge.crud.core.db.DatabaseIntrospector;
import com.yonge.crud.core.db.model.Table;
import com.yonge.crud.core.generator.ModuleGenerateIntrospector;

public class CrudSession {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CrudSession.class);

	private Connection connection;

	private DatabaseMetaData dbMetaData;

	private String catalog;

	private String schema;

	private CrudSessionFactory sessionFactory;

	CrudSession(Connection connection, String catalog, String schema,
			CrudSessionFactory sessionFactory) {
		if (connection == null) {
			LOGGER.warn("没有获取连接，不能打开session");
			return;
		}
		this.connection = connection;
		this.catalog = catalog;
		this.schema = schema;
		this.sessionFactory = sessionFactory;
		try {
			this.dbMetaData = connection.getMetaData();
		} catch (SQLException e) {
			LOGGER.warn("获取数据库连接的元数据失败", e);
		}
	}

	/**
	 * 关闭会话
	 */
	public void closeSession() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.warn("数据库连接关闭失败", e);
			}
		}
	}

	/**
	 * 获取工厂信息
	 * 
	 * @return
	 */
	public CrudSessionFactory getFactory() {
		return sessionFactory;
	}

	/**
	 * 获取所有表信息
	 * 
	 * @return
	 */
	public List<Table> getTables() {
		DatabaseIntrospector dbIntrospector = new DatabaseIntrospector(
				dbMetaData);
		return dbIntrospector.getTables(catalog, schema);
	}

	/**
	 * 数据库表的反向工程
	 * 
	 * @param table
	 *            表对象
	 * @param srcBase
	 *            生成目录的根路径
	 * @param pojoPackageName
	 *            存放pojo的包名称
	 * @param sqlmapPackageName
	 *            存放sqlmap的包名称
	 * @param daoPackageName
	 *            存放dao的包名称
	 */
	public void reverse(Table table, String srcBase, String pojoPackageName,
			String sqlmapPackageName, String daoPackageName,
			String servicePackageName) {
		ModuleGenerateIntrospector
				.generatePOJO(table, srcBase, pojoPackageName);
		ModuleGenerateIntrospector.generatSqlmap(this.getFactory()
				.getMetadata().getDriverClass(), table, srcBase,
				sqlmapPackageName, daoPackageName);
		ModuleGenerateIntrospector.generateDaoAndService(table, srcBase,
				daoPackageName, servicePackageName);
	}

	/**
	 * 生成spring的配置文件
	 * 
	 * @param tables
	 *            需要生成spring配置文件对应的表
	 * @param srcBase
	 *            根目录
	 * @param packageName
	 *            配置文件的包路径
	 * @param daoPackageName
	 *            dao的包路径
	 * @param servicePackageName
	 *            service的包路径
	 */
	public void generateSpringConfig(List<Table> tables, String srcBase,
			String packageName, String daoPackageName, String servicePackageName) {
		ModuleGenerateIntrospector.generateSpring(tables, srcBase, packageName,
				daoPackageName, servicePackageName);
	}

	/**
	 * 生成sqlmapConfig文件
	 * 
	 * @param tables
	 *            相关表信息
	 * @param srcBase
	 *            生成目标的根目录
	 * @param packageName
	 *            sqlmapConfig文件的包路径
	 * @param sqlmapPackageName
	 *            sqlmap文件的包路径
	 */
	public void generateSqlmapConfig(List<Table> tables, String srcBase,
			String packageName, String sqlmapPackageName) {
		ModuleGenerateIntrospector.generateSqlMapConfig(tables, srcBase,
				sqlmapPackageName, packageName);
	}

}
