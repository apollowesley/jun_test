package com.mini.jdbc.cfg;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mini.jdbc.DbHelper;
import com.mini.jdbc.MiniDaoException;
import com.mini.jdbc.dialect.Dialect;
import com.mini.jdbc.dialect.DialectFactory;
import com.mini.jdbc.dialect.H2Dialect;
import com.mini.jdbc.dialect.MysqlDialect;
import com.mini.jdbc.dialect.OracleDialect;
import com.mini.jdbc.dialect.SqlServerDialect;
import com.mini.jdbc.dynamic.DynamicDataSource;
import com.mini.jdbc.dynamic.DynamicDialect;
import com.mini.jdbc.ehcache.EhCacheHelper;

/**
 * 基础配置文件
 * @author sxjun1904
 *
 */
public class MiniConfig {
	/**
	 * 数据库帮助类，非必须配置
	 */
	private DbHelper dbHelper;
	
	/**
	 * 动态数据源，dynamicDialect和dynamicDataSource两者选一配置
	 */
	private DynamicDataSource dynamicDataSource;
	
	/**
	 * jdbcTemplate类，非必须配置
	 */
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 动态方言，dynamicDialect和dynamicDataSource两者选一配置
	 */
	private DynamicDialect dynamicDialect;
	
	private boolean exchange = false;//是否转义数据源方言
	
	/**
	 * 设置是否转义数据源方言
	 * @param exchange
	 */
	public void setExchange(boolean exchange) {
		this.exchange = exchange;
	}
	
	/**
	 * 初始化MiniJdbc配置
	 */
	public MiniConfig(){
		initCache();
	}
	
	/**
	 * 初始化MiniJdbc配置
	 */
	public MiniConfig(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
		initCache();
	}

	/**
	 * 初始化缓存
	 */
	public void initCache() {
		EhCacheHelper ehCacheHelper = new EhCacheHelper();
		ehCacheHelper.start();
	}

	/**
	 * 获取动态数据源
	 * @return
	 */
	public DynamicDataSource getDynamicDataSource() {
		return dynamicDataSource;
	}

	/**
	 * 设置动态数据源
	 * @param dynamicDataSource
	 */
	public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
		this.dynamicDataSource = dynamicDataSource;
	}

	/**
	 * 获取数据库访问帮助类
	 * @return
	 */
	public DbHelper getDbHelper() {
		if(dbHelper==null){
			dbHelper = new DbHelper();
			dbHelper.setJdbcTemplate(getJdbcTemplate());
			dbHelper.setDialect(getDynamicDialect());
		}
		return dbHelper;
	}

	/**
	 * 获取jdbctemplate
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate() {
		if(jdbcTemplate==null){
			if(getDynamicDataSource()!=null){
				jdbcTemplate = new JdbcTemplate();
				jdbcTemplate.setDataSource(getDynamicDataSource());
			}else{
				throw new MiniDaoException("You have not config dynamicDataSource");
			}
		}else{
			if(getDynamicDataSource()==null){
				dynamicDataSource = new DynamicDataSource();
				dynamicDataSource.setDefaultTargetDataSource(jdbcTemplate.getDataSource());
				Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
				targetDataSources.put(UUID.randomUUID().toString(), jdbcTemplate.getDataSource());
				dynamicDataSource.setTargetDataSources(targetDataSources);
			}
		}
		return jdbcTemplate;
	}

	/**
	 * 设置jdbcTemplate
	 * @param jdbcTemplate
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 获取动态方言
	 * @return
	 */
	public DynamicDialect getDynamicDialect() {
		if(dynamicDialect==null)
			if(getDynamicDataSource()!=null){
				dynamicDialect = new DynamicDialect();
				dynamicDialect.setTargetDialect(loadDialect(dynamicDataSource.getTargetDataSources()));
				dynamicDialect.setDefaultDialect(loadDialect((DataSource)dynamicDataSource.getDefaultTargetDataSource()));
			}else{
				throw new MiniDaoException("You have not config dynamicDataSource or dynamicDialect...");
			}
		return dynamicDialect;
	}

	/**
	 * 设置动态方言
	 * @param dynamicDialect
	 */
	public void setDynamicDialect(DynamicDialect dynamicDialect) {
		this.dynamicDialect = dynamicDialect;
	}
	
	/**
	 * 获取方言
	 * @param datasources
	 * @return
	 */
	public Map<Object,Dialect> loadDialect(Map<Object, Object> datasources){
		Map<Object,Dialect> dialects = new HashMap<Object,Dialect>();
		for(Entry<Object, Object> entry : datasources.entrySet()){
			dialects.put(entry.getKey(), loadDialect(loadDialect((DataSource)entry.getValue())));
		}
		return dialects;
	}
    
    /**
	 * 获取方言
	 * @param datasource
	 * @return
	 */
	private Dialect loadDialect(DataSource datasource){
		Dialect dialect = null;
		try {
			dialect = DialectFactory.getDialect(datasource.getConnection());
		} catch (SQLException e) {
			throw new MiniDaoException("get dialect error!");
		}
		return loadDialect(dialect);
	}
	
	/**
	 * 获取方言
	 * @param dialect
	 * @return
	 */
	private Dialect loadDialect(Dialect dialect){
		if(dialect instanceof OracleDialect){
			return new OracleDialect();
		}else if(dialect instanceof SqlServerDialect){
			return new SqlServerDialect();
		}else if(dialect instanceof  MysqlDialect){
			return new MysqlDialect();
		}else if(dialect instanceof  H2Dialect){
			return new H2Dialect();
		}
		return null;
	}
	
}
