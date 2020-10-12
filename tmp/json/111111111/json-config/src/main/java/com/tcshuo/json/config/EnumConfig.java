/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import java.util.List;
import org.apache.commons.beanutils.MethodUtils;

/**
 *
 * @author tengda
 */
public class EnumConfig extends BeanConfig {

    public static final String JAVA_ENUM_NAME = "javaEnumName";
    
    public static final String DECLARING_CLASS = "declaringClass";

    protected Object enumObject;

    public EnumConfig(Object target) {
        super(target);
        this.enumObject = target;
    }

    /**
     * 获取字段值
     *
     * @param name
     * @return
     */
    protected Object get(String name) {
        if (JAVA_ENUM_NAME.equals(name)) {
            try {
                return MethodUtils.invokeMethod(this.enumObject, "name", new Object[0]);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        
        if (DECLARING_CLASS.equals(name)) {
             Class clazz = (Class)super.get(name);
             return clazz.getName();
        }
        
        return super.get(name);

    }

    /**
     * 获取所有字段
     *
     * @return
     */
    protected List<String> getPropertys() {
        List<String> list = super.getPropertys();
        list.add(JAVA_ENUM_NAME);
        return list;

    }

    /**
     * 获取字段类型
     *
     * @param name
     * @return
     */
    protected Class getType(String name) {
        if (JAVA_ENUM_NAME.equals(name)) {
            return String.class;
        }
        if (DECLARING_CLASS.equals(name)) {
            return String.class;
        }
        return super.getType(name);

    }

}
