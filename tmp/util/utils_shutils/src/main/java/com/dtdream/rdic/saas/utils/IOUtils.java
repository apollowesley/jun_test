package com.dtdream.rdic.saas.utils;

import org.codehaus.jackson.util.ByteArrayBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ozz8 on 2015/06/25.
 */
public class IoUtils {
    protected final static Logger logger = LoggerFactory.getLogger(IoUtils.class);
    public static void close (Closeable is) {
        if (null != is) {
            try {
                is.close();
            } catch (IOException e) {
                logger.error("尝试关闭流失败：", e);
            }
        }
    }

    public static ByteArrayBuilder read (InputStream is) {
        ByteArrayBuilder bab = new ByteArrayBuilder(8192);
        int c;
        try {
            while ((c = is.read()) != -1)
                bab.append(c);
            return bab;
        } catch (IOException e) {
            logger.error("读取流内容失败：", e);
            return null;
        }
    }
}
