#通过iutils快速搭建项目
1、新建模块，默认包 cn.iutils.demo，如要扩展另外的包，请同步修改spring的配置文件，
spring-config.xml和spring-mvc.xml。将这两个文件从源码包中拷贝到项目的resources资源目录下
然后，修改文件中的spring扫描配置
```
spring-config.xml：base-package="cn.iutils" 加入自己的包，如： base-package="cn.iutils,com.company"
```
```
spring-config.xml：<property name="basePackage" value="cn.iutils"/> 加入自己的包，如： <property name="basePackage" value="cn.iutils,com.company"/>
```
```
spring-mvc.xml：base-package="cn.iutils.**.controller" 加入自己的包，如：base-package="cn.iutils.**.controller,com.company.**.controller"
```

2、新建子模块，以demo模块为例，一个模块中的代码怎么分类，
```
cn.iutils.demo --包名文件
    controller --控制器
    dao --数据持久化
    entity --实体
    service --服务
```

```
resources --配置文件
    mappings/demo/ --sql存放文件目录
```

```
webapp --页面
    WEB-INF/view/demo/子模块（存放子模块页面）
```

3、怎样修改基础平台原来的页面或代码？
   在自己的项目中，新建和基础平台相同的包名或文件名，然后将基础平台的页面或代码文件拷贝到自己的项目中，直接修改拷贝的文件即可。

4、怎样新增数据库表，有什么规则？
   主键：固定用“id”命名，类型bigint，自动增长
   建议每个业务表都加上，
    create_by（创建人）、create_date（创建时间）、update_by（修改人）、update_date（修改时间）、remarks（备注）、
   status（状态）。
   然后在根据自己的业务自行扩展字段。

5、新建表后，运行代码生成工具，将模块的代码自动生成，然后拷贝带项目中。

6、iutils平台引用
```
<!-- 引入iutils库 -->
<dependency>
     <groupId>cn.iutils</groupId>
     <artifactId>iutils-admin</artifactId>
     <version>2.0-SNAPSHOT</version>
     <classifier>lib</classifier>
</dependency>
<dependency>
    <groupId>cn.iutils</groupId>
    <artifactId>iutils-admin</artifactId>
    <version>2.0-SNAPSHOT</version>
    <type>war</type>
</dependency>
```

```
<!-- 引入iutils插件 -->
<!-- 评论插件 -->
<dependency>
    <groupId>cn.iutils</groupId>
    <artifactId>iutils-comment</artifactId>
    <version>2.0-SNAPSHOT</version>
    <type>war</type>
</dependency>
<!-- 留言板插件 -->
<dependency>
    <groupId>cn.iutils</groupId>
    <artifactId>iutils-guestbook</artifactId>
    <version>2.0-SNAPSHOT</version>
    <type>war</type>
</dependency>
<!-- 顶踩插件 -->
<dependency>
     <groupId>cn.iutils</groupId>
     <artifactId>iutils-updown</artifactId>
     <version>2.0-SNAPSHOT</version>
     <type>war</type>
</dependency>
```

注意：详细开发文档请看[快速搭建第一个项目的文档](http://iutils.cn/f/39/100/detail.html)，感谢大家的支持！QQ交流群：460229964