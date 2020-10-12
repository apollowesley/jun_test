package com.ilvyou.core.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by GuanYuCai on 2016/9/13 0013.
 */
public interface Dao {
    public void insert(Object obj) throws IllegalAccessException;

    public void update(Object obj) throws IllegalAccessException;

    public <T> void deleteByPK(Class<? extends T> cls, Object... keys);

    public <T> void delete(Class<? extends T> cls, Criteria criteria);

    public <T> T get(Class<? extends T> cls, Criteria criteria) throws Exception;

    public <T> T getByPK(Class<? extends T> cls, Object... key) throws Exception;

    public <T> Map<String, Object> getMap(Class<? extends T> cls, String columns, Criteria criteria) throws Exception;

    public <T> Map<String, Object> getMap(Query query) throws Exception;

    public <T> List<Map<String, Object>> getMapList(Query query) throws Exception;
}
