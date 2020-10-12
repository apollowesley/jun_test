package com.slavic.hors.orm.base.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.Properties;

@Slf4j
@Configuration
@MapperScan(value = "com.slavic.hors.orm.mapper",sqlSessionFactoryRef = "sqlSessionFactoryBean")
public class DataSourceConfig {

    @Bean
    public DruidDataSource druidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/api_starter?serverTimezone=Hongkong");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setPoolPreparedStatements(false);
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(30);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setValidationQuery("SELECT 'j'");
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        Properties properties = new Properties();
        properties.setProperty("config.decrypt","false");
        dataSource.setConnectProperties(properties);

        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DruidDataSource druidDataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(druidDataSource);
        return dataSourceTransactionManager;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DruidDataSource druidDataSource) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver pathMatchResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = pathMatchResolver.getResources("classpath*:mapper/*-Mapper.xml");
            bean.setMapperLocations(resources);
        } catch (Exception e) {
            log.error("{}",e);
        }
        bean.setDataSource(druidDataSource);
        return bean;
    }
}
