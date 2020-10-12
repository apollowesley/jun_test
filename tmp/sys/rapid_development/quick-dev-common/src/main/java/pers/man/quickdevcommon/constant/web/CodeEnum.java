package pers.man.quickdevcommon.constant.web;

/**
 * 返回状态码
 *
 * @author MAN
 * @version 1.0
 * @date 2020-03-31 20:25
 * @project quick-dev
 */
public enum CodeEnum {
    /**
     * 成功状态码
     */
    SUCCESS("0"),
    /**
     * 失败状态码
     */
    FAIL("1");

    private String value;

    private CodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
