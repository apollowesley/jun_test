package cn.backflow.lib.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

@SuppressWarnings({"unchecked"})
public abstract class JsonUtil {

    private static Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    public static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(Include.NON_NULL);
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。
     * 如果对象为Null, 返回null.
     * 如果集合为空集合, 返回"[]".
     */
    public static String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            LOG.warn("write to json string error:" + o, e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 同{@link #toJson}, 不过输出的是格式化的JSON
     */
    public static String toPrettyJson(Object o) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            LOG.warn("write to json string error:" + o, e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 反序列化POJO或简单Collection如List&lt;String>.
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     * 如需反序列化复杂Collection如List&lt;MyBean>, 请使用fromJson(String,JavaType)
     *
     * @see #fromJson(String, JavaType)
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            LOG.error("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List&lt;Bean&gt;, 先使用函數createCollectionType构造类型,然后调用本函数.
     *
     * @see #createCollectionType(Class, Class...)
     */
    public static <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            LOG.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 構造泛型的Collection Type如:
     * ArrayList&lt;MyBean&gt;,
     * 则调用constructCollectionType(ArrayList.class,MyBean.class)
     * HashMap&lt;String,MyBean&gt;, 则调用(HashMap.class,String.class, MyBean.class)
     */
    public static JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 當JSON裡只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
     */
    public static <T> T update(String jsonString, T object) {
        try {
            return (T) mapper.readerForUpdating(object).readValue(jsonString);
        } catch (Exception e) {
            LOG.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
        return null;
    }

    /**
     * 輸出JSONP格式數據.
     */
    public static String toJSONP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }
}