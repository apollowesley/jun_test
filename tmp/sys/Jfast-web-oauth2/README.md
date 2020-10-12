<p align="center">
  <img src="https://img.shields.io/badge/Avue-2.1.0-green.svg" alt="Build Status">
  <img src="https://img.shields.io/badge/Spring%20Cloud-Hoxton.RELEASE-blue.svg" alt="Coverage Status">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.2.2.RELEASE-blue.svg" alt="Downloads">
</p>
 
**Jfast-web**   
   
- 基于 Spring Cloud Hoxton.RELEASE 、spring boot 2.2.2、Spring Security OAuth2 的RBAC权限管理系统  
- 基于数据驱动视图的理念封装最新版element-ui，即使没有 vue 的使用经验也能快速上手    
- 内置分布式服务降级, 同时基于redis实现分布式锁和分布式api接口限流
- 提供 lambda 、stream api的生产实践   

#### 核心依赖 


依赖 | 版本
---|---
Spring Boot |  2.2.2.RELEASE  
Spring Cloud | Hoxton.RELEASE    
Spring Security OAuth2 | 依赖于spring boot

#### 模块说明
```lua
jfast-web -- https://gitee.com/zhuimengshaonian/Jfast-web-oauth2

pig
├── jfast-web-api -- api 服务模块
     └── jfast-web-api-admin -- 通用用户权限管理系统业务处理模块[4000]
├── jfast-web-auth -- 认证服务
└── jfast-web-common -- 系统公共模块 
     ├── common-core -- 公共工具类核心包
     └── common-security -- 安全工具类
├── jfast-web-config -- 配置中心[8002]
├── jfast-web-registry -- 服务注册与发现[8001]
├── jfast-web-zuul -- Spring Cloud Zuul网关[8004]	 
```

#### 提交反馈

1. 欢迎提交 issue，请写清楚遇到问题的原因，开发环境，复显步骤。

2. 不接受`功能请求`的 issue，功能请求可能会被直接关闭。  
  
3. <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=1913188966&site=qq&menu=yes"> 个人QQ: 1913188966</a>

#### 开源协议


![](https://images.gitee.com/uploads/images/2019/0330/065147_e07bc645_410595.png)

