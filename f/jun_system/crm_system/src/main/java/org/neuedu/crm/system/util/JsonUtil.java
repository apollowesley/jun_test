package org.neuedu.crm.system.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: CDHONG.IT
 **/
public class JsonUtil {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    //获取Json转换实例
    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    // 转换为 JSON 字符串
    public static String toString(Object obj) throws JsonProcessingException {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        return objectMapper.writeValueAsString(obj);
    }

    // 转换为 JSON 字符串，忽略空值
    public static String ToStringIgnoreNull(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(obj);
    }

    // 转换为 JavaBean
    // 转换为 JavaBean
    public static <T> T toEntity(String jsonString,Class<T> clazz) throws Exception {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
        return objectMapper.readValue(jsonString, clazz);
    }

    //将 JSON 数组转换为集合
    public static <T> List<T> toList(String jsonArrayStr, Class<T> clazz) throws Exception {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        return objectMapper.readValue(jsonArrayStr, javaType);
    }

    /**
     * 获取泛型的 Collection Type
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    // 将 Map 转换为 JavaBean
    public static <T> T mapToEntity(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    // 将 JSON 对象转换为 JavaBean
    public static <T> T objToEntity(Object obj, Class<T> clazz) {
        return objectMapper.convertValue(obj, clazz);
    }
}
