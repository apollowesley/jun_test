package com.sise.school.teach.common;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author idea
 * @data 2018/10/1
 */
public interface BaseDao<T> {

    /**
     * 获取单个对象
     *
     * @param key
     * @param value
     * @return
     */
    T selectOne(@Param("key") String key, @Param("value") Object value);

    /**
     * 插入某个对象
     *
     * @param t
     */
    void insert(T t);

    /**
     * 删除某个对象
     *
     * @param key
     * @param value
     */
    void delete(@Param("key") String key, @Param("value") Object value);

    /**
     * 更新某个对象
     *
     * @param t
     */
    void update(T t);

    /**
     * 查询全部
     *
     * @return
     */
    List<T> selectAll();
}
