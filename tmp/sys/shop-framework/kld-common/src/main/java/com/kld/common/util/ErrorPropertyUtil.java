package com.kld.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取错误码定义
 * 1.延迟加载
 * 2.每个应用只加载一次
 *
 * @author anpushang
 */
public class ErrorPropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(ErrorPropertyUtil.class);

    private static class PropertyHolder {
        private static Properties prop;

        static {
            prop = new Properties();
            InputStream io = Thread.currentThread().getContextClassLoader().getResourceAsStream("errorCode.properties");
            try {
                prop.load(io);
            } catch (IOException e) {
                logger.error("错误码文件属性加载异常", e);
            } finally {
                if (io != null) {
                    try {
                        io.close();
                    } catch (IOException e) {
                        logger.error("关闭错误码文件异常", e);
                    }
                }
            }
        }
    }

    /**
     * 根据key读取Properties的value
     *
     * @param key 主键
     * @return 属性值
     */
    public static String getPropertyValue(String key) {
        return (String) PropertyHolder.prop.get(key);
    }
}
