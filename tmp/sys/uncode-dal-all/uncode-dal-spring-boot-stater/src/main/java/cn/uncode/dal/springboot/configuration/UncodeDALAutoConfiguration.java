package cn.uncode.dal.springboot.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.uncode.dal.cache.CacheManager;
import cn.uncode.dal.cache.support.SimpleCacheManager;
import cn.uncode.dal.core.BaseDAL;
import cn.uncode.dal.descriptor.db.ResolveDataBase;
import cn.uncode.dal.descriptor.db.impl.SimpleResolveDatabase;
import cn.uncode.dal.mybatis.CommonMapper;
import cn.uncode.dal.mybatis.MybatisDAL;
import cn.uncode.dal.springboot.config.UncodeDALConfig;

/**
 * Created by KevinBlandy on 2017/2/28 14:11
 */
@Configuration
@EnableConfigurationProperties({UncodeDALConfig.class})
public class UncodeDALAutoConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UncodeDALAutoConfiguration.class);
	
	@Autowired
	private UncodeDALConfig uncodeDALConfig;
	
	@Bean(name = "commonMapper")
	public CommonMapper commonMapper(SqlSessionFactory sqlSessionFactory){
		MapperFactoryBean<CommonMapper> mapperFactoryBean = new MapperFactoryBean<CommonMapper>();
		mapperFactoryBean.setMapperInterface(CommonMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
		sqlSessionFactory.getConfiguration().addMapper(CommonMapper.class);
		CommonMapper commonMapper = null;
		try {
			commonMapper = (CommonMapper)mapperFactoryBean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("=====>CommonMapper inited..");
		return commonMapper;
	}
	
	@Bean(name = "dalCacheManager")
	public CacheManager cacheManager(){
		LOGGER.info("=====>CacheManager inited..");
		return new SimpleCacheManager();
	}
	
	
	@Bean(name = "resolveDatabase")
	public ResolveDataBase resolveDatabase(DataSource dataSource){
		SimpleResolveDatabase simpleResolveDatabase = new SimpleResolveDatabase();
		simpleResolveDatabase.setDataSource(dataSource);
		LOGGER.info("=====>ResolveDataBase inited..");
		return simpleResolveDatabase;
	}
	
	
	@Bean(name = "baseDAL")
	public BaseDAL baseDAL(CommonMapper commonMapper,  CacheManager cacheManager, ResolveDataBase resolveDatabase){
		MybatisDAL mybatisDAL = new MybatisDAL();
		mybatisDAL.setCommonMapper(commonMapper);
		mybatisDAL.setCacheManager(cacheManager);
		mybatisDAL.setResolveDatabase(resolveDatabase);
		mybatisDAL.setUseCache(uncodeDALConfig.isUseCache());
		mybatisDAL.setNoCacheTables(uncodeDALConfig.getNoCacheTables());
		mybatisDAL.setVersion(uncodeDALConfig.getVersionField());
		mybatisDAL.setVersionTables(uncodeDALConfig.getVersionTables());
		LOGGER.info("=====>BaseDAL inited..");
		return mybatisDAL;
	}
	
	
}
