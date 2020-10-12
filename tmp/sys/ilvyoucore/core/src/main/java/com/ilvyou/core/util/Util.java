package com.ilvyou.core.util;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by GuanYuCai on 2016/9/6 0006.
 */
public class Util {

    public static <T> T fromRequest(HttpServletRequest request, Class<? extends T> cls,
                                    String... params) throws Exception{
        if (params.length == 0){
            return getData(request, null, cls);
        }

        T obj = cls.newInstance();
        for (String param : params){
            String value = request.getParameter(param);
            String name = param.toLowerCase();
            Field field = cls.getDeclaredField(name);

            if (value != null){
                setField(field, obj, value);
            }
        }
        return obj;
    }

    public static <T> T fromMap(Map<String, Object> map, Class<? extends  T> cls) throws Exception {
        return getData(null, map, cls);
    }

    private static <T> T getData(HttpServletRequest request, Map<String, Object> maps,
                          Class<? extends T> cls) throws Exception{
        if (request == null && maps == null){
            return null;
        }

        T obj = cls.newInstance();

        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields){
            String name = f.getName();
            Object v = maps != null ? maps.get(name.toLowerCase())
                    : request.getParameter(name);

            if(v == null){
                continue;
            }

            if (maps != null &&
                    (f.getType() == Date.class || f.getType() == Timestamp.class)){
                setter(f, obj, v);
            }else{
                setField(f, obj, v.toString());
            }
        }

        return obj;
    }

    private static void setField(Field f, Object obj, String value) throws Exception {
        Class type = f.getType();
        if (type == int.class || type == Integer.class) {
            setter(f, obj, Integer.parseInt(value));
        }else if (type == long.class || type == Long.class) {
            setter(f, obj, Long.parseLong(value));
        }else if(type == short.class || type == Short.class){
            setter(f, obj, Short.parseShort(value));
        }else if (type == double.class || type == Double.class) {
            setter(f, obj, Double.parseDouble(value));
        }else if(type == float.class || type == Float.class){
            setter(f, obj, Float.parseFloat(value));
        }else if (type == boolean.class || type == Boolean.class){
            setter(f, obj, Boolean.parseBoolean(value));
        }else if (type == String.class || type == char.class) {
            setter(f, obj, value);
        }else if (type == Date.class || type == Timestamp.class){
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            setter(f, obj, fmt.parse(value));
        }
    }

    public static void transfer(Object from, Object to, String fields){
        for (String str : fields.split(",")){
            str = str.trim();
            Object value = getter(str, from);
            if (value != null){
                setter(to, str, value, value.getClass());
            }
        }
    }

    public static Object getter(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    private static void setter(Field f, Object obj, Object value) throws IllegalAccessException {
        f.setAccessible(true);
        f.set(obj, value);
    }

    private static void setter(Object obj, String fieldName, Object value, Class<?> type) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setter = "set" + firstLetter + fieldName.substring(1);
            Method method = obj.getClass().getMethod(setter, type);
            method.invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long createId(){
        String s = System.currentTimeMillis() + StringUtil.randomNum(6);
        return Long.parseLong(s);
    }
}
