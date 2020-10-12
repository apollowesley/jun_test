# make_knife

#### 项目介绍
Spring Cloud 完整微服务搭建与部署（centos7）<br/>
架构描述：<br/>
        服务注册中心：Eureka<br/>
        用户管理中心：user-center<br/>
        贸易管理中心：deal-center<br/> 

#### 软件架构
软件架构说明：<br/>
    核心框架：Spring Boot<br/>
    模板引擎：Thymeleaf<br/>
    持久层框架：MyBatis<br/>
    数据库连接池：Alibaba Druid<br/>
    缓存框架：Ehcache<br/>
    日志管理：SLF4J+Logback<br/>
<hr/>
<p>item-parent：<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 是一个父级项目，打包方式是POM，作用：控制其下所有子项目的jar包版本，利于项目包版本统一、项目版本统一升级，降低所用包版本迭代快的影响。</p>

<p>item-common:<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 这个就不用多介绍了，你认为你每个项目都用的到的，就可以扔进去，包括JAR包。</p>

<p>euerka-center:<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 这就是一个Spring cloud 中组件之一的euerka，实现了高可用与服务验证。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 具体介绍：<a href="https://my.oschina.net/bianxin/blog/1819947" target="_blank">https://my.oschina.net/bianxin/blog/1819947</a></p>

<p>user-center:<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 用户中心：我定义为一个生产者，为别的服务提供用户信息查询等接口。</p>

<p>deal-center:<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 交易中心：我定义为一个消费者，实现了用Feign以ApachHttpclient发送请求的方式，消费了由用户中心提供的接口。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 具体介绍：<a href="https://my.oschina.net/bianxin/blog/1821684" target="_blank">https://my.oschina.net/bianxin/blog/1821684</a></p>

<p>user-center结构说明：<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;user-api：打包方式（jar），包含实体类，与提供的Feign请求的接口。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; user-biz：打包方式（jar），包含用户相关的数据层，与逻辑处理。<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;user-web：打包方式（jar），包含用户相关的接口，与页面逻辑处理。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; user-biz引入user-api，user-web引入user-biz。</p>

<p>deal-center结构说明：<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 基本与user-center一样，因为要消费user-center中的接口，在deal-web的maven配置中引入user-api就好。</p><br/>

#### 架构实现
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; **单个工程：<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.实现swagger的API接口文档<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.整合pagehelper实现便捷分页<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.实现了高可维护型的自然模板（Java控制页面跳转）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 整体架构：<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.实现服务注册中心集群和单机版<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.实现了Java端（脱离传统nginx负载均衡的）负载均衡及根据响应时间自动分配。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.实现以Feign访问服务请求，并改底层为Apache的httpclient可获取连接池、超时时间等与性能息息相关的控制能力。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;后续考虑使用模板技术，实现自动生成三层代码，减少工作量，避免不必要的错误。** 

