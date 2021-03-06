/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oschina.durcframework.easymybatis.ext;

import static org.springframework.util.Assert.notNull;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;

import net.oschina.durcframework.easymybatis.EasymybatisConfig;


/**
 * SqlSessionFactoryBean扩展
 * @author tanghc
 *
 */
public class SqlSessionFactoryBeanExt extends SqlSessionFactoryBean {
	
	private static Log LOGGER = LogFactory.getLog(SqlSessionFactoryBeanExt.class);
	
	private MapperLocationsBuilder mapperLocationsBuilder = new MapperLocationsBuilder();
	
	private String basePackage;
	
	@Override
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
		String dbName = buildDbName(dataSource);
		mapperLocationsBuilder.setDbName(dbName);
	}
	
	@Override
	public void setMapperLocations(Resource[] mapperLocations) {
		mapperLocationsBuilder.storeMapperFile(mapperLocations);
	}
	
	@Override
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		notNull(this.basePackage, "属性 'basePackage' 必填");
		
		Resource[] allMapperLocations = mapperLocationsBuilder.build(this.basePackage);
		// 重新设置mapperLocation属性
		super.setMapperLocations(allMapperLocations);
		
		return super.buildSqlSessionFactory();
	}
	
	
	/**
	 * @param basePackage
	 *            指定哪些包需要被扫描,支持多个包"package.a,package.b"并对每个包都会递归搜索
	 */
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public void setConfig(EasymybatisConfig easymybatisConfig) {
		mapperLocationsBuilder.setConfig(easymybatisConfig);
	}
	
	// 获取数据库类型
	private static String buildDbName(DataSource dataSource) {
		if(dataSource == null) {
			throw new NullPointerException("dataSource 不能为null");
		}
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			DatabaseMetaData metaData = conn.getMetaData();
			String dbName = metaData.getDatabaseProductName();
			LOGGER.info("数据库名称：" + dbName);
			return dbName;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}
	
}
