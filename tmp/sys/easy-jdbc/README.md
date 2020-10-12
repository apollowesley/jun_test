# easy-jdbc

一个支持高并发的jdbc链接池，体积小、速度快，目前只支持mysql<br>
如果您觉得好用，请Star

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.4coder/jdbc/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.4coder/jdbc/)
![Jar Size](https://img.shields.io/badge/jar--size-50.5k-blue.svg)

特性
-------------------------
* 轻量、高效
* 无复杂配置
* 直接手写SQL
* 支持返回多个结果集
* 可配置多数据源
* 支持多数据源事务

环境
-------------
- JDK 7
- slf4j

如何使用
-----------------------
* 添加dependency到POM文件:

```
<dependency>
    <groupId>cn.4coder</groupId>
    <artifactId>jdbc</artifactId>
    <version>0.0.6</version>
</dependency>
```

* src/main/resources目录下添加jdbc.properties文件:
```
/* 多数据源名称需逗号分隔 */
jdbc.all=test1
jdbc.multiQueries=true
jdbc.queryTimeout=5

/* 默认数据源 */
jdbc.datasource.driverClassName=com.mysql.jdbc.Driver
jdbc.datasource.url=jdbc:mysql://localhost:3306/test
jdbc.datasource.username=root
jdbc.datasource.password=123456
jdbc.datasource.initialSize=4

/* 其它的数据源 */
jdbc.datasource.test1.driverClassName=com.mysql.jdbc.Driver
jdbc.datasource.test1.url=jdbc:mysql://localhost:3306/test1
jdbc.datasource.test1.username=root
jdbc.datasource.test1.password=123456
jdbc.datasource.test1.initialSize=2
```

* [编码实现](https://gitee.com/yydf/easy-jdbc/wikis/pages)
```
SqlSessionFactory.createSessions();//创建数据源
SqlSession session = SqlSessionFactory.getSession("default");//获取某个数据源
SqlSession session1 = SqlSessionFactory.getSession("test1");
System.out.println(session.selectOne(Integer.class, "select count(1) from weike"));

//多数据源事务
SqlTranction tran = null;
try {
	tran = session.beginTranction(session1.beginTranction());
	session.execute("INSERT INTO weike (title) VALUES (?)", "test");
	session1.execute("INSERT INTO test (title) VALUES (?)", "test1");
	tran.commit();
} catch (Exception e) {
	if(tran != null)
		tran.rollback(e);
}

//销毁数据源
SqlSessionFactory.destory(); 
```

* 项目中可继承DaoSupport直接使用
```
public class WeikeDao extends DaoSupport {

	public List<Weike> getDeviceList() {
		return jdbc().selectList(Weike.class, "select * from weike");
	}
	
	//事务处理
	public boolean test() {
		return tran(new Run(){
			@Override
			public void exec(SqlSession session) {
				//保持使用同一个session
				//session.selectList
				//session.selectOne
			}
		});
	}
}
```
