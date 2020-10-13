![输入图片说明](https://gitee.com/uploads/images/2018/0607/134835_3a2f7e0a_639234.png "hammer_64px_1175591_easyicon.net.png")

# 项目说明
- iartisan-admin-template是Java快速开发平台，可以快速构建后台项目

- 基于shiro已经集成权限功能，集成iartisan-runtime解决了环境依赖问题

- 集成基于db的代码生成器 iartisan-generator-plugin，dao层代码一键生成

**only care 业务代码，俨然一个接私活利器**

# 底层实现功能
- 用户管理
- 菜单管理
- 权限管理
- 操作日志管理
- druid监控

 
# 技术选型
## 后台
- spring-boot
- shiro
- sitemesh
- mybatis-plus

## 前端
- layui
- layuiCMS2.0 

## 演示地址
- http://140.143.246.200:8081
- 用户名/密码  admin  /  admin

### 1.项目部署

- 执行doc/sql下的sql脚本

- 修改数据库连接配置，找到文件env.properties 
  修改配置项：`jdbc.url，jdbc.username，jdbc.password` env 文件支持外置

- 运行类：WebBootRun.java

- 访问：http://localhost:8080/login 当然端口是可改的，需要在env.properties
  文件添加配置项：`server.port`

### 效果图
![输入图片说明](https://gitee.com/uploads/images/2018/0417/160151_a92b142e_639234.png "image.png")

![输入图片说明](https://gitee.com/uploads/images/2018/0417/160336_02b3aab2_639234.png "image2.png")

![输入图片说明](https://gitee.com/uploads/images/2018/0417/160355_850ceadf_639234.png "image3.png")

![输入图片说明](https://gitee.com/uploads/images/2018/0417/160414_a92c8f64_639234.png "image4.png")

![输入图片说明](https://gitee.com/uploads/images/2018/0502/104128_53a1d51d_639234.jpeg "image.jpg")

![输入图片说明](https://images.gitee.com/uploads/images/2018/0706/145544_ce1a635f_639234.jpeg "menu.saveimg.savepath20180706145511.jpg")
### todo

- activiti 工作流