package com.dtdream.rdic.saas.intf;

/**
 * Created by Ozz8 on 2015/07/03.
 */
public interface Converter<F,T> {
    public T convert (F src, Class<T> clazz);
}
