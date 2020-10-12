package com.mkfree.hbase.example.domain;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * author oyhk
 *
 */
public class HBaseDomain {

    /**
     * 用于反射缓存，提高性能
     */
    private static Map<Class, Class> cacheClassMap = new HashMap<>();
    /**
     * 用于反射缓存，提高性能
     */
    private static Map<String, Field> cacheFieldMap = new HashMap<>();

    /**
     *
     * 把从hbase读取中的数据转换成对应的target对象数据
     *
     * @param target
     * @param hBaseResult
     * @throws IllegalAccessException
     */
    public static void orh(Object target, Result hBaseResult) throws IllegalAccessException {
        for (Cell cell : hBaseResult.listCells()) {
            String columnName = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            Field field = getClassFiled(target.getClass(), columnName);
            Class fieldType = field.getType();
            if (fieldType == String.class) {
                field.set(target, Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            } else if (fieldType == Double.class) {
                field.set(target, Bytes.toDouble(cell.getValueArray(), cell.getValueOffset()));
            } else if (fieldType == Long.class) {
                field.set(target, Bytes.toLong(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            }
        }
    }


    /**
     * 把class的所有属性缓存到MAP中
     *
     * 注意这里小心KEY冲突，冲突就有可能造成错误，命名规则必须为 class.getSimpleName() + fieldName
     *
     * @param clazz
     */
    private static void addClassField(Class clazz) {
        if (!cacheClassMap.containsKey(clazz)) {
            cacheClassMap.put(clazz, clazz);
            for (Field field : HBaseOrder.class.getDeclaredFields()) {
                field.setAccessible(true);
                cacheFieldMap.put(clazz.getSimpleName() + field.getName(), field);
            }
        }
    }

    /**
     * 获取对应class的属性
     * @param clazz
     * @param fieldName
     * @return
     */
    private static Field getClassFiled(Class clazz, String fieldName) {
        addClassField(clazz);
        Class c = cacheClassMap.get(clazz);
        return cacheFieldMap.get(c.getSimpleName() + fieldName);
    }
}
