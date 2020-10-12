package com.dtdream.rdic.saas.hibernate;

/**
 * 类型转换通用接口
 * @param <F>   源类型
 * @param <T>   目标类型
 */
public interface HibernateConverter<F,T> {
    /**
     * 通用转换接口
     * @param object    待转换的对象
     * @return          转换后的对象
     */
    public T convert(F object);
}
