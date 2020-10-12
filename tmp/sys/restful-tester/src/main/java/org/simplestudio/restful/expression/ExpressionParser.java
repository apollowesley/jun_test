package org.simplestudio.restful.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.simplestudio.restful.httpclient.cache.FeatureCache;
import org.simplestudio.restful.util.PropertyUtil;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import net.minidev.json.JSONArray;

public class ExpressionParser {

    private static Pattern PARAM_PATTERN = Pattern.compile("\\$\\{(.+?)\\}");

    public static Object parse(String expression) throws Exception {
        Matcher matcher = PARAM_PATTERN.matcher(expression);

        while (matcher.find()) {
            String param = matcher.group(1);
            Object paramResult = getParamResult(param);

            if (param.equals(expression.replaceAll(" ", "").replace("$", "").replace("{", "")
                    .replace("}", ""))) {//仅仅只是要取某个值，不需要解析成表达式
                if (paramResult == null || !PARAM_PATTERN.matcher(paramResult.toString()).find()) {
                    return paramResult;
                }
            }

            expression = expression.replace("${" + param + "}",
                    formatResult(paramResult).toString());
            matcher = PARAM_PATTERN.matcher(expression);
        }

        return expression;
    }

    private static Object getParamResult(String param) throws Exception {
        String[] splitArr = param.split("\\.");
        String key = splitArr[0];

        Object cacheObj = getFromCache(key);
        if (cacheObj == null) {//缓存中不存在，从内置参数中获取
            try {
                cacheObj = NestedParamHelper.get(key);
            } catch (Exception e) {//ignore

            }
        }

        if (cacheObj != null && splitArr.length > 1) {//参数类似a.b的方式，可能需要JsonPath解析
            Object result = cacheObj;
            if (cacheObj instanceof String) {
                try {
                    cacheObj = JSONObject.parse(cacheObj.toString());
                } catch (Exception e) {

                }
            }
            if (cacheObj instanceof JSONObject || cacheObj instanceof JSONArray
                    || cacheObj instanceof com.alibaba.fastjson.JSONArray) {
                String jsonPathExp = param.replace(key, "");

                try {
                    result = JsonPath.read(cacheObj, "$" + jsonPathExp);
                } catch (PathNotFoundException e) {//有可能没有该属性，解析出错，返回null
                    return null;
                }
            }

            return result;
        }

        return cacheObj;
    }

    private static Object formatResult(Object cacheObj) {
        if (cacheObj == null) {
            return "null";
        }
        else if (cacheObj instanceof String) {
            return formatString(cacheObj.toString());
        }
        else {
            return cacheObj;
        }
    }

    private static String formatString(String result) {
        return "\"" + result + "\"";
    }

    private static Object getFromCache(String key) {
        Object cacheObj = FeatureCache.get(key);
        if (cacheObj == null) {//feature cache取不到，从properties文件中获取
            cacheObj = PropertyUtil.getProperty(key);
        }

        return cacheObj;
    }

}
