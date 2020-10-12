### 一、项目简介
1. 基于关系型数据库和freemarker模板生成代码，不限制开发语言，因为只是一个代码生成器。
1. 理论上兼容PostgreSQL、MySql、SqlServer、Oracle。 只在PostgreSQL测试过，其他数据库暂未测试。
1. 提供的类型映射配置文件只有PostgreSQL+java的：config/mappings-postgresql-java.xml，其他数据库和开发语言需要自己修改类型映射文件。
1. 原来一直使用的rapid-generator，不过由于rapid-generator早已不维护，而且不能够满足需求，所以基于Spring Boot和Freemarker仿造了个轮子并进行强化。    
1. 基本功能和用法和rapid-generator一致。 原来使用过rapid-generator的可以很容易迁移过来。
1. 只是一个代码生成器，并非一站式后台管理系统框架生成器。可以根据自定义模板生成任意格式代码。
1. 代码生成器只是个工具，重要的是整理适合自己的模板。就像是我给你个机床，如果您有图纸可以造出一艘航空母舰，如果什么也没用，可能一个螺丝钉也造不出来。
1. 演示模板只是为了演示标签的基本用法，生成的代码不能真正使用，看看就好。
1. 暂时没有UI界面，应该不是很需要。
1. 项目是基于Eclipse + Gradle的，用IDEA和Maven的自行调整下。

