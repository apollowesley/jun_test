package com.uei.base;

import org.springframework.stereotype.Component;

/**
 *  
 * @author sun
 *
 */
@Component("UEI_BaseMapper")
public interface BaseMapper<T> {

    /**
     * 获取有效数据数量
     * 
     * @param username
     * @return
     */
    public Integer count(T t);
}
