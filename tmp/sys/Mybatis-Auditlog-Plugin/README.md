# Mybatis-Auditlog-Plugin (mapper数据修改监控插件)
<br/>
Mybatis-Auditlog-Plugin 数据修改日志插件对各种mapper的增删改进行监控，且仅需升级最新版本的Druid库并作少量更改即可支持大部分的数据库（目前版本支持MySQL）。

本插件通过拦截器记录Mybatis下所有Mapper类在进行增删改动作时所影响数据的 oldValue（修改前，插入前，删除前），newValue（修改后，插入后，删除后），并将此类数据日志插入到`audit_log`数据审计表中，方便运维人员进行数据复原。


#### 使用说明

完成如下两个步骤后，当启动基于mybatis的项目时，将会自动建立数据监控表，监控并记录所有由mapper引起的数据变动。

pom.xml引入
```
     <dependency>
         <groupId>com.gitee.lopssh</groupId>
         <artifactId>mybatis-auditlog-plugin</artifactId>
         <version>1.0.0</version>
     </dependency>
```

mybatis-config.xml加入

```
    <plugins>
        <plugin interceptor="com.gitee.lopssh.plugin.mybatis.auditlog.interceptor.SQLAuditLogInterceptor">
            <!-- 是否启用，默认值: true -->
            <property name="enable" value="true"/>
            <!-- 是否进行自动分表，默认值: true -->
            <property name="split" value="true"/>  
            <!-- 不分表时的表名，表不存在将会自动新建，默认值: audit_log -->
            <property name="defaultTableName" value="audit_log"/>  
            <!-- 分表时的表名前缀，每月自动新建一张，生成规则 前缀名+年月，默认值: audit_log_ -->
            <property name="preTableName" value="audit_log_"/>
            <!-- 指定用于获取当前登录用户ID的方法，#号之前是类的全限定名称，#号之后是静态方法的名称，返回int类型，如不需要记录用户ID可删除此配置项 -->  
            <property name="clerkIdMethod" value="com.test.utils.ClerkIdGetter#current"/>
        </plugin>
    </plugins>
```


#### 测试示例

![输入图片说明](https://images.gitee.com/uploads/images/2018/0820/202016_5ae56bc5_1478767.png "屏幕截图.png")

#### 数据审计记录表（audit_log / audit_log_日期）的字段介绍
```
> ID -- 本记录ID
 
> TableName -- 记录下的表名
 
> ColumnName -- 记录下的列名
 
> PrimaryKey -- 记录下的主键名
 
> ParentID -- 父记录ID （例如：insert、delete、update语句 从第二个字段开始 会以第一个字段在本表的ID作为ParentID，以便于SQL分组）
 
> NewValue -- 该字段的新值
 
> OldValue -- 该字段的旧值
 
> Operation -- 记录下的动作（目前有：update、insert、delete）
 
> CreateTime -- 记录的创建时间

> CreateClerk -- 操作人员ID（本例子默认为 -1）