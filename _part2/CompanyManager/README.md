 CompanyManager项目简介
===================
该项目的搭建环境：
项目管理：maven
框架：spring、springmvc、mybatis、easyui
数据：mysql
主要用途：实现了用户管理、菜单管理、部门管理、理财管理、理财报表、待办管理、字典管理等多个功能，可作为有具体需要的朋友提供参考的demo，也可作为新手学习的demo。

开始看项目之前可以先看我之前上传到git上的项目，该项目讲解了ssm的环境搭建以及提供了一个可以现用的demo，朋友们可以直接将项目pull下来后直接使用。git地址：https://git.oschina.net/xi_fan/spring-springmvc-mybatis.git

目前该项目已经搭建在腾讯云上，有需要的朋友可以登录访问：http://123.207.236.102:8080/ssm_template/pages/login.jsp
超级管理员用户名：system
超级管理员密码：123

----------

###一、主要板块讲解：

-------------
####1、用户管理板块

需求说明：


> - 该项目的使用角色可分为三个：分别为一般用户、系统管理员、超级管理员；
> - 权限管理：超级管理员具有所有的权限，包括添加、修改、删除系统管理员等权限，而系统管理员具有管理一般用户的权限，遵守现实中上级管理下级的原则。

用户管理板块具体实现：

 > - 1、先从dao层说起：在cn.springmvc.dao中搭建好UserInfoDao，该类是一个接口，中包含着对user相关信息的增删查改。**具体的可看项目中cn.springmvc.dao.UserInfoDao的实现**
 > - 2 、在对应的目录下配置好UserInfoDao的Mapper映射文件，实现具体函数在mybatis中实现对Mysql的操作。**具体的可看项目中cn.springmvc.dao.UserInfoMapper的实现**
 > - 3 、在cn.springmvc.service中搭建好对应的service接口，并在cn.springmvc.service.impl中实现具体的service层功能。**具体的可看项目中cn.springmvc.service.impl.UserInfoServiceImpl的实现**
 > - 4 、在cn.springmvc.controller中实现对应的控制器**具体的可看项目中cn.springmvc.service.controller.UserInfoController的实现**


####2、菜单管理板块


需求说明：


> - 该项目的菜单主要可以分为两种，一是项目系统的菜单，需要编写相应的代码进行连接，二是web的菜单，只要提供链接既可以点击后实现跳转。
> - 主要实现菜单的crud。

菜单管理板块具体实现：

 > - 项目的分层以及具体实现跟用户管理板块类似，可以直接查看源码。
 
在这里我要谈谈我是如何进行mybatis的物理分页的：

>学过mybatis的都知道，mybatis使用RowBounds实现的分页是逻辑分页,也就是先把数据记录全部查询出来,然在再根据offset和limit截断记录返回为了在数据库层面上实现物理分页,如果我们不想改变原来MyBatis的函数逻辑,又实现物理分页，可以编写plugin截获MyBatis Executorstatementhandler,重写SQL来执行查询。

那么我是如何在整个项目中进行实现的呢？

>我所做的就是在view层、controller层以及Model层三个层中传递一个PageInfo对象，该对象绑定着分页的信息。

在controller层：

     @RequestMapping(value = "/queryUser")
     public String queryUser(Model    model,            @ModelAttribute(value = "pageInfo") PageInfo<UserInfo> pageInfo,@ModelAttribute(value = "user") UserInfo userInfo, @ModelAttribute(value = "dept") DeptInfo dept)  {     //实现业务 }


在service层：将PageInfo打包成RowBounds对象后传给dao层

    public ArrayList<UserInfo> queryUsers(Map<String, String> map,PageInfo<UserInfo> pageInfo) {
    return userInfoDao.queryUsers(new     RowBounds(pageInfo.getFromRecord(),pageInfo.getPageSize()), map);}

在mybatis配置文件中配置plugin：interceptor属性指向拦截器；

    <plugins>
		<plugin interceptor="cn.springmvc.utildao.PaginationInterceptor" />
	</plugins>

而PaginationInterceptor可以查看项目中cn.springmvc.utildao的源码，
对于主要实现数据库的CRUD的mapper没有任何变化。

> **Note:** 其它板块和以上两个差不多，具体的可以查看源码！！！

----------

###二、在ie以及360兼容模式下出现json数据被浏览器拦截并且直接请求保存的问题：
在springmvc-servlet文件中添加：

    <!-- json格式配置 -->
	<mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="supportedMediaTypes">
                    <list>
                        <value>text/html;charset=UTF-8</value>
                        <value>application/json;charset=UTF-8</value>
                    </list>
                </property>
                <property name="objectMapper">
                    <bean class="com.fasterxml.jackson.databind.ObjectMapper">
                        <property name="serializationInclusion">
                            <value type="com.fasterxml.jackson.annotation.JsonInclude.Include">NON_NULL</value>
                        </property>
                        <!-- 
                        <property name="dateFormat">
                            <bean class="java.text.SimpleDateFormat">
                                <constructor-arg value="yyyy-MM-dd HH:mm:ss"/>
                            </bean>
                        </property> -->
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>


便可以解决该问题。

-------------
> **Note:** 欢迎有兴趣的小伙伴们一起为该项目添加不一样的色彩！！！