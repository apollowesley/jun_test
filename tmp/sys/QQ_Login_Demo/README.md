# QQ_Login_Demo

## qq sdk登陆改造主要内容
 &nbsp;&nbsp;&nbsp;&nbsp;由于官方提供的是比较老板的项目（2012年eclipse项目demo），由于个人使用的
 开发工具为IDEA，所以希望整体的项目代码结构调整为maven结构，方便各种开发工具直接导入使用。

## 首先来了解一下auth2原理登陆

![输入图片说明](https://gitee.com/uploads/images/2018/0330/160025_9a0663db_1387578.png "屏幕截图.png")
 
 _**主要调整内容有如下**_
 
 - 将sdk4j.jar包打包到本地仓库
 
 > mvn install:install-file -DgroupId=cn.kiwipeach -DartifactId=qq-sdk4j -Dversion=1.0.0 -Dpackaging=jar -Dfile=Sdk4J.jar
 
 - 添加项目依赖
```xml
         <dependency>
             <groupId>cn.kiwipeach</groupId>
             <artifactId>qq-sdk4j</artifactId>
             <version>${qq.sdk4j.version}</version>
         </dependency>
         <dependency>
             <groupId>javax.servlet</groupId>
             <artifactId>javax.servlet-api</artifactId>
             <version>${servlet.version}</version>
             <scope>provided</scope>
         </dependency>
         <dependency>
             <groupId>taglibs</groupId>
             <artifactId>standard</artifactId>
             <version>${tglib.version}</version>
         </dependency>
         <dependency>
             <groupId>javax.servlet</groupId>
             <artifactId>jstl</artifactId>
             <version>${jstl.version}</version>
         </dependency>
```

 
 - 将官方案例改造后的目录结构

![输入图片说明](https://gitee.com/uploads/images/2018/0331/114544_f100fe0c_1387578.png "屏幕截图.png")


 
- 官网案例运行截图
![输入图片说明](https://gitee.com/uploads/images/2018/0330/155646_b3bda2bc_1387578.png "屏幕截图.png")

- 改造后运行demo图

![输入图片说明](https://gitee.com/uploads/images/2018/0331/114630_4eb46ab0_1387578.gif "QQ登录demo演示.gif")
