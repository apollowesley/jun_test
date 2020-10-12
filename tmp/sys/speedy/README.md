# speedy 管理系统
<h2>示例地址</h2>
<a href="http://www.luoqy.com/common/login">speedy管理系统</a>

<h2>开发环境描述</h2>
开发环境JAVA，JDK1.85 
开发架构框架 springboot+Mybatis 
后台页面框架:HTML+JavaScript+Jquery+Bootstrap 
大数据微管理平台
数据库为MySql，speedy_ 开头的表为系统表，不可随意修改
<h2>项目描述</h2>
对简单类型网站的快速开发，模块化，简要化
<p>备注</p>
<p>1.搜索功能的增加  OK</p>
<p>2.个人信息的修改  OK</p>
<p>3.数据统计面板     </p>
<p>4.短信50% 支付 对接 </p>
<p>5.让模块具有独特性和speedy的自我特色，独特的UI</p>
<p>6.资源的统一管理，以命名文件形式为"名_表"的形式进行存储，以便于管理</p>
## 介绍
<p>1.springboot 基础框架结构</p>
<p>2.mybatis-Generate 后台代码生成器</p>
<p>3.freemarker thymeleaf 前端静态页面配置</p>
<p>注：目前仅支持Mysql数据源（其他数据源需自行配置修改）</p>

## 目录结构
```
speedy
├─src
│   ├─main
│   │   ├─java 代码文件存放
│   │	│	├─common 存放公共使用目录
│	│	│	│	├─CodeGenerate 代码生成工具类
│	│	│	│	├─ConfigManage 配置文件管理类
│	│	│	│	├─FirstCreateBase 第一次登录检测
│	│	│	│	├─LinuxDos Linux命令执行类
│	│	│	│	├─WebCodeGenerate 前端文章内容生成类
│	│	│	│	└─WindowsDos windows命令执行类
│	│	│	├─config 项目配置目录
│	│	│	│	├─pay 支付配置目录
│	│	│	│	│	├─AlipayConfig 支付宝支付配置类
│	│	│	│	│	└─MyWxConfig 微信支付配置类
│	│	│	│	├─AsyncConfig 异步线程配置类
│	│	│	│	│	├─TimedTaskConfig 定时任务配置类
│	│	│	│	│	├─UserSecurityInterceptor 拦截配置类
│	│	│	│	│	└─WebConfig 访问控制类
│	│	│	│	├─core 核心类目
│	│	│	│	│	├─base 基础类目
│	│	│	│	│	├─cache	二级缓存
│	│	│	│	│	└─generator 代码生成器
│	│	│	│	├─data 数据访问器
│	│	│	│	│	├─DataBase 数据配置
│	│	│	│	│	└─MySqldbUtil 数据SQL执行工具
│	│	│	│	├─modular 实现模块业务
│	│	│	│	├─util 工具类
│	│	│	│	│	├─aop 切面编程
│	│	│	│	│	├─AllException 对异常类的处理
│	│	│	│	│	├─DealStrSub 对HTML进行的正则处理
│	│	│	│	│	├─FileHandle 对文件进行操作得工具类
│	│	│	│	│	├─GetTimeUtil 获取时间周期
│	│	│	│	│	├─IpAddressUtils 获取请求IP和地址
│	│	│	│	│	├─Office 对Office的文档处理（excel,word）
│	│	│	│	│	├─Oss 云存储处理
│	│	│	│	│	├─ParameDispose 所有参数格式处理方式
│	│	│	│	│	├─Result 返回数据封装
│	│	│	│	│	├─SendMessage 短信信息发送
│	│	│	│	│	└─SessionUtil 处理数据session缓存的处理方式
│	│   ├─webapp 页面文件
│	│	│	├─error 状态错误页面
│	│	│	├─plugin 组件页面
│	│	│	├─view 前端页面，可通过配置进行前后端分离
│	│	│	├─WEB-INF 后台管理页面
│	│	│	│	├─code 代码生成器页面
│	│	│	│	├─common 公共页面（勿删）
│	│	│	│	├─desktop 桌面操作系统页面
│	│	│	│	├─resources 资源上传页面
│	│	│	│	└─system 系统页面
│	│	└─resources 静态资源文件
│	│		├─mapper 数据操作mapper 未启用
│	│		├─META-INF 配置信息（根据需求配置）
│	│		├─properties 项目读取配置目录
│	│		├─speedyTemplate 代码生成模板
│	│		├─static 静态文件资源
│	│		├─application.yml 项目配置（根据需求配置）
│	│		├─base.properties 服务开发配置
│	│		└─speedy.sql 数据库基础配置安装（勿操作）

```

## 软件架构
```HTML
<h3>基础类：</h3>
<p>1.CodeController 代码生成控制器</p>
<p>2.LoginController 登录控制器</p>
<p>3.MainController 主方法控制器</p>
<p>4.MenuController 菜单控制器</p>
<p>5.RoleController 角色控制器</p>
<p>6.SytemUserController 系统人员控制器</p>
<p>7.ToolController 工具控制器（支付，短信等等工具集合）</p>
<p>8.FirstController 第一次进入后台的安装校验</p>
<p>9.PluginController 插件的管理控制</p>
<p>10.UserController 后台用户管理控制</p>
<p>11.ViewController 前端视图控制器</p>
<p>12.ApiController 接口管理控制器(处理跨域)</p>
```
## 安装教程
注：安装完成后自带基础的数据信息
<p>前端：127.0.0.1(对应域名) 首页</p>
<p>后端：127.0.0.1/system</p>
<p>1. 第一次安装直接访问 【域名/system】，配置完成后点击保存会自动根据配置的数据库进行安装基础数据 </p>
<p>2. 自行配置需要的结构目录</p>
<p>3. 配置完成即可使用</p>

## 使用说明

<p>1. 首次登录直接进入127.0.0.1/system 进行数据库的安装</p>
<p>2. 安装好后方可对平台进行使用</p>
<p>3. 如需进行开发，则需要共享到本地进行开发代码生成等，需要对SpringMVC模式有一定的了解</p>
<p>注:代码生成工具不支持在JAR包模式下使用</p>
<br/>
![如果觉得我得项目帮到您](https://images.gitee.com/uploads/images/2019/1022/100201_03a33a1f_922921.png)
<img src="https://images.gitee.com/uploads/images/2019/1022/100201_03a33a1f_922921.png" />
<br/>

## 合作
<p>支持定制APP,网站，微信开发 </p>
<p>微信号 lqy84527</p>


