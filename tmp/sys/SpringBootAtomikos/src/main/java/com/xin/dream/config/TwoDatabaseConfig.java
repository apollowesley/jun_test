package com.xin.dream.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.alibaba.druid.pool.xa.DruidXADataSource;

@Configuration
@MapperScan(basePackages = "com.xin.dream.two.dao",sqlSessionFactoryRef = "twoSqlSessionFactory")
public class TwoDatabaseConfig {
	
	@Autowired
	public TwoDataSourceProperties twoDataSourceProperties;
	@Bean(name = "twoDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.two")
	public DataSource twoDataSource() {
		//return DataSourceBuilder.create().build();
		DruidXADataSource datasource = new DruidXADataSource();
    	BeanUtils.copyProperties(twoDataSourceProperties,datasource);
    	AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(datasource);
        xaDataSource.setUniqueResourceName("twoDataSource");
        return xaDataSource;
	}

	@Bean(name = "twoSqlSessionFactory")
	public SqlSessionFactory twoSqlSessionFactory(@Qualifier("twoDataSource") DataSource twoDataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		// 设置别名包（实体类）
		//bean.setTypeAliasesPackage("com.xin.dream.two.pojo");
		bean.setDataSource(twoDataSource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		bean.setMapperLocations(resolver.getResources("classpath:com/xin/dream/two/mapper/*.xml"));
		return bean.getObject();
	}
	
	@Bean(name = "twoSqlSessionTemplate")
	public SqlSessionTemplate twoSqlSessionTemplate(@Qualifier("twoSqlSessionFactory") SqlSessionFactory twoSqlSessionFactory)
			throws Exception {
		return new SqlSessionTemplate(twoSqlSessionFactory);
	}
	
}
