package cn.usbtg.session.util;

import java.util.List;
import java.util.Map;

/**
 * 工具类:验证是否为空
 */
public class EmptyUtil {

    /**
     * 验证对象是否为空
     *
     * @param obj 待验证对象
     * @return
     */
    public static boolean isEmpty(Object obj) {
        return null == obj;
    }

    /**
     * 验证对象是否不为空
     *
     * @param obj 待验证对象
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 验证list是否为空
     *
     * @param list 待验证对象
     * @return
     */
    public static boolean isEmpty(List list) {
        if (isEmpty((Object) list)) return true;
        if (list.size() == 0) return true;
        return false;
    }

    /**
     * 验证list是否不为空
     *
     * @param list 待验证对象
     * @return
     */
    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    /**
     * 验证map是否为空
     *
     * @param map 待验证对象
     * @return
     */
    public static boolean isEmpty(Map map) {
        if (isEmpty((Object) map)) return true;
        if (map.size() == 0) return true;
        return false;
    }

    /**
     * 验证map是否不为空
     *
     * @param map 待验证对象
     * @return
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    /**
     * 验证String是否为空
     *
     * @param str 待验证对象
     * @return
     */
    public static boolean isEmpty(String str) {
        if (isEmpty((Object) str)) return true;
        if (str.trim().length() == 0) return true;
        return false;
    }

    /**
     * 验证String是否不为空
     *
     * @param str 待验证对象
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
