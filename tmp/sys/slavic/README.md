# Slavic

#### 项目背景

#### 项目介绍
基于SpringBoot的Java应用开发基础通用脚手架,包含
1. Restful Api框架封装
2. 基于Vue + Element Ui的PC端后台管理系统
    1. 后台用户管理
    2. 后台资源,角色,权限管理
3. 项目文档
    1. 项目部署文档
    2. 相关技术基础扩展文档

#### 项目目标
针对Java 服务器应用开发,降低学习成本,提高开发效率.

#### 系统架构图

#### 管理系统Demo

#### 目录结构

```
slavic
├─hors 
│  ├─hors-admin-api 后台管理系统接口
│  ├─hors-orm 数据库持久层
│  ├─hors-portal 后台管理系统
│  ├─hors-service 接口层
│  └─hors-user-api App/H5等前端接口
├─veles 通用基础包
│   ├─veles-base 通用基础类
│   ├─veles-core 核心包
│   └─veles-utils 工具包
└─docs 
    └─guide 文档

```

#### 快速安装
```
1. 克隆项目到本地Ide
2. 安装数据库,更新application-dev/prod.yml数据源账号密码配置,导入/docs/init.sql文件,初始化数据库
3. 配置Maven环境,拉取jar包
4. 安装lombok插件,重启idea
5. 执行hors-admin-api目录下Application类main函数,启动项目管理系统服务端
6. 安装node.js
7. 进入hors-portal目录,cmd命令行执行npm install
8. 执行 npm run serve 浏览器访问 localhost:8080访问后台管理系统
9. 执行hors-user-api目录下Application类main函数,启动前端接口服务端
10. 使用postman访问Api接口获得响应
```

#### 软件环境
    
技术|描述|
---|---|
SpringBoot|项目框架
JWT|Token管理工具
Mybatis|持久层框架
Mysql | 数据库 
Maven | 项目构建管理
logback | 日志管理
PageHelper mybatis|分页插件
[vue](https://cn.vuejs.org/)|vue
[vuex](https://vuex.vuejs.org/zh/)|vuex
[Vue Router](https://router.vuejs.org/zh/)|SPA应用路由
[element ui](https://element.eleme.cn/2.0/#/zh-CN)|管理系统UI组件

#### 开发工具及常用插件
```
1. idea
    1. lombok 代码简洁插件
    2. cloud toolkit 服务端一键部署插件
2. vscode
    1. ESLint 代码规范
    2. Vetur vue插件
    3. Beautify 保存代码自动格式化代码插件
    4. HTML Snippets 简化标签输入
    5. Chinese (Simplified) Language vscode汉化
```

#### 版本及更新信息
1. 版本: 1.0
    1. 项目模块化
    2. 台管理系统
### 开发者
推荐 : QQ群 : 546556883
1. 微信: weixin54321a
2. QQ: 735376047

#### 鸣谢
感谢每一位浏览至此的小伙伴,无论是学习,还是基于项目二次开发,能为大家带来便利,便是本项目最大的价值.
如果在使用过程中有疑问或建议,欢迎微信,QQ,issue等任意方式提出,作者会及时解决并持续更新.