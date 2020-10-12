package com.yutong.smart.util;

import java.util.Enumeration;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * 
 * @author yuxiangtong
 *
 */
public class Log4JUtils {
    public static void setRootLevel(Level level) {
        Logger.getRootLogger().setLevel(level);
    }


    public static void setLogger(String packageName, String level) {
        LogManager.getLogger(packageName).setLevel(Level.toLevel(level));
    }


    public static void enableDebug(String target) {
        LogManager.getLogger(target).setLevel(Level.DEBUG);
    }


    public static void enableInfo(String target) {
        LogManager.getLogger(target).setLevel(Level.INFO);
    }


    public static void enableWarn(String target) {
        LogManager.getLogger(target).setLevel(Level.WARN);
    }


    public static void enableError(String target) {
        LogManager.getLogger(target).setLevel(Level.ERROR);
    }


    public static void printAllCurrLoggers() {
        @SuppressWarnings("unchecked")
        Enumeration<Logger> currentLoggers = LogManager.getCurrentLoggers();
        while (currentLoggers.hasMoreElements()) {
            Logger logger = currentLoggers.nextElement();
            System.out.println(logger.getName() + ":" + logger.getLevel());
        }
    }

}
