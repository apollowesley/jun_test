# SpringMVC+Freemarker+Maven整合项目


### 1.添加maven依赖spring-context-support、freemarker
```
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
        </dependency>

        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>${freemarker.version}</version>
        </dependency>
```
 

### 2.配置freemarker视图解析器
```
    <!--2.2 配置freemarker模板引擎-->
    <!--配置freeMarker -->
    <bean id="freeMarkerConfigurer"
          class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
        <property name="templateLoaderPath" value="/WEB-INF/views/"></property>
        <property name="defaultEncoding" value="utf-8"/>
        <property name="freemarkerSettings">
            <bean class="org.springframework.beans.factory.config.PropertiesFactoryBean">
                <property name="location" value="classpath:freemarker.properties"/>
            </bean>
            <!--
            <props>
                <prop key="template_update_delay">1</prop>
                <prop key="locale">zh_CN</prop>
                <prop key="datetime_format">yyyy-MM-dd</prop>&lt;!&ndash; 时间格式化 &ndash;&gt;
                <prop key="date_format">yyyy-MM-dd</prop>
                <prop key="number_format">#.##</prop>
            </props>
            -->
        </property>
    </bean>

    <bean id="freeMarkerViewResolver"
          class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
        <property name="cache" value="true"/>
        <property name="prefix" value=""/><!-- 上面已经配了，这里就不用配啦 -->
        <property name="suffix" value=".ftl"/>
        <property name="contentType" value="text/html;charset=UTF-8"/>
        <property name="allowSessionOverride" value="true"/>
        <property name="allowRequestOverride" value="true"/>
        <property name="exposeSpringMacroHelpers" value="true"/>
        <property name="exposeRequestAttributes" value="true"/>
        <property name="exposeSessionAttributes" value="true"/>
        <property name="requestContextAttribute" value="request"/>
    </bean>
```

### 4.基本数据类型的取值+封装类型的取值

### 5.Collection的遍历+Map数据的遍历

### 6.字串集合操作

### 7.自定义函数

### 8.自定义指令

### 9.其他拓展

### 整理预览图

![功能案例菜单](https://gitee.com/uploads/images/2018/0226/161212_952e0f87_1387578.png "29CME_NLI)DC0(HC94}PJH2.png")
