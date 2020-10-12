package org.nature.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 转型工具类
 * Created by Ocean on 2016/3/7.
 */
public final class CastUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(CastUtil.class);

    /**
     * 转字符串类型(可指定默认值）
     */
    public static String castString(Object object, String defaultValue) {
        return object != null ? String.valueOf(object) : defaultValue;
    }

    /**
     * 转字符串类型（默认为""）
     */
    public static String castString(Object object) {
        return castString(object, "");
    }

    /**
     * 转为double类型（可指定默认值）
     */

    public static double castDouble(Object object, double defaultValue) {
        double value = defaultValue;
        if (object != null) {
            String stringValue = castString(object);
            if (StringUtil.isNotEmpty(stringValue)) {
                try {
                    value = Double.valueOf(stringValue);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                    LOGGER.error("fail to cast double",e);
                }
            }
        }
        return value;
    }

    /**
     * 转为double类型（默认为0）
     */
    public static double castDouble(Object object) {
        return castDouble(object, 0);
    }

    /**
     * 转为long类型（可指定默认值）
     */
    public static long castLong(Object object, long defaultValue) {
        long value = defaultValue;
        if (object != null) {
            String string = castString(object);
            if(StringUtil.isNotEmpty(string)){
                try {
                    value = Long.valueOf(string);
                } catch (NumberFormatException e) {
                    value=defaultValue;
                    LOGGER.error("fail to cast long",e);
                }
            }
        }
        return value;
    }

    /**
     * 转为long类型（默认为0）
     */
    public static long castLong(Object object){
        return castLong(object,0);
    }
    
    /**
     * 转为float类型（可指定默认值）
     */
    public static float castFloat(Object object, float defaultValue) {
    	float value = defaultValue;
        if (object != null) {
            String string = castString(object);
            if(StringUtil.isNotEmpty(string)){
                try {
                    value = Float.valueOf(string);
                } catch (NumberFormatException e) {
                    value=defaultValue;
                    LOGGER.error("fail to cast long",e);
                }
            }
        }
        return value;
    }
    /**
     * 默认值为0
     */
    public static float castFloat(Object object){
    	return castFloat(object,0);
    } 
    
    /**
     * 转为int类型（可指定默认值）
     */
    public static int castInt(Object object,int defaultValue){
        int value = defaultValue;
        if(object != null){
            String string = castString(object);
            if(StringUtil.isNotEmpty(string)){
                try {
                    value = Integer.valueOf(string);
                } catch (NumberFormatException e) {
                    value = defaultValue;
                    LOGGER.error("fail to cast int",e);
                }
            }
        }
        return value;
    }

    /**
     * 转为int类型（默认值为0）
     */
    public static int castInt(Object object){
        return castInt(object,0);
    }

    /**
     * 转为boolean类型（可指定默认值）
     */
    public static boolean castBoolean(Object object,boolean defaultValue){
        boolean value = defaultValue;
        if(object!=null){
            String string = castString(object);
            if(StringUtil.isNotEmpty(string)) {
                try {
                    value = Boolean.valueOf(string);
                } catch (Exception e) {
                    value = defaultValue;
                    LOGGER.error("fail to cast boolean",e);
                }
            }
        }
        return value;
    }

    /**
     * 转为boolean类型（默认为false)
     */
    public static boolean castBoolean(Object object){
        return castBoolean(object,false);
    }
    /**
     * 转为boolean类型（可指定默认值）
     */
    public static char castChar(Object object,char defaultValue){
    	char value = defaultValue;
        if(object!=null){
            String string = castString(object);
            if(StringUtil.isNotEmpty(string)) {
                try {
                    value = string.toCharArray()[0];
                } catch (Exception e) {
                    value = defaultValue;
                    LOGGER.error("fail to cast boolean",e);
                }
            }
        }
        return value;
    }

}
