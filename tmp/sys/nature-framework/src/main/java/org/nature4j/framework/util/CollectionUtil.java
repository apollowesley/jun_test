package org.nature4j.framework.util;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 * Created by Ocean on 2016/3/8.
 */
public class CollectionUtil {

    public static boolean isNotEmpty(Collection<?> collection){
       return collection!=null&&collection.size()>0;
    }


    public static boolean isEmpty(Collection<?> collection){
        return !isNotEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?,?> map){
        return map!=null&&!map.isEmpty();
    }

    public static boolean isEmpty(Map<?,?> map){
        return !isNotEmpty(map);
    }

}
