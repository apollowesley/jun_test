package com.markbro.dzd.sso.core;

import com.markbro.dzd.sso.core.entity.ReturnT;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2018/12/2.
 */
public class SsoConf {
    private static Logger logger = LoggerFactory.getLogger(SsoConf.class);

    public static  String SSO_SERVER;
    public static  String SSO_LOGIN_PATH ;
    public static  String SSO_LOGOUT_PATH ;
    public static  String SSO_EXCLUDED_PATHS;
    public static  String SSO_FILTER_TYPE;

    public static String KEY_USER_NAME;
    public static String KEY_PASSWORD;
    public static String KEY_REMEMBER;
    /**
     * 是否启用SSO
     */
    public static  Boolean SSO_ENABLE;
    /**
     * 根据配置文件config/sso.properties初始化参数
     */
    static{
        Properties prop = new Properties();
        try {
            InputStream inputStream = SsoConf.class.getClassLoader().getResourceAsStream("config/sso.properties");
            prop.load(inputStream);
            String str = prop.getProperty("sso.enable");
            if(StringUtils.isEmpty(str)){
                SSO_ENABLE = false;
            }else{
                SSO_ENABLE = Boolean.parseBoolean(str);
            }
            SSO_SERVER = prop.getProperty("sso.ssoServer");
            SSO_LOGIN_PATH= prop.getProperty("sso.loginPath");
            SSO_LOGOUT_PATH = prop.getProperty("sso.logoutPath");
            SSO_EXCLUDED_PATHS = prop.getProperty("sso.excludedPaths");
            SSO_FILTER_TYPE = prop.getProperty("sso.filterType");
            KEY_USER_NAME = prop.getProperty("sso.form.key.username");
            KEY_PASSWORD = prop.getProperty("sso.form.key.password");
            KEY_REMEMBER = prop.getProperty("sso.form.key.remember");
        } catch (IOException e) {
            logger.error("参数文件{}读取出错","config/sso.properties");
        }


    }
    /**
     * sso sessionid, between browser and sso-server (web + token client)
     */
    public static final String SSO_SESSIONID = "sso_sessionid";


    /**
     * redirect url (web client)
     */
    public static final String REDIRECT_URL = "redirect_url";

    /**
     * sso user, request attribute (web client)
     */
    public static final String SSO_USER = "sso_user";


    public static final String KEY_INVALID_SESSION="INVALID_SESSION";

    /**
     * login fail result
     */
    public static final ReturnT<String> SSO_LOGIN_FAIL_RESULT = new ReturnT(501, "sso not login.");
}
