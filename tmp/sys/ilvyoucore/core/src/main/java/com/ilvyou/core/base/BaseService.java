package com.ilvyou.core.base;


import com.ilvyou.core.dao.Criteria;
import com.ilvyou.core.dao.Dao;
import com.ilvyou.core.dao.Query;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by GuanYuCai on 16/9/16.
 */
public abstract class BaseService {

    protected Logger log = Logger.getLogger(getClass());

    protected abstract Dao getDao();

    public <T> T getByPK(Class<? extends T> cls, Object... keys) throws Exception {
        return getDao().getByPK(cls, keys);
    }

    public List<Map<String, Object>> getMapList(Query query) throws Exception {
        return getDao().getMapList(query);
    }

    public Map<String, Object> getMap(Class cls, String columns, Criteria criteria) throws Exception {
        return getDao().getMap(cls, columns, criteria);
    }

    public Map<String, Object> getMap(Query query) throws Exception {
        return getDao().getMap(query);
    }
}
