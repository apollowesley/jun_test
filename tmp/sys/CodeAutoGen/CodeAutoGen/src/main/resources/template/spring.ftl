[#ftl]
<?xml version="1.0" encoding="UTF-8"?>
<!--
This file is auto generated by Ant & FreeMarker.
DO NOT MODIFY IT.All modified content will be lost when next auto generate.
这个文件是用ANT和FreeMarker自动生成的。
不要修改此文件。所有改动将在下次重新自动生成时丢失。
-->
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx" xmlns:jdbc="http://www.springframework.org/schema/jdbc"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc-2.5.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.5.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd"
	default-autowire="byName">

	<import resource="classpath*:conf/spring/spring-query.xml" />
	
	<import resource="classpath*:conf/spring/spring-datasource.xml" />
	[#-- 
	<bean id="sqlMapClient" class="org.springframework.orm.ibatis.SqlMapClientFactoryBean">
		<property name="configLocation" value="${sqlmapConfigPackageName}/SqlMap_Config.xml" />
		<property name="dataSource" ref ="${r"${dataSource}"}" />
	</bean>
	--]
	
	[#list tables as table]
	<bean id="${table.className?uncap_first}Dao" class="com.erayt.solar2.db.DaoProxyFactoryBean">
		<property name="daoInterface" value="${daoPackageName}.${table.className}Dao" />
		<property name="sqlMapClient" ref="sqlMapClient" />
	</bean>
	
	<bean id="${table.className?uncap_first}Service" class="${servicePackageName}.impl.${table.className}ServiceImpl" />
	
	[/#list]
</beans>