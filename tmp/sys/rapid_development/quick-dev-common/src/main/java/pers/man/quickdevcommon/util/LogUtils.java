package pers.man.quickdevcommon.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志工具类
 *
 * @author MAN
 * @version 1.0
 * @date 2020-04-01 20:23
 * @project quick-dev
 */
@Slf4j
public class LogUtils {
    /**
     * 记录info信息
     *
     * @param info
     */
    public static void info(String info) {
        log.info(info);
    }

    /**
     * 记录warn信息
     *
     * @param warn
     */
    public static void warn(String warn) {
        log.warn(warn);
    }

    /**
     * 记录错误信息
     *
     * @param error
     */
    public static void error(String error) {
        log.error(error);
    }

    private LogUtils() {
    }
}
