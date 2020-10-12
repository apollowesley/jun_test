package org.gen.testmongo.model;

/**
 * Created by Administrator on 2015/8/11.
 */
public class StringUtils {
    public static boolean isBlank(String orderBy) {
        return orderBy == null || "".equals(orderBy);
    }

    public static boolean containsAny(String orderBy, char... chars) {
        for (char aChar : chars) {
            if (orderBy.contains(String.valueOf(aChar)))
                return true;
        }
        return false;
    }
}
