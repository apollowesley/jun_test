package com.jfast.web.common.core.base;

import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/10/2 16:20
 */
public interface BaseMapper<T> {

    T queryOne(T entity);

    Map queryOne(Map params);

    List<T> queryList(T entity);

    List<Map> queryList(Map params);

    int save(T entity);

    int save(Map params);

    int update(T entity);

    int update(Map params);
}
