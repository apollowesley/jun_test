package com.ilvyou.core.dao;

import com.ilvyou.core.util.Util;
import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GuanYuCai on 2016/9/13 0013.
 */
public abstract class CommonDao extends CoreDao implements Dao {

    private final static ThreadLocal<String> local = new ThreadLocal<String>();
    private Map<String, Table> tables;

    protected abstract void setDatasource(DataSource dataSource);
    protected abstract Map<String, Table> getTables();

    @Override
    public void insert(Object obj) throws IllegalAccessException {
        local.set(obj.getClass().getSimpleName());
        super.insert(getTable(), obj);
    }

    @Override
    public void update(Object obj) throws IllegalAccessException {
        local.set(obj.getClass().getSimpleName());
        Criteria condi = new Criteria();
        for (String key : getKeys()){
            condi.add(key + "=?", Util.getter(key, obj));
        }
        super.update(getTable(), obj, condi);
    }

    @Override
    public <T> void deleteByPK(Class<? extends T> cls, Object... keys) {
        local.set(cls.getSimpleName());
        super.delete(getTable(), getPrimaryCriteria(keys));
    }

    @Override
    public <T> void delete(Class<? extends T> cls, Criteria criteria) {
        local.set(cls.getSimpleName());
        super.delete(getTable(), criteria);
    }

    @Override
    public <T> T get(Class<? extends  T> cls, Criteria criteria) throws Exception {
        local.set(cls.getSimpleName());
        Map<String, Object> map = queryForMap(getTable(), null, criteria);
        return Util.fromMap(map, cls);
    }

    @Override
    public <T> T getByPK(Class<? extends T> cls, Object... keys) throws Exception {
        local.set(cls.getSimpleName());
        Map<String, Object> map = queryForMap(getTable(), null, getPrimaryCriteria(keys));
        return Util.fromMap(map, cls);
    }

    @Override
    public <T> Map<String, Object> getMap(Class<? extends T> cls, String columns, Criteria criteria) throws Exception {
        local.set(cls.getSimpleName());
        return queryForMap(getTable(), columns, criteria);
    }

    @Override
    public <T> Map<String, Object> getMap(Query query) throws Exception {
        List<Map<String, Object>> list = getMapList(query);
        return list.size() > 0 ? list.get(0) : new HashMap<String, Object>();
    }

    @Override
    public <T> List<Map<String, Object>> getMapList(Query query) throws Exception {
        if (tables == null){
            tables = getTables();
        }

        return queryForList(query.getSql(tables), query.getArgs());
    }

    private String getTable() {
        if (tables == null){
            tables = getTables();
        }
        return tables.get(local.get()).getTableName();
    }

    private String[] getKeys(){
        if (tables == null){
            tables = getTables();
        }
        return tables.get(local.get()).getPrimaryKey().split(",");
    }

    private Criteria getPrimaryCriteria(Object... keys){
        Criteria criteria = new Criteria();
        int i = 0;
        for (String key : getKeys()){
            criteria.add(key + "=?", keys[i++]);
        }

        return criteria;
    }

    @Data
    public static class Table {
        private String tableName;
        private String primaryKey;

        public Table(String tableName, String primaryKey) {
            this.tableName = tableName;
            this.primaryKey = primaryKey;
        }
    }
}
