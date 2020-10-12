package com.site.common.constants;

/**
 * 常量
 * 
 * @author wangxin
 * @Date 2015-03-04
 */
public class Constants {

    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    public static int defaultPageSize = 10;

    public static final String SYSTEM_ROLE_CODE = "system_role";// 系统管理员
    public static final String COMMON_ROLE_CODE = "common_role";// 普通用户

    public static final String SYSTEM_ROLE_NAME = "系统管理员";
    public static final String COMMON_ROLE_NAME = "普通用户";

    public static final String PERMISSION_SESSION = "permission_session";
    public static final String SESSION_KEY = "session_key";
    // url & roleName
    public static final String ROLE_CODE = "role_code";
    public static final String PERMISSION_URL = "permission_url";

    /**
     * 角色： 系统管理员：ROLE_SYSTEM_ADMIN<br/>
     * QA：ROLE_QA <br/>
     * 客服管理员：ROLE_CUSTOMER_SERVICE_ADMIN <br/>
     * 客服用户：ROLE_CUSTOMER_SERVICE<br/>
     */
    public static final String ROLE_SYSTEM_ADMIN = "role_system_admin";
    public static final String ROLE_QA = "role_qa";
    public static final String ROLE_CUSTOMER_SERVICE_ADMIN = "role_customer_service_admin";
    public static final String ROLE_CUSTOMER_SERVICE = "role_customer_service";

    /******************** xss攻击防注入参数 begin ************************/
    // 出错跳转的目的地
    public final static String XSS_ERROR_PATH = "/WEB-INF/pages/error.jsp";
    // 不进行拦截的urlnm
    public final static String XSS_EXCLUDE_PATHS = "";
    // 需要拦截的JS字符关键字
    public final static String XSS_SAFELESS =
        "<script, </script, <iframe, </iframe, <frame, </frame, set-cookie, %3cscript, %3c/script, %3ciframe, %3c/iframe, %3cframe, %3c/frame, src=\"javascript:, <body, </body, %3cbody, %3c/body, <, >, </, />, %3c, %3e, %3c/, /%3e";
    /******************** xss攻击防注入参数 end ************************/

    /* 状态,1=有效，0=失效 */
    public static final Integer STATUS_VALID = 1;
    public static final Integer STATUS_INVALID = 0;

    /** 已删除 1 */
    public final static int IS_DEL_Y = 1;

    /** 未删除 0 */
    public final static int IS_DEL_N = 0;

}
