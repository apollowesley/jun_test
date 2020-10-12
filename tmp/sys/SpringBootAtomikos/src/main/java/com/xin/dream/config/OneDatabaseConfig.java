package com.xin.dream.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import com.alibaba.druid.pool.xa.DruidXADataSource;

@Configuration
@MapperScan(basePackages = "com.xin.dream.one.dao", sqlSessionTemplateRef = "oneSqlSessionTemplate")
public class OneDatabaseConfig {
	@Autowired
	public OneDataSourceProperties oneDataSourceProperties;
	@Primary
	@Bean(name = "oneDataSource", destroyMethod = "close")
	public DataSource oneDataSource() {
		DruidXADataSource datasource = new DruidXADataSource();
    	BeanUtils.copyProperties(oneDataSourceProperties,datasource);
    	AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(datasource);
        xaDataSource.setUniqueResourceName("oneDataSource");
        return xaDataSource;
	}

	@Primary
	@Bean(name = "oneSqlSessionFactory")
	public SqlSessionFactory oneSqlSessionFactory(@Qualifier("oneDataSource") DataSource oneDataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(oneDataSource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		bean.setMapperLocations(resolver.getResources("classpath:com/xin/dream/one/mapper/*.xml"));
		return bean.getObject();
	}

	@Primary
	@Bean(name = "oneSqlSessionTemplate")
	public SqlSessionTemplate oneSqlSessionTemplate(
			@Qualifier("oneSqlSessionFactory") SqlSessionFactory oneSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(oneSqlSessionFactory);
	}

}