### 二、特别鸣谢
1. 感谢 hutool 已经在多个项目中使用[hutool](https://hutool.cn/) 
1. 感谢 rapid-generator   
1. 感谢 图灵谷（北京）科技有限公司  
1. 感谢 各种TV

### 三、文件目录说明
| 序号 | 文件/目录                            | 说明                                                                                           |
| ---- | ----------------------------------- | --------------------------------------------------------------------------------------------- |                                                                   
| 1    | config/application.yml              | 参数配置文件，具体配置看相关注释                                                                  |
| 2    | config/mappings-postgresql-java.xml | 类型映射文件，可以根据自己的数据库类型和开发语言定义新的，并在application.yml中设置mappingsFileName    |
| 3    | jre                                 | 暂时没有提供，可以自己将jre放在下面方便运行在没有安装jdk和jre的电脑上                                 |
| 4    | logs/output.log                     | 输出日志文件                                                                                    |
| 5    | output                              | 代码输出目录                                                                                    |
| 6    | target/tg-common-generator.jar      | 可运行的项目jar文件                                                                              |
| 7    | templates                           | 模板目录，可以存放多套模板，并在application.yml中设置templateName选择使用哪套模板                     |
| 8    | startup.bat                         | 启动bat，双击运行                                                                                |
| 9    | startupWithJre.bat                  | 启动包含jar的版本，参考上面的jre说明                                                               |

### 四、使用说明  
1. 将distFiles（发布文件）拷贝到电脑上。
1. 将自己的模板放置在templates下，如templates/tg-test。 使用了freemarker模板引擎，模板文件后缀都为".ftl"。具体语法可以参考freemarker官方文档和 templates的demo。  
1. 根据自己的数据库类型和开发语言修改：config/mappings-xxxxxx.xml类型映射文件。并在application.yml中设置mappingsFileName。可以修改application.yml中showDetailLogs以便查看sql类型。
1. 修改config/application.yml 配置文件，参考 “参数配置” 部分。    
1. 双击startup.bat运行。 
 

### 五、参数配置
config目录下application.yml文件   
| 序号 | 是否必填 | 参数               | 名称             | 默认值                     |说明                                                                 |
| ---- | -------- | ----------------- | --------------- | ------------------------- | -------------------------------------------------------------------- |
| 1    | 是       | templateName      | 模板名称         | 无                        | 模板目录下可能有多个模板，需要指定要用的模板名称                          |
| 2    | 是       | mappingsFileName  | 类型映射配置文件名| 无                        | config下的 类型映射配置文件名，可以参考mappings-postgresql-java.xml命名  |                    
| 3    | 是       | defultFieldType   | 默认类型         | String                    | 当没有找到匹配的类似时，返回的默认类型名                                 |
| 4    | 是       | showDetailLogs    | 是否显示详细日志  | false                     | 可以改为true， 查看sql类型名称。以便编辑自定义的类型映射配置文件           |
| 5    | 是       | projectName       | 项目名称         | 无                        | 项目名称，如：tg-demo                                                  |
| 6    | 是       | basePackage       | 包名             | 无                        | 包名，如：com.turingoal.demo                                          |
| 7    | 是       | datasourceUrl     | 数据库连接Url    | 无                         | 数据库连接Url，如：jdbc:postgresql://192.168.1.8:5432/tg-demo         |
| 8    | 是       | datasourceUsername| 数据库连接用户名  | 无                        | 数据库连接用户名，如：user                                              |
| 9    | 是       | datasourcePassword| 数据库连接密码    | 无                        | 数据库连接密码，如：132456                                              |
| 10   | 否       | projectTitle      | 项目标题         | 无                        | 如：图灵谷demo项目                                                      |
| 11   | 否       | projectTitleShort | 项目标题缩写      | 无                        | 如：图灵谷demo                                                         |
| 12   | 否       | prefixsNeedRemove | 需要移除的表前缀  | 无                        | 英文逗号分隔，如：tg_c_,tg_m_                                           |
| 13   | 否       | ignoreTables      | 需要忽略的表      | 无                        | 英文逗号分隔，如：tg_c_user,tg_c_role                                   |
| 14   | 否       | schema            | 数据库schema     | public                    | 默认为 public                                                          |
| 15   | 否       | templateBaseDir   | 模板目录         | jar同级目录下的templates   | 下面可能有多套模板。 默认为项目jar同级目录下的templat                      |
| 16   | 否       | outputBaseDir     | 输出目录         | jar同级目录下的output      | 默认为项目jar同级目录下的output                                          |

### 六、标签说明
**1、全局标签（可用于目录名、文件名、文件内）** 
| 序号 | 类型       | 标签                   | 说明     |
| ---- | ---------- | ---------------------- | -------- |
| 1    | 全局标签   | ${projectName}             | 项目名称     |
| 2    | 全局标签   | ${projectTitle}            | 项目标题     |
| 3    | 全局标签   | ${projectTitleShort}       | 项目标题缩写 |
| 4    | 全局标签   | ${projectDesc}             | 项目描述 |
| 5    | 全局标签   | ${basePackage}             | 包名 |
| 6    | 全局标签   | ${datasourceUrl}           | 数据库连接Url |
| 7    | 全局标签   | ${datasourceUsername}      | 数据库连接用户名 |
| 8    | 全局标签   | ${datasourcePassword}      | 数据库连接密码 |
| 9    | 全局标签   | ${schema}                  | 数据库schema |   

**2、目录名标签（只可用于目录名）**  
| 序号 | 类型       | 标签                        | 说明     |
| ---- | ---------- | --------------------------| -------- |
| 1    | 目录名标签 | ${basePackage_dir}         | 目录名：包名 |
| 2    | 目录名标签 | ${tableName_dir}           | 目录名：表名|
| 3    | 目录名标签 | ${className_dir}           | 目录名：类名 |
| 4    | 目录名标签 | ${classNameFirstLower_dir} | 目录名：类名首字母小写 |    

**3、文件名标签（只可用于文件名）**    
| 序号 | 类型       | 标签                       | 说明     |
| ---- | ---------- | --------------------------| -------- |
| 1    | 文件名标签 | ${tableName}               | 文件名：表名 |
| 2    | 文件名标签 | ${className}               | 文件名：类名 |
| 3    | 文件名标签 | ${classNameFirstLower}     | 文件名：类名首字母小写 |    

**4、文件内表标签（只可用于文件内）**    
| 序号 | 类型       | 标签                   | 说明     |
| ---- | ---------- | ---------------------- | -------- |
| 1    | 文件内表标签 | ${table.tableName}           | 表名  |
| 2    | 文件内表标签 | ${table.className}           | 类名  |
| 3    | 文件内表标签 | ${table.classNameFirstLower} | 类名首字母小写  |
| 4    | 文件内表标签 | ${table.remarks}             | 表备注  |
| 5    | 文件内列标签 | ${column.columnName}          | 列名  |
| 6    | 文件内列标签 | ${column.fieldName}           | 字段名  |
| 7    | 文件内列标签 | ${column.fieldNameFirstLower} | 字段名首字母小写  |
| 8    | 文件内列标签 | ${column.remarks}             | 列备注  |
| 9    | 文件内列标签 | ${column.possibleType}        | 类型，映射文件里配置的，如果没有选用默认类型  |