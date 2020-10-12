package pers.man.quickdevcommon.constant;

/**
 * 用户状态常量
 *
 * @author MAN
 * @version 1.0
 * @date 2020-04-10 19:49
 * @project quick-dev
 */
public enum UserStateEnum {
    /**
     * 正常
     */
    NORMAL("0"),
    /**
     * 锁定
     */
    LOCK("1"),
    /**
     * 禁用
     */
    BAN("2");

    private String value;

    private UserStateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
