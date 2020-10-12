# operation标签
operation为table的子标签，与resultMap标签同级，MyBatis的一条SQL对应于一个opertation标签。mydalgen会根据每一个operation配置生成对应的Mapper配置以及DAO接口		

# Required Attributes
| 属性名 | 描述 |
|----|---|
| name|在命名空间中唯一的标识符|


# Optional Attributes
| 属性名 | 描述 |
|----|---|
| multiplicity|返回SQL执行响应结果数据个数(one/many)，仅`select`操作时该参数有效|
| paramType|生成的DAO接口入参类型(object/primitive)。object，构建出的DAO接口入参为当前表对应的DO对象；primitive，构建出的DAO接口入参为operation中SQL语句设置的所有变量。**若operation定义了[extraparams](element-extraparams.md)子标签,则无需设置该属性**|
|resultType|执行`select`操作从这条语句中返回的期望类型的类的完全限定名或别名。注意如果是集合情形，那应该是集合可以包含的类型，而不能是集合本身。使用 resultType 或 resultMap，但不能同时使用|
|resultMap|外部 resultMap 的命名引用，取值为标签[`table/resultMap`](element-resultMap.md)的属性`name`值。结果集的映射是mydalgen 最强大的特性，对其有一个很好的理解的话，许多复杂映射的情形都能迎刃而解。使用 resultMap 或 resultType，但不能同时使用|
# Child Elements
- [&lt;extraparams&gt;](element-extraparams.md) (0 or 1)
- [&lt;sql&gt;](element-sql.md) (1 Required)
- [&lt;sqlmap&gt;](element-sqlmap.md) (1 Required)

# 示例		
```
<?xml version="1.0" encoding="UTF-8"?>
<table xmlns="http://smartboot.oschina.io/schema/mydalgen/table" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xsi:schemaLocation="http://smartboot.oschina.io/schema/mydalgen/table 
    http://smartboot.oschina.io/schema/mydalgen/table.xsd
    sqlname="uc_user_info">
    <opertation name="queryInfo">
        ***
    </operation>
</table> 
```