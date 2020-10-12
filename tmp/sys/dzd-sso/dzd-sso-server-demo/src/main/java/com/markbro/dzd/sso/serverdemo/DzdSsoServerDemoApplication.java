package com.markbro.dzd.sso.serverdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基于xxl-sso，但提供了额外的配置和功能。
 * 比如：
 * 1.抽象出了3个接口。这样容易配置和扩展。ISsoLoginHelper（实现类有SsoTokenLoginHelper和SsoWebLoginHelper）、ISsoLoginStore（实现类还是原来的基于redis的SsoLoginStore）
 * ISsoSessionIdHelper（实现类有原来的SsoSessionIdHelper和我实现的Des加密处理的MySsoSessionIdHelper）
 * 2.类或者接口命名变动（去掉xxl-）,UserInfo变为SsoUserInfo、UserService变为SsoUserService。
 * 3.增加处理人工删除Cookie中sesssionId的情况。
 * 4.增加了人工踢出用户接口（/sso/invalidSession或/sso/api/invalidSession）。检测用户登录状态接口（/sso/loginState或/sso/api/loginState）。
 * 5.配置文件增加登录页面表单key和Cookie的key自定义项。
 * 6.配置文件增加了服务端使用第一条中接口哪个实现类的自定义配置。
 * 7.还未实现的功能。在SsoController有体现。在线用户管理界面、人工踢出用户和获取在线用户session数据的接口。
 * 8. 对于客户端，打算提供过滤器和拦截器2中方式过滤用户请求。并且提供了SsoHelp静态类，接入原有的未实现sso功能的系统应用登录、退出只需要调用该类的一个方法。
 */
@SpringBootApplication
public class DzdSsoServerDemoApplication {

    /**
     * 自己搭建一个sso服务端，可直接在sso-server代码上直接改动。
     * 一般来说，向本demo直接继承sso-server的代码更简单。
     * 除了本启动类外，服务端demo只需要写4个java类
     *  1. DemoSsoWebMvcConfigurer继承 SsoWebMvcConfigurer即可， 不需要写代码。
     *  2.MySsoConf 继承SsoConfig ，需要写少量代码。主要是指定 使用哪个用户登录校验接口SsoUserService的实现类。
     *  3.MySsoController 继承 SsoController 即可， 不需要写代码。
     *  4.实现自己系统用户登录校验 接口SsoUserService的实现类。（可以实现多个登录校验，至于选择哪一个可以在配置文件配置。前提是SsoConf类有这些选项对应的配置。）
     */
    public static void main(String[] args) {
        SpringApplication.run(DzdSsoServerDemoApplication.class, args);
    }
}
