#关注插件
收藏、赞、顶、踩、关注等实现的通用功能，可以根据实际业务需求定义各种类型
> [java doc](http://api.ms.mingsoft.net/mattention)

#maven依赖方式使用
```
<!--war包-->
<dependency>
  <groupId>net.mingsoft</groupId>
  <artifactId>ms-mattention</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <type>war</type>
</dependency>

<!--源代码-->
<dependency>
  <groupId>net.mingsoft</groupId>
  <artifactId>ms-mattention</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <classifier>sources</classifier>
  <scope>provided</scope>
</dependency>

<!--jar包依赖方便业务开发调用-->
<dependency>
  <groupId>net.mingsoft</groupId>
  <artifactId>ms-mattention</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <classifier>classes</classifier>
  <scope>provided</scope>
</dependency>

```
#源码方式使用
直接将在MStore下载的源代码解压覆盖到项目中，编译并运行