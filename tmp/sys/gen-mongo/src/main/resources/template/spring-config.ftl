<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd" default-autowire="byName">

    <context:component-scan base-package="org.gen"/>


    <bean id="mongoClient" class="com.mongodb.MongoClient">
        <constructor-arg index="0">
            <list>
                <bean class="com.mongodb.ServerAddress">
                    <constructor-arg index="0" value="localhost"/>
                </bean>
            </list>
        </constructor-arg>
    </bean>

    <bean id="mongoDatabase" factory-bean="mongoClient" factory-method="getDatabase">
        <constructor-arg type="java.lang.String" value="test"/>
    </bean>

    <bean id="userCollection" factory-bean="mongoDatabase" factory-method="getCollection">
        <constructor-arg type="java.lang.String" value="user"/>
    </bean>

    <bean id="adminCollection" factory-bean="mongoDatabase" factory-method="getCollection">
        <constructor-arg type="java.lang.String" value="admin"/>
    </bean>



</beans>