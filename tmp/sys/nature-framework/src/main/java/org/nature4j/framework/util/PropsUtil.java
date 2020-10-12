package org.nature4j.framework.util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 属性文件工具类
 * Created by Ocean on 2016/3/7.
 */
public class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String filename){
        Properties properties = null;
        InputStream inputStream = null;
        inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        try {
            if(inputStream==null){
                throw new FileNotFoundException(filename+" file is not found");
            }
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("load properties file failure",e);
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("close inputStream failure",e);
                }
            }
        }
        return properties;
    }

    /**
     * 获取字符串类型属性（可指定默认值）
     */
    public static String getString(Properties properties,String key,String defaultValue){
        return  properties.getProperty(key,defaultValue);
    }

    /**
     * 获取字符串类型属性（默认值为“”）
     */
    public static String getString(Properties properties,String key){
        return properties.getProperty(key,"");
    }

    /**
     * 获取数值类型属性（可指定默认值）
     */

    public static int getInt(Properties properties,String key,int defaultValue){
        int value = defaultValue;
        if(properties.containsKey(key)){
            try {
                String string = properties.getProperty(key);
                value = CastUtil.castInt(string);
            } catch (Exception e) {
                value=defaultValue;
            }
        }
        return value;
    }
    /**
     * 获取数值类型属性（默认值为0）
     */

    public static int getInt(Properties properties,String key){
        return getInt(properties,key,0);
    }
    
    /**
     * 获取数值类型属性（可指定默认值）
     */

    public static long getLong(Properties properties,String key,long defaultValue){
    	long value = defaultValue;
        if(properties.containsKey(key)){
            try {
                String string = properties.getProperty(key);
                value = CastUtil.castLong(string);
            } catch (Exception e) {
                value=defaultValue;
            }
        }
        return value;
    }
    /**
     * 获取数值类型属性（默认值为0）
     */

    public static long getLong(Properties properties,String key){
        return getLong(properties,key,0);
    }

    /**
     * 获取布尔类型属性（可指定默认值）
     */
    public static boolean getBoolean(Properties properties,String key,boolean defaultValue){
        boolean value = defaultValue;
        if(properties.containsKey(key)){
            String string = properties.getProperty(key);
            value = CastUtil.castBoolean(string,defaultValue);
        }
        return value;
    }

    /**
     * 获取布尔类型属性（默认值false）
     */
    public static boolean getBoolean(Properties properties,String key){
        return getBoolean(properties,key,false);
    }
}
