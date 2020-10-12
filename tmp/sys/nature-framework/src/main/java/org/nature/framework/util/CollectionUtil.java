package org.nature.framework.util;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 * Created by Ocean on 2016/3/8.
 */
public class CollectionUtil {
    /**
     * 判断collection不为空并且大小不为0
     */
    public static boolean isNotEmpty(Collection<?> collection){
       return collection!=null&&collection.size()>0;
    }

    /**
     * 判断collection为空或者大小为0
     */
    public static boolean isEmpty(Collection<?> collection){
        return !isNotEmpty(collection);
    }

    /**
     * 判断Map是不为空并且大小不为0
     */
    public static boolean isNotEmpty(Map<?,?> map){
        return map!=null&&!map.isEmpty();
    }

    /**
     * 判断Map为空或者大小为0
     */
    public static boolean isEmpty(Map<?,?> map){
        return !isNotEmpty(map);
    }

}
