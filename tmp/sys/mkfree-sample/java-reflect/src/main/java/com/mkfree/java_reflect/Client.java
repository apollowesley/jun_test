package com.mkfree.java_reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author oyhk
 * @date 2019-04-23 18:10
 **/
public class Client {
    public static void main(String[] args) throws Exception {

        User user = new User();
        user.setAccount("hk");
        user.setAge(18);

        Class clazz = User.class;
        Class superClass = clazz.getSuperclass();
        System.out.println(superClass);
        Field[] superClassFields = superClass.getDeclaredFields();
        for (int i = 0; i < superClassFields.length; i++) {
            Field field = superClassFields[i];
            field.setAccessible(true);
            HbaseColumn hbaseColumn = field.getAnnotation(HbaseColumn.class);
            if (hbaseColumn != null) {
                System.out.println(hbaseColumn);
            }
        }

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            HbaseColumn hbaseColumn = field.getAnnotation(HbaseColumn.class);
            if (hbaseColumn != null) {
                System.out.println(hbaseColumn);
            }
        }


    }
}
