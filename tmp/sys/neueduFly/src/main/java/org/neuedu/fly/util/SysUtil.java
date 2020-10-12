package org.neuedu.fly.util;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/10/22-9:15
 **/
public class SysUtil {

    public static final String VERIFY_CODE = "verifyCode";  //验证码
    public static final String TEL_CODE = "telCode"; //手机验证码
    public static final String CURRENT_USER = "currentUser"; //登录存储的用户
    public static final String EMAIL_TOKEN = "emailToken"; //邮箱激活码

    public interface UserStatus{
        int ENABLED = 0;  //启用
        int DISABELD = 1; //禁用
    }

}
