# 更新日志

## 1.8.8

- 删除没用的文件

## 1.8.7

- Condition注解新增ignoreEmptyString属性

## 1.8.6

- 修复dom4j读取xml超时BUG

## 1.8.5

- 修复mybatis文件内容为空产生<mapper/>问题

## 1.8.4

- 修复saveIgnoreNull不更新时间问题

## 1.8.2

- 【优化】一些类实现Serialable接口，支持dubbo序列化

## 1.8.1
- 【新增】Query类新增几个便捷方法.

## 1.8.0
- 【优化】调整pom依赖

## 1.7.1

- 【修复】修复BaseParam返回条件BUG

## 1.7.0

- 【新增】新增逻辑删除功能 [doc](http://durcframework.gitee.io/easymybatis-doc/#904)
- 【修复】修复模板占位符问题

## 1.6.1

- 【修复】@GeneratedValue注解空指针问题
- 【增强】FillHandler功能增强
- 【新增】QueryUtils新增方法query(SchDao<Entity> schDao,Object bean)

## 1.6.0

- 【新增】新增乐观锁。[doc](http://durcframework.gitee.io/easymybatis-doc/#903) 
- 【新增】新增全局Dao。[doc](http://durcframework.gitee.io/easymybatis-doc/#902)
- 【新增】新增根据普通bean查询。[doc](http://durcframework.gitee.io/easymybatis-doc/#90206)
- 【新增】新增@Condition注解。[doc](http://durcframework.gitee.io/easymybatis-doc/#90207)

## 1.5.1

- 【修复】templateClasspath读取问题

## 1.5.0

- 【新增】支持PostgreSQL、SQLite
- 【修复】@Transient注解BUG

## 1.3.1

- 【新增】springboot-starter支持，搭建项目更加方便快捷。[doc](http://durcframework.gitee.io/easymybatis-doc/#2)

## 1.2.3

- 【新增】字段填充功能。[#IFAUM](https://gitee.com/durcframework/easymybatis/issues/IFAUM) [doc](http://durcframework.gitee.io/easymybatis-doc/#5)
- 【修复】模板问题

## 1.2.2

- 【优化】启动速度。[#IF8NF](https://gitee.com/durcframework/easymybatis/issues/IF8NF)
- 【新增】mapperSaveDir配置：指定mapper文件存放路径。[doc](http://durcframework.gitee.io/easymybatis-doc/#8)

## 1.2.0

- 【新增】支持枚举类型字段。javaBean中可以定义枚举类型的字段了。 [doc](http://durcframework.gitee.io/easymybatis-doc/#6)

