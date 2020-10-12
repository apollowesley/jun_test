---
title: 快速集成
category: 小目标
order: 1
---

# 视频教程
{% video %}http://on1sjifdn.bkt.clouddn.com/mydalgen/getting-started-on.mp4{% endvideo %}

# 集成步骤
1. 登陆码云下载mydalgen依赖的资源文件[mydalgen-resource.tar](http://git.oschina.net/smartboot/maven-mydalgen-plugin/attach_files)
2. 将**mydalgen-resource.tar**内的文件解压至您的项目工程中dal层模块的目录`src/main/resource/META-INF`下
3. 修改dal层**pom.xml**文件，设置`maven-mydalgen-plugin`插件配置
```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	....
	<!-- maven-mydalgen-plugin 配置 -->
	<properties>
		<middlegen.package>org.smartboot.dal.middlegen</middlegen.package>
		<middlegen.package.dir>org/smartboot/dal/middlegen</middlegen.package.dir>
		<middlegen.jdbc.url>jdbc:mysql://localhost:3306/demo</middlegen.jdbc.url>
		<middlegen.jdbc.user>root</middlegen.jdbc.user>
		<middlegen.jdbc.password>root</middlegen.jdbc.password>
		<middlegen.jdbc.driver>com.mysql.jdbc.Driver</middlegen.jdbc.driver>
		
		<middlegen.destination>${basedir}/src/main</middlegen.destination>
		<middlegen.source>${middlegen.destination}/resources/META-INF</middlegen.source>
		<middlegen.config.file>${middlegen.source}/tables-config.xml</middlegen.config.file>
		<middlegen.templates>file://${middlegen.source}/templates</middlegen.templates>
	</properties>
	<build>
		<plugins>
			<plugin>
				<groupId>org.smartboot.maven.plugin</groupId>
				<artifactId>maven-mydalgen-plugin</artifactId>
				<version>1.0.2</version>
				<configuration>
					<config>
						<configFile>${middlegen.config.file}</configFile>
						<mergedir>${middlegen.source}/templates/mergedir</mergedir>
						<middlegenPackage>${middlegen.package}</middlegenPackage>
						<jdbcDriver>${middlegen.jdbc.driver}</jdbcDriver>
						<jdbcUrl>${middlegen.jdbc.url}</jdbcUrl>
						<jdbcUser>${middlegen.jdbc.user}</jdbcUser>
						<jdbcPassword>${middlegen.jdbc.password}</jdbcPassword>
					</config>

					<fileProducers>
						<fileProducer>
							<!-- 为每一个表生成一个DO -->
							<destination>${middlegen.destination}/java/${middlegen.package.dir}</destination>
							<template>${middlegen.templates}/do.vm</template>
							<filename>dataobject/{0}DO.java</filename>
						</fileProducer>

						<fileProducer>
							<!-- 为每一个表生成一个DAO -->
							<destination>${middlegen.destination}/java/${middlegen.package.dir}</destination>
							<template>${middlegen.templates}/dao.vm</template>
							<filename>dao/{0}DAO.java</filename>
						</fileProducer>

						<fileProducer>
							<!-- 生成dal层组件bean -->
							<destination>${middlegen.destination}/java/${middlegen.package.dir}</destination>
							<template>${middlegen.templates}/middlegen-dal-bean.vm</template>
							<filename>MiddlegenDalBean.java</filename>
						</fileProducer>

						<fileProducer>
							<!-- 生成sqlMap-mapping -->
							<destination>${middlegen.destination}/resources</destination>
							<template>${middlegen.templates}/table-sqlmap-mapping.vm</template>
							<filename>mybatis/mapping/{0}-sqlmap-mapping.xml</filename>
						</fileProducer>
					</fileProducers>
				</configuration>
			</plugin>
		</plugins>
	</build>
</project>
```	
pom.xml文件中需要根据实际项目进行调整的属性参数: 
	- middlegen.package 	
		mydalgen插件生成Java代码的包路径，例如：`org.smartboot.dal.middlegen`
	- middlegen.package.dir		
		mydalgen插件生成Java代码的存放目录，将**middlegen.package**值中的`.`换成`/`即可，例如：`org/smartboot/dal/middlegen`
	- middlegen.jdbc.url	
		数据库URL
	- middlegen.jdbc.user	
		数据库用户名
	- middlegen.jdbc.password	
		数据库密码
	- middlegen.jdbc.driver
		数据库驱动，因为当前插件仅支持**MySQL**，故设置成`com.mysql.jdbc.Driver`即可
	 
4. 设置完成后在DAL层所在模块下运行命令`mvn mydalgen:mybatis`，出现如下信息说明配置成功
```
[INFO] Scanning for projects...
[INFO]                                                                         
[INFO] ------------------------------------------------------------------------
[INFO] Building smart-dal 0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-mydalgen-plugin:1.0.1:mybatis (default-cli) @ smart-dal ---
[INFO] **********************************
[INFO] * 开始启动dalgen任务 *
[INFO] * org.smartboot.maven.plugin.mydalgen.MiddlegenMojo *
[INFO] **********************************
[INFO] 系统启动，注册[IWalletPlugin]插件...
[INFO] 开始执行dalgen任务：org.smartboot.maven.plugin.mydalgen.MiddlegenMojo
[INFO] databaseProductName= MySQL
[INFO] databaseProductVersion= 5.5.43-0ubuntu0.14.04.1
[INFO] driverName= MySQL Connector Java
[INFO] driverVersion= mysql-connector-java-6.0.4 ( Revision: d2d72c397f9880b5861eb144cd8950eff808bffd )
[INFO] schema= null
[INFO] catalog= null
[INFO] 验证插件：iwallet
[INFO] dalgen任务执行完成, 耗时[799]ms.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.786 s
[INFO] Finished at: 2016-12-23T16:43:14+08:00
[INFO] Final Memory: 14M/169M
[INFO] ------------------------------------------------------------------------
```