package pers.man.quickdevcommon.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 字符串工具类
 *
 * @author MAN
 * @version 1.0
 * @date 2020-04-01 19:53
 * @project quick-dev
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";

    /**
     * 生成日期随机数
     * 年月日时分秒毫秒
     *
     * @return
     */
    public static String getDateRandom() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMddHHmmssSSS"));
    }

    /**
     * 获取一个没有"-"的uuid
     *
     * @return
     */
    public static String getUUId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private StringUtils() {
    }
}
