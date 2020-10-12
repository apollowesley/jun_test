# dzd-sso

#### 项目介绍
SpringBoot版单点登录模块。

#### 软件架构



- dzd-sso核心模块
- dzd-sso-server 服务端模块
- dzd-sso-server-demo 服务端模块Demo 


#### 软件环境

1. Java8+
2. SpringBoot2.1.1
3. Spring5.1.3
4. Marven3.2.5
5. IntelliJ IDEA 2018

#### 使用说明

本项目基于xxl-sso 并作出一些适当改动。相关文档可参考[xxl-sso主页](https://gitee.com/xuxueli0323/xxl-sso)
改动说明如下：

1. 增加配置文件config/sso.properties。
1. 抽象出了3个接口。这样容易配置和扩展。ISsoLoginHelper（实现类有SsoTokenLoginHelper和SsoWebLoginHelper）、ISsoLoginStore（实现类还是原来的基于redis的SsoLoginStore）、ISsoSessionIdHelper（实现类有原来的SsoSessionIdHelper和我实现的Des加密处理的MySsoSessionIdHelper）
1. 类或者接口命名变动（去掉xxl-）,UserInfo变为SsoUserInfo、UserService变为SsoUserService。
1. 增加处理人工删除Cookie中sesssionId的情况。
1. 增加了人工踢出用户接口（/sso/invalidSession或/sso/api/invalidSession）。检测用户登录状态接口（/sso/loginState或/sso/api/loginState）。
1. 配置文件增加登录页面表单key和Cookie的key自定义项。
1. 配置文件增加了服务端使用第一条中接口哪个实现类的自定义配置。
1. 还未实现的功能。在SsoController有体现。在线用户管理界面、人工踢出用户和获取在线用户session数据的接口。
1. 对于客户端，打算提供过滤器和拦截器2中方式过滤用户请求。并且提供了SsoHelp静态类，接入原有的未实现sso功能的系统应用登录、退出只需要调用该类的一个方法。




