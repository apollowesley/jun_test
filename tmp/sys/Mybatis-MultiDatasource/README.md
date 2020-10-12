# Mybatis-MultiDatasource
Mybatis中如何配置多数据源环境，灵感来自此博客内容：[Mybatis配置多数据源](https://www.cnblogs.com/lzrabbit/p/3750803.html)


### 一、配置过程
 **1. 配置CustomMultipleDataSource 继承 AbstractRoutingDataSource 重写 determineCurrentLookupKey方法** 

 **2. 配置多数据源bean** 
```xml
    <!-- 多数据源配置-->
    <import resource="spring-datasource.xml"></import>
    <bean id="multipleDataSource" class="cn.kiwipeach.demo.datasource.CustomMultipleDataSource">
        <!--默认数据源-->
        <property name="defaultTargetDataSource" ref="mysqlDataSource"/>
        <!--可支持数据源-->
        <property name="targetDataSources">
            <map>
                <entry key="mysqlDataSource" value-ref="mysqlDataSource"/>
                <entry key="oracleDataSource" value-ref="oracleDataSource"/>
            </map>
        </property>
    </bean>
```
 **3. Mybatis的其他通用项目配置** 
```xml
    <!--SqlSessionFactoryBean配置-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="multipleDataSource"/>
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations">
            <list>
                <value>classpath:mappings/**/*.xml</value>
            </list>
        </property>
        <!-- 数据库id标识 -->
        <property name="databaseIdProvider" ref="databaseIdProvider"></property>
        <!-- 注册类型 -->
        <property name="typeAliasesPackage" value="cn.kiwipeach.**.modal"></property>
    </bean>

    <!--Mapper扫描配置-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="cn.kiwipeach.demo.mapper"></property>
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
    </bean>

    <!--事务管理器配置 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="multipleDataSource"/>
    </bean>

    <!-- 使用声明式事务 -->
    <tx:annotation-driven transaction-manager="transactionManager"/>

```
 **4. 创建注解类** 
```java
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Datasource {

    String value() default "";

}
```
 **5. 配置数据源自动切换切面Aop** 

 _Java切面对象_ 
```java
public class CustomMultipleDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }
    @Override
    protected Object determineCurrentLookupKey() {
         return dataSourceKey.get();
    }
}
```
 _Spring xml配置文件_ 
```xml
    <!--数据源注解切面，实现自动切换数据源信息-->
    <bean id="loggingAspect" class="cn.kiwipeach.demo.aspect.DatasourceAspect"></bean>
    <aop:config>
        <aop:pointcut id="logPointcut" expression="execution(public * cn.kiwipeach.demo.mapper.*.*(..))"/>
        <aop:aspect ref="loggingAspect">
            <aop:before method="beforedMethod" pointcut-ref="logPointcut"></aop:before>
            <aop:after method="afterMethod" pointcut-ref="logPointcut"></aop:after>
            <aop:after-returning method="afterReturnMethod" pointcut-ref="logPointcut" returning="result"></aop:after-returning>
            <aop:after-throwing method="afterThrowMethod" pointcut-ref="logPointcut" throwing="ex"></aop:after-throwing>
        </aop:aspect>
    </aop:config>
```

 **6. 使用方法** 
```java
@Datasource("oracleDataSource")
public interface MethodMulityEmployMapper {

    @Datasource("mysqlDataSource")
    Employ mysqlSelectByPrimaryKey(BigDecimal empno);


    Employ oracleSelectByPrimaryKey(BigDecimal empno);

}
```

### 二、使用手动方法进行数据源切换操作
 **见测试类：** 
1. cn.kiwipeach.demo.multiple.MulityDatasourceTestCase2.testMulityDS1

### 三、使用Spring AOP+Annotation进行自动切换
 **见测试类：** 
1. cn.kiwipeach.demo.multiple.MulityDatasourceTestCase2.testMulityDS2
2. cn.kiwipeach.demo.service.impl.EmployServiceImpl#testMulityDatasource

### 三、多数据源事务控制
 **见测试类：** cn.kiwipeach.demo.service.impl.EmployServiceImpl#testMulityDatasourceTransation

### 四、注意事项
 **_使用下列代码加载会有问题，不要用这种方法进行多数据源测试_** 

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring-context.xml",
        "classpath:spring-mybatis.xml"
})
@Transactional
public class SpringJunitBase {
}
```

### 附上控制台日志打印结果
```
21:22:08.043 [main] INFO  cn.kiwipeach.demo.aspect.DatasourceAspect - 数据源将切换至[mysqlDataSource]进行查询.
21:22:08.046 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect beforedMethod....
21:22:08.046 [main] INFO  cn.kiwipeach.demo.aspect.DatasourceAspect - 数据源将切换至[mysqlDataSource]进行查询.
21:22:08.047 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect beforedMethod....
21:22:08.060 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Creating a new SqlSession
21:22:08.066 [main] DEBUG org.mybatis.spring.SqlSessionUtils - SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@22590e3e] was not registered for synchronization because synchronization is not active
21:22:08.085 [main] DEBUG org.mybatis.spring.transaction.SpringManagedTransaction - JDBC Connection [com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl@3874b815] will not be managed by Spring
21:22:08.098 [main] DEBUG cn.kiwipeach.demo.mapper.MethodMulityEmployMapper.mysqlSelectByPrimaryKey - ==>  Preparing: select EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO from EMP where EMPNO = ? 
21:22:08.300 [main] DEBUG cn.kiwipeach.demo.mapper.MethodMulityEmployMapper.mysqlSelectByPrimaryKey - ==> Parameters: 7777(BigDecimal)
21:22:08.349 [main] DEBUG cn.kiwipeach.demo.mapper.MethodMulityEmployMapper.mysqlSelectByPrimaryKey - <==      Total: 1
21:22:08.355 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@22590e3e]
21:22:08.357 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect afterMethod....
21:22:08.357 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect afterMethod....
21:22:08.359 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect afterReturnMethod....
21:22:08.359 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect afterReturnMethod....
{"comm":0.0,"deptno":20,"empno":7777,"ename":"黄莹","hiredate":1515859200000,"job":"SALESMAN","mgr":7902,"sal":0.0}
21:22:08.553 [main] INFO  cn.kiwipeach.demo.aspect.DatasourceAspect - 数据源将切换至[oracleDataSource]进行查询.
21:22:08.555 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect beforedMethod....
21:22:08.567 [main] INFO  cn.kiwipeach.demo.aspect.DatasourceAspect - 数据源将切换至[oracleDataSource]进行查询.
21:22:08.568 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect beforedMethod....
21:22:08.569 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Creating a new SqlSession
21:22:08.570 [main] DEBUG org.mybatis.spring.SqlSessionUtils - SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3f9f71ff] was not registered for synchronization because synchronization is not active
21:22:08.571 [main] DEBUG org.mybatis.spring.transaction.SpringManagedTransaction - JDBC Connection [com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl@27e5b378] will not be managed by Spring
21:22:08.571 [main] DEBUG cn.kiwipeach.demo.mapper.MethodMulityEmployMapper.oracleSelectByPrimaryKey - ==>  Preparing: select EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO from EMP where EMPNO = ? 
21:22:08.869 [main] DEBUG cn.kiwipeach.demo.mapper.MethodMulityEmployMapper.oracleSelectByPrimaryKey - ==> Parameters: 7777(BigDecimal)
21:22:08.885 [main] DEBUG cn.kiwipeach.demo.mapper.MethodMulityEmployMapper.oracleSelectByPrimaryKey - <==      Total: 1
21:22:08.885 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@3f9f71ff]
21:22:08.885 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect afterMethod....
21:22:08.885 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect afterMethod....
21:22:08.886 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect afterReturnMethod....
21:22:08.886 [main] DEBUG cn.kiwipeach.demo.aspect.DatasourceAspect - DatasourceAspect afterReturnMethod....
{"comm":0.0,"deptno":20,"empno":7777,"ename":"刘卜铷","hiredate":345830400000,"job":"SALESMAN","mgr":7902,"sal":0.0}

```
