package org.neuedu.fly.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/10/18-14:31
 **/
public class GsonUtil {
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) ->  new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
            .create();

    public static String toJson(Object object){
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json,Class<T> clazz){
        return gson.fromJson(json,clazz);
    }


}
