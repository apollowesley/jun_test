package com.nikijava.elklog;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.UUID;

/**
  *
  * @ProjectName:
  * @Package:        com.niki.log
  * @ClassName:      JsonUtils
  * @Description:     类作描述
  * @Author:         Niki Zheng
  * @CreateDate:     2019/8/26 16:56
  * @UpdateRemark:   更新说明
  * @Version:        1.0
 */
public class JsonUtils {
    private final static Gson gson = new Gson();


    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}