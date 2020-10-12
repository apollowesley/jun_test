package org.simplestudio.restful.util;

public class RestUtil {

    /**
     * 获取类相对于classpath的路径，如类型为a.b.c.D，则返回a/b/c
     * @param classz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getPackagePath(Class classz) {
        String path = classz.getName();
        path = path.replace("." + classz.getSimpleName(), "");
        path = path.replace(".", "/");

        return path;
    }

    public static String trimQuote(String str) {
        if (str != null) {
            str = str.replaceAll("\"", "");
        }

        return str;
    }

    public static boolean isBlank(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }

        return false;
    }

}
