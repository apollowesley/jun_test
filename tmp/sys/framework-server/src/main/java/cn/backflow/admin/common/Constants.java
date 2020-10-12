package cn.backflow.admin.common;

public class Constants {
    public static final int USER_STATUS_LOCKED = 0; // 用户锁定状态

    public static final String SYS_CACHE = "SYS_CACHE";
    public static final String WORDS_SPLITER = "、|，|,|\\s+";

    public static String LOGIN_FAILURE_MAP = "LOGIN_FAILURE_MAP";
    public static String USER_SCREEN_LOCKED = "USER_SCREEN_LOCKED";
    public static String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY"; // Session中保存验证码的key
    public static String SESSION_USER_KEY = "USER"; // Session中保存用户登陆信息的key

    public static String unLoginMessage = "未登录或登录已超时，需要重新登录。";
    public static String unAuthorizeMessage = "您没有权限执行此操作，请检查账号的权限级别。";
    public static String illegalArgument = "请求参数错误。";
    public static String illegalRequest = "非法请求。";
    public static String systemBusy = "系统繁忙，请稍后再试。";
    public static String systemError = "系统内部错误，请联系管理员。";
}