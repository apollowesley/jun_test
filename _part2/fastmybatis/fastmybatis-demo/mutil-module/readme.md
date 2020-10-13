# 多模块示例

演示多模块示例，mapper在demo-common中，启动在demo-web中

注意：不能用`@MapperScan`，直接在配置文件中指定`mybatis.base-package`即可

```properties
# 指定mapper类，多个用;隔开
mybatis.base-package=com.demo.common.mapper
# 指定配置文件
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
```
