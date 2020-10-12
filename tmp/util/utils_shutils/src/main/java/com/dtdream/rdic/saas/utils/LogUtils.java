package com.dtdream.rdic.saas.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
    public final static Logger logger = LoggerFactory.getLogger(LogUtils.class.getSimpleName());
    private static StringBuilder prefix (int frame) {
        int frm = frame + 1;
        return ThreadUtils.info(frm);
    }
    private static String message(Object msg) {
        return new StringBuilder()
            .append("[").append(prefix(3)).append("]")
            .append(msg).toString();
    }
    public static void info(Object info) {
        logger.debug(info.toString());
    }
    public static void info(Object info, Throwable t) {
        logger.debug(info.toString(), t);
    }
    public static void debug(Object info) {
        logger.debug(info.toString());
    }
    public static void debug(Object info, Throwable t) {
        logger.debug(info.toString(), t);
    }
    public static void error(Object info) {
        logger.error(info.toString());
    }
    public static void error(Object info, Throwable t) {
        logger.error(info.toString(), t);
    }
}
