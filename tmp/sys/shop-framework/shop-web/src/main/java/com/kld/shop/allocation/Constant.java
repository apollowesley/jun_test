package com.kld.shop.allocation;

/**
 * Created by anpushang on 2016/3/15.
 */
public class Constant {


    //权限拦截使用,不被拦截的url配置
    public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)).*";	//不对匹配该值的访问路径拦截（正则）
    //session key
    public static final String SESSION_USER = "SESSION_USER";
    //登陆地址
    public static final String LOGIN = "/login_toLogin.do";
    //图片服务器地址 1
    public static final String IMAGE_URL01 = "";
    //图片服务器地址 2
    public static final String IMAGE_URL02 = "";
    //图片服务器地址 3
    public static final String IMAGE_URL03 = "";


}
