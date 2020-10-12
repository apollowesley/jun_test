#lemur-dao
--------------------------------
说明
--------------------------------
		普通服务的DAO层,单不是我们常用的Dao层框架,而是基于spring jdbc做的扩展,同时提供sql分离的解决方案,为hibernate应用,提供sql分离已经调用
	spring jdbc提供统一方案

		集成了freemark 和 ognl 这样就扩展了spring jdbc的一些方案,同时你在工程中想调用原生的sql,可以提供类mybatis的解决方案,获取sql也就是
	几个字的代码,争取做到最简单,引入了mybatis命名空间的思想,可以根据类+方法名很容易定位到你的sql.
	freemark的集成可以很容易做到类似于mybatis的sql拼接,免去了java 类中代码拼接的麻烦事
	集成了 where and这种无用语法的判断,可以简单集成
	
	作者博客：http://blog.csdn.net/qjueyue
	作者邮箱： qrb.jueyue@gmail.com
	QQ群:  364192721
	
	
```xml
<?xml version="1.0" encoding="UTF-8"?>
<mapper namespace="com.onepiece.dao.test.examples.dao.EmployeeDao">
	<sql key="contion">
		<![CDATA[
			<#if employee.age ?exists>
				and age = :employee.age
			</#if>
			<#if employee.name ?exists>
				and name = :employee.name
			</#if>
			<#if employee.empno ?exists>
				and empno = :employee.empno
			</#if>
		]]>
	</sql>
	<sql key="getAllEmployees">
		<![CDATA[
			SELECT * FROM employee where
			<#include "com.onepiece.dao.test.examples.dao.EmployeeDao.contion">
		]]>
	</sql>
	<sql key="getCount">
		SELECT count(*) FROM employee
	</sql>
	<sql key="getMap">
		SELECT * FROM employee WHERE empno = :empno and name = :name
	</sql>
</mapper>
```
这里sql没有想mybatis分的那么清楚,全部都是sql,区分CURD 是用的命名规则
```java
	//这个是CURD前缀
	private static final String        INF_METHOD_ACTIVE = "insert,add,create,update,modify,store,delete,remove";
	//这个是批量方法前缀
    private static final String        INF_METHOD_BATCH  = "batch";
```
希望大家还是少用curd 因为这个是属于JPA的解决区域,没有必要用本地sql这么麻烦的事情

------------------------
实例
------------------------
###1.和spring的集成
```xml
	<!-- JDBC配置 -->
	<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
		<property name="dataSource">
			<ref bean="dataSource" />
		</property>
	</bean>

	<!-- JDBC 占位符配置 -->
	<bean id="namedParameterJdbcTemplate"
		class="org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate">
		<constructor-arg ref="dataSource" />
	</bean>

	<!-- lemur-dao扫描类 -->
	<bean class="io.lemur.dao.factory.DaoBeanScannerConfigurer">
		<!-- dao地址,配置符合spring方式 -->
		<property name="daoPackage" value="io.lemur.dao.test.examples.dao"></property>
		<property name="sqlPackage" value="mapper"></property>
		<!-- 使用的注解,默认是lemur-dao,推荐 Repository 默认是IDao-->
		<property name="annotation" value="org.springframework.stereotype.Repository"></property>
	</bean>
```

		主要使用的是namedParameterJdbcTemplate 因为这个是预编译sql的,而且支持map传参
	配置也是符合spring的统一风格
	
###2.sql写法
		就是上面的sql写法,freemark这里我就不多说了,jdbcTemplate的写法最好不要用,这个要说的是namedParameterJdbcTemplate写法
	and name = :name 开头是:后面就是el表达式的写法了

###3.获取sql的方法 SQLResource 这个类的,
	最简单的是直接get,要求命名空间必须是当前类和当前方法
	第二个是传参,然后freemark解析下
	第三个是传入key
	第四种是传入key 和map 这个适用性全部
代码如下
```java
	/**
     * 根据类名和方法名获取 动态 SQL
     * 
     * @return SQL
     */
    public static String get() {
        return get(getMethodUrl(), null);
    }

    /**
     * 根据类名和方法名获取 动态 SQL
     * 
     * @return SQL
     */
    public static String get(Map<String, Object> map) {
        return get(getMethodUrl(), map);
    }

    /**
     * 根据类名和方法名获取 动态 SQL
     * 
     * @return SQL
     */
    public static String get(String key, Map<String, Object> map) {
        try {
            Template template = config.getTemplate(key);
            if (template == null) {
                throw new RuntimeException(key + "---------不存在该Key");
            }
            Writer write = new StringWriter();
            template.process(map, write);
            String sql = write.toString();
            sql = sql.replaceAll("\\n", " ").replaceAll("\\t", " ").replaceAll("\\s{1,}", " ").trim();
            if (StringUtils.endsWithIgnoreCase(sql, "where")) {
                sql = sql.substring(0, sql.length() - 5);
            }
            int index = 0;
            while ((index = indexOfIgnoreCase(sql, "where and", index)) != -1) {
                sql = sql.substring(0, index + 5) + sql.substring(index + 9, sql.length());
            }
            return sql;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
```
###4.接口调用spring jdbc
```java
@Repository
public interface UserDao {

    public List<Map<String, Object>> listUserByAge(@IDaoParam("age") Integer age);

    public void updateUserBirthday(@IDaoParam("name") String name, @IDaoParam("age") Integer age,
                                   @IDaoParam("birthday") Date birthday);

}
```
和mybatis基本上一样,其实没必要再出来一个语法折腾自己
然后调用接口就是了,后面的就和mybatis-spring做的事情一样了


    	