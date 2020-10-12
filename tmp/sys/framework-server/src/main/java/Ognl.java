import cn.backflow.lib.util.StringUtil;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


/**
 * Ognl工具类，主要是为了在ognl表达式访问静态方法时可以减少长长的类名称编写
 * Ognl访问静态方法的表达式为: @class@method(args) 如:
 * <p>
 * <pre>
 * 	&lt;if test="@Ognl@isNotEmpty(userId)">
 *     and user_id = #{userId}
 * &lt;/if>
 * </pre>
 */
public class Ognl {

    public static void main(String[] args) throws Exception {
        System.out.println(StringUtil.md5("taeyeon", "taeyeon"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
        Date date = format.parse("2009-1-2");
        System.out.println(date);

    }

    /**
     * 可以用于判断String,Map,Collection,Array是否为空
     *
     * @param o 目标对象
     */
    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if (o == null) return true;

        if (o instanceof String) {
            if (((String) o).length() == 0) {
                return true;
            }
        } else if (o instanceof Collection) {
            if (((Collection<?>) o).isEmpty()) {
                return true;
            }
        } else if (o.getClass().isArray()) {
            if (Array.getLength(o) == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map<?, ?>) o).isEmpty()) {
                return true;
            }
        } else
            return false;
        return false;
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否不为空
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNotBlank(Object o) {
        return !isBlank(o);
    }

    public static boolean isNumber(Object o) {
        if (o == null) return false;
        if (o instanceof Number) return true;
        if (o instanceof String) {
            String str = (String) o;
            if (str.length() == 0) return false;
            if (str.trim().length() == 0) return false;
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean isBlank(Object o) {
        return o == null || o instanceof String && isBlank((String) o);
    }

    public static boolean isBlank(String str) {
        if (str == null || str.length() == 0) return true;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}