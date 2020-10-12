package org.simplestudio.restful.expression;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.simplestudio.restful.exception.RestException;
import org.simplestudio.restful.util.DateUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class NestedParamHelper {

    private static NestedParamHelper   SELF             = new NestedParamHelper();
    private static Random              RANDOM           = new Random();
    private static Map<String, Method> NESTED_PARAM_MAP = new HashMap<String, Method>();

    static {
        Class c = NestedParamHelper.class;
        try {
            NESTED_PARAM_MAP.put("随机串", c.getDeclaredMethod("getRandomStr", String.class));
            NESTED_PARAM_MAP.put("当前时间", c.getDeclaredMethod("getDate", String.class));
        } catch (Exception e) {

        }
    }

    /**
     * 获取当前时间
     * @param param
     *      如果参数是"当前时间"，返回yyyy-MM-dd，如果是"当前时间.yyyy-MM-dd hh:mm:ss"，则按指定的format返回
     * @return
     */
    public String getDate(String param) {
        String[] paramArr = param.split("\\.");
        if (paramArr.length == 1) {//默认返回yyyy-MM-dd
            return DateUtil.format(new Date());
        }
        else {//自定义的format
            SimpleDateFormat sdf = new SimpleDateFormat(paramArr[1].trim());
            return sdf.format(new Date());
        }
    }

    /**
     * 获取随机串
     * @param param
     *      默认返回5个随机串，如果不满足需求可以自定义，如"随机串.100"返回100个随机数
     * @return
     */
    public String getRandomStr(String param) {
        String[] paramArr = param.split("\\.");
        int randomLen = 5;
        if (paramArr.length > 1) {
            randomLen = Integer.parseInt(paramArr[1].trim());
        }
        String randomStr = "";
        for (int i = 0; i < randomLen; i++) {
            randomStr += RANDOM.nextInt(9);
        }

        return randomStr;
    }

    public static Object get(String key) throws Exception {
        //获取出内置参数的名称，因为有可能传递过来的是"随机串.100"
        String cacheKey = key.split("\\.")[0];
        if (NESTED_PARAM_MAP.containsKey(cacheKey)) {
            Method method = NESTED_PARAM_MAP.get(cacheKey);
            return method.invoke(SELF, new Object[] { key }).toString();
        }
        else {
            throw new RestException("[" + key + "]未定义,请确认定义之后再使用！");
        }
    }

}
