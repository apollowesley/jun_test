package org.nature.framework.util;

/**
 * 数组工具类
 * Created by Ocean on 2016/3/9.
 */
public final class ArrayUtil {

    /**
     * 判断数组为空
     */
    public static boolean isEmpty(Object[] objects){
        return objects==null||objects.length==0;
    }

    /**
     * 判断数组为空
     */
    public static boolean isNotEmpty(Object[] objects){
        return !isEmpty(objects);
    }
}
