package com.handy.common.constants;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 17:11
 */
public abstract class Constants {
    /**
     * 已删除标识
     */
    public static final boolean DELETE_FLAG = true;
    /**
     * 未删除标识
     */
    public static final boolean NOT_DELETE_FLAG = false;
    /**
     * 登录用户的资源 session key
     */
    public static final String USERRESOURCEKEY = "USERRESOURCEKEY";
    /**
     * 登录用户 session key
     */
    public static final String USERSESSION = "BASEUSER";
    /**
     * session 失效时间 8小时
     */
    public static final int SESSION_TIME_OUT = 28800;
}
