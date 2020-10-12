package com.myapp.config;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.myapp.common.bean.StringRemarkFill;

import net.oschina.durcframework.easymybatis.EasymybatisConfig;
import net.oschina.durcframework.easymybatis.ext.SqlSessionFactoryBeanExt;
import net.oschina.durcframework.easymybatis.support.DateFillInsert;

/**
 * 数据库配置,不想用easymybatis-spring-boot-starter可采用这种方式，
 * 打开@Configuration，@EnableTransactionManagement注释即可。
 * 此方式类似于传统的xml配置
 * @author tanghc
 *
 */
//@Configuration
//@EnableTransactionManagement
public class DatabaseConfig {

	// sqlSessionFactory名称
	private static final String sqlSessionFactoryName = "sqlSessionFactory";
	// mybatis的config文件
	private static final String mybatisConfigLocation = "classpath:mybatis/mybatisConfig.xml";
	// mybatis的mapper文件
	private static final String mybatisMapperLocations = "classpath:mybatis/mapper/*.xml";
	// 存放mapper包路径，多个用;隔开
	private static final String basePackage = "com.myapp.dao";
	
	@Bean
	public EasymybatisConfig easymybatisConfig() {
		EasymybatisConfig config = new EasymybatisConfig();
		/* 驼峰转下划线形式，默认是true
		 开启后java字段映射成数据库字段将自动转成下划线形式
		 如：userAge -> user_age
		 如果数据库设计完全遵循下划线形式，可以启用
		 这样可以省略Entity中的注解，@Table，@Column都可以不用，只留
		@Id
		@GeneratedValue 
		参见：UserInfo.java
		*/
		config.setCamel2underline(true);
		config.setFills(Arrays.asList(
				new DateFillInsert("add_time")
				));
		
		return config;
	}
	
	@Bean(name="dataSource")
	@Primary
	public DataSource dataSource() throws SQLException {
		return DruidDataSourceBuilder.create().build();
	}

	/*
	 * <!-- 替换org.mybatis.spring.SqlSessionFactoryBean -->
	<bean id="sqlSessionFactory"
		class="net.oschina.durcframework.easymybatis.ext.SqlSessionFactoryBeanExt">
		<property name="dataSource" ref="dataSource" />
		<property name="configLocation">
			<value>classpath:mybatis/mybatisConfig.xml</value>
		</property>
		<property name="mapperLocations">
			<list>
				<value>classpath:mybatis/mapper/*.xml</value>
			</list>
		</property>

		<!-- dao所在的包名,跟MapperScannerConfigurer的basePackage一致 
			多个用;隔开
		-->
		<property name="basePackage" value="com.myapp.dao" />
		
	</bean>
	 */
	@Bean(name = sqlSessionFactoryName)
	public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource,EasymybatisConfig config) throws Exception {
		SqlSessionFactoryBeanExt bean = new SqlSessionFactoryBeanExt();
		
		bean.setDataSource(dataSource);
		bean.setConfigLocation(this.getResource(mybatisConfigLocation));
		bean.setMapperLocations(this.getResources(mybatisMapperLocations));
		
		// ====以下是附加属性====
		
		// dao所在的包名,跟MapperScannerConfigurer的basePackage一致,多个用;隔开
		bean.setBasePackage(basePackage);
		bean.setConfig(config);
		
		return bean;
	}
	
	/*
	 <!-- DAO接口所在包名，Spring会自动查找其下的类 -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
		<property name="basePackage" value="com.myapp.dao" />
	</bean>
	 */
	@Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(sqlSessionFactoryName);
        mapperScannerConfigurer.setBasePackage(basePackage);
        return mapperScannerConfigurer;
    }

	@Bean(name="transactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager(@Autowired DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	private Resource[] getResources(String path) throws IOException {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		return resolver.getResources(path);
	}
	
	private Resource getResource(String path) {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		return resolver.getResource(path);
	}

}
