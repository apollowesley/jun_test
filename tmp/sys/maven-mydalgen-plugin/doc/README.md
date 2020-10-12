---
title: Welcome
---

# 开篇
1. 什么是**maven-mydalgen-plugin**?	
顾名思义，这是一款maven插件，通常我们简称之为mydalgen，而mydalgen又寓意着Mybatis+DAL+Generator。因此，这是一款集成Mybatis自动生成DAL层代码、配置文件的Maven插件。
2. 为什么开发**maven-mydalgen-plugin**？	
因为懒！懒！懒！重要事情说三遍。从事开发行业的初衷正是为了研发出能为人们的生活、工作带来便利的产品，而作为程序猿应该首先享受程序能赠予你偷懒的权利。软件程序员的工作中充满了大量重复性的工作，懒性正为此而来，因此才会想办法避免去写无聊重复的代码——因此避免的代码的冗余，消减了维护的成本，使重构变得容易。最终，这些由于懒惰激发出的动力而开发出的工具和最佳编程实践方法提升了产品的质量。
3. 为什么选择DAL层？	
因为DAL层作为数据访问层，没有过多的业务逻辑，操作行为更加单一(CRUD)，这样的一个场景特别适合通过工具化的形式来构建该层代码。


# DAL层现状
在DAL层的开发中MyBatis是一款极具代表性的框架，不仅支持定制化SQL、存储过程以及高级映射，还避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集，并可以对配置和原生Map使用简单的XML或注解，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。

MyBatis在一定程度上提升了程序猿的编码效率与质量，但随着业务的逐渐复杂需要投入不少的时间去维护Mapper XML配置以及相同风格的POJO类或者DAO接口。这是一项令程序猿相当反感的工作，为了从中解脱出来，我们开发了mydalgen插件。开发人员只需关注实际业务所对应的SQL语句，通过插件自动生成MyBatis的Mapper XML配置、POJO类、DAO接口。

通过以下简短的视频来感受mydalgen的魅力：

{% video %}http://on1sjifdn.bkt.clouddn.com/mydalgen/welcome.mp4{% endvideo %}

> 该项目并非是从零开始，而是在开源项目[https://github.com/codehaus/middlegen3](https://github.com/codehaus/middlegen3 "middlegen3") 上扩展的一款maven插件，在完成模板文件编辑后可执行命令`mvn mydalgen:mybatis `进行代码的构建

# 环境依赖
1. 启用了注解的Spring Maven工程
2. MySQL 5+

# 名词解释
- DO(Data Object)	
数据对象，数据库表映射出的java对象，字段名、字段类型都相互匹配。一个DO对象就是对应数据库中某张表的一条记录。
- DAO(Data Access Object)	
数据访问对象，作为业务逻辑与数据库资源的中间层，实现对数据库的操作方法。通常DAO是与PO(Persistant Object)结合使用的，但为了有更高的辨识度，我们将PO重新定义为DO(Data Object)。

# 源码下载
maven-mydalgen-plugin已开源至码云，欢迎前往下载。

- 源码地址：[maven-mydalgen-plugin](http://git.oschina.net/smartboot/maven-mydalgen-plugin)
- 资源文件：[mydalgen-resource.tar](http://git.oschina.net/smartboot/maven-mydalgen-plugin/attach_files)


#目录结构
通过上一章节大致了解了一个集成了mydalgen插件的工程目录结构为：

    -project-dir  
      +-src/main/resource/META-INF  
      |   +-tables  
	  |   | +-*.xml  
	  |   +-tempates  
	  |   | +-mergedir  
	  |   | +-*.vm
	  |   +-tables-config.xml
      +-pom.xml 

- `pom.xml`接入mydalgen插件进行参数配置，参见《[快速集成](/getting-started-on/getting-started-on.md)》章节  
- `META-INF/tables-config.xml` mydalgen主配置文件    
- `META-INF/tables`是由**用户创建**并用于存放**[mydalgen模板文件](configreference/mydalgen-template.md)**的目录。
- `META-INF/templates`存放velocity文件，如果对于插件自动生成的代码有定制化需求，可修改该目录下的文件。

#执行流程
![mydalgen工作流程](/assets/images/mydalgen.png)
1. 执行`mvn mydalgen:mybatis`运行插件。
2. 加载pom.xml、tables-config.xml配置信息
3. 解析开发人员编写的模板文件(/META-INFO/tables/*.xml)
4. 对接数据库，获取涉及到的所有表结构信息(字段名、类型)
5. 将**步骤4**获取到的数据，通过Velocity渲染输出Java、Mapper文件至指定目录下

# 关于作者
Edit By [Seer](http://git.oschina.net/smartboot)  
E-mail:[zhengjunweimail@163.com](mailto:zhengjunweimail@163.com)  
QQ:504166636