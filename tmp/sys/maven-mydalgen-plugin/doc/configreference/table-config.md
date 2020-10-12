---
title: table-config.xml配置
category: 用户指南
order: 2
---

mydalgen插件的运行是通过读取配置文件`tables-config.xml`来进行**Mapper XML、DO、DAO**的构建。该文件存在于mydalgen中的角色类似于JSP/Servlet的web.xml文件，或者是Spring MVC中的application.xml。示例：

    <?xml version="1.0" encoding="UTF-8"?>
    <tables xmlns="http://smartboot.oschina.io/schema/mydalgen/config" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://smartboot.oschina.io/schema/mydalgen/config
    http://smartboot.oschina.io/schema/mydalgen/config.xsd">

        <tableprefix prefix="uc"/>

        <typemap from="java.sql.Date" to="java.util.Date" />
        <typemap from="java.sql.Timestamp" to="java.util.Date" />
        <typemap from="java.math.BigDecimal" to="java.lang.Long" />
        <typemap from="java.lang.Byte" to="java.lang.Integer" />
        <typemap from="java.lang.Short" to="java.lang.Integer" />
    
        <include table="tables/table1.xml" />
        <include table="tables/table2.xml" />
    </tables>

- tableprefix:  
表名前缀，非必须项，但最多只能设置一个该标签。
仅在设计的表名包含业务系统标识前缀时需要设置tableprefix标签。例如我们设计的一系列表名：`xx_table1`、`xx_table2`、`xx_table3`，则需要在table-config.xml文件中定义`<tableprefix prefix="xx"/>`
- typemap：	
维护数据库表字段类型与Java类中的变量类型转换关系，该标签只有`from`与`to`两个属性。例如：`<typemap from="java.sql.Date" to="java.util.Date" />`，意味着在构建DO类时，将数据库驱动识别出的`java.sql.Date`类型的表字段，在DO类中定义为`java.util.Date`类型。    
目前已支持的类型：
    - java.sql.Date
    - java.sql.Timestamp
    - java.math.BigDecimal
    - java.util.Date
    - java.lang.Double
    - java.lang.Byte
    - java.lang.Short
    - java.lang.Integer
    - java.lang.Long
- include：
导入开发人员编写的用于构建代码的模板文件，采用的是相对路径。例如我们的mydalgen资源文件存放于`src/main/resource/META-INF`目录下，且`tables-config.xml`的配置为`<include table="tables/table1.xml" />`，则表示模板文件`table1.xml`是存放于目录`src/main/resource/META-INF/tables`下的。