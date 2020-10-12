# 项目说明    
- 一款基于springboot的快速开发模块化脚手架，采用springboot2.2.2、activiti6、spring、SpringMvc、mybatis-plus、redis、swagger、thymeleaf、layui技术开发;使用mysql数据源，实现功能有用户中心模块：平台用户、验证码查询、登录日志;系统设置模块：角色权限、菜单管理、接口管理,本项目会一直维护,添加新功能和修复bug,使得项目更加完善。  

# 团队成员  
[handy](http://gitee.com/handy-git)    

# 演示地址  
[www.ljxmc.top](http://www.ljxmc.top)    
帐号:18512345671 密码Aa123456,请善待开源公共资源  

# 项目架构 [项目架构图在线链接(全)](https://www.processon.com/view/link/5d707ed0e4b08987f5509ca5)
![项目架构图](https://images.gitee.com/uploads/images/2019/0905/152325_8eb8c398_1604115.jpeg "1567668185(1).jpg")
 
# 目前功能说明
-  **用户中心** 
    1. 平台用户
    2. 验证码查询
    3. 登录日志
-  **系统设置** 
    1. 角色权限
    2. 菜单管理
    3. 接口管理
    4. 定时任务
-  **工作流程** 
    1. 流程列表
    2. 模型列表
    3. 发起流程
    4. 待办任务
# 技术
|  类型   |   名称  |   版本  |  链接   |
| ---  | --- | --- | --- |
|  java  |  jdk   |  1.8   |     |
|  核心框架|   spring boot  |  2.2.2   |  [spring boot官网](https://spring.io/projects/spring-boot/ )  |
|  视图框架|   spring mvc  |     |  |
|  持久层框架   |   mybatis-plus  |  3.1.2   |   [mybatis-plus官网](https://mp.baomidou.com/)  |
|  接口文档   |  swagger   |  2.9.2   |     |
|  接口文档 页面   |  swagger-bootstrap-ui|  1.9.5|     |
|  模板引擎   |   thymeleaf  |    |     |
|  前端页面   |   layui|  2.5.4   |  [layui官网](https://www.layui.com/)   |
|  前端页面   |   layuimini|   |  [layuimini链接](https://gitee.com/zhongshaofa/layuimini)   |
|  数据库   |   mysql|  5.7   |     |
|   工具  |   lombok|     |     |
|  oss对象存储   |   oss|  3.5.0   |   aliyun购买oss  |
|  模版工具   |   poi-tl|   1.5.0  |  [poi-tl官网](http://deepoove.com/poi-tl/ )  |
|  工作流|   activiti|   6.0.0  |  [activiti官网](https://www.activiti.org/)  |
|  缓存|   redis|    |   |

# 阿里云代金券
- 本项目大量使用阿里云产品:演示版的 服务器,数据库,oss储存,短信服务,如果想自己搭建起来的话可以领取下面代金券优惠购买
- 建议注册个 新账户购买 ，享受2折 ，如果购买多个产品，可以先放到 购物车 再一键购买
- [阿里云代金券2000元,点击即可获取](https://promotion.aliyun.com/ntms/yunparter/invite.html?userCode=zdax7tgo)

# 部署说明
- Rapid-Boot是使用Maven构建的多模块项目，分模块开发，各模块可插拔。web项目是Rapid-Boot的主入口，在web的pom文件中引入需要的模块之后，通过以下步骤来启动项目：

一. 导入数据库
项目下有数据库脚本rapid-boot.sql，首先导入数据。

二. 修改相关配置
修改rapid-boot项目下pom文件的properties将其中的数据库配置和其他配置修改为自己的数据库

三. 启动项目
1. idea:本项目为Springboot项目启动,直接子项目web即可
2. 直接使用maven把该项目整体install打包,把其中的web项目打包成jar,使用java -jar web.jar启动

# 备注
- 如果您有疑问,你可以评论回复。
- 如果rapid-boot对您有一点帮助，您可以点个star，就是对作者最大的支持了。
- rapid-boot会继续更新下去，努力成为一款快速上手又非常好用的脚手架。

# 效果图
![输入图片说明](https://images.gitee.com/uploads/images/2019/0910/121313_7fc17c46_1604115.jpeg "1.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0910/121322_143a00cb_1604115.jpeg "2.jpg")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0910/121330_4b6932cc_1604115.png "3.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0910/121338_c3b05ede_1604115.png "4.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0910/121347_302f9363_1604115.png "5.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0910/121355_61de010f_1604115.png "6.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0910/121403_29bd15f2_1604115.png "7.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0910/121532_7a7120a6_1604115.png "8.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0910/121540_f6063d85_1604115.png "9.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0910/121548_ee131772_1604115.png "10.png")
