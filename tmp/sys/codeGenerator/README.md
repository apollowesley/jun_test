### # codeGenerator

利用  freemaker 模板生成Java工程
Java 生成  domain dao service  层文件
 **这里是列表文本
### 注意：尚未完工*
    --1. 这里是列表文本完成程度:可以读取MySQL字段生成实体类 、mapper文件支持 update*、insert、slect *
     数据库配、包名、Dao后缀、置可以配置化 --config.properties
    --2.未完成 dao  mapper  service(基本功能) 公共模板待完善
    spring 配置文件、mybatis配置文件模板尚未添加
             
### 问题 
     entity实体类 --》数据库字段类型与java字段类型映射 (已解决)  
     现在只支持mysql 其他数据库尚未扩展，目标兼容oracle;
     
##  使用：
    配置 config文件
    运行test 
    生成文件现在本工程下generator-out
     

 