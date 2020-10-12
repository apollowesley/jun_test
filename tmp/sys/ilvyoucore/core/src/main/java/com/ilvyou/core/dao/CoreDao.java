package com.ilvyou.core.dao;

import com.ilvyou.core.util.StringUtil;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by GuanYuCai on 2016/9/6 0006.
 */
public class CoreDao extends JdbcDaoSupport {

    protected final void insert(String table, Object obj) throws IllegalAccessException {
        if(null == obj){
            return;
        }

        Map<String, Object> values = new HashMap<String, Object>();
        String[] tableFields = getTableFields(table);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields){
            f.setAccessible(true);
            String name = f.getName();

            if(isTableField(name, tableFields)){
                Object v = f.get(obj);
                if (v == null){
                    if (f.getType() == String.class){
                        v = "";
                    }else if (f.getType() == Date.class){
                        v = new Date();
                    }else if (f.getType() == Timestamp.class){
                        v = new Timestamp(System.currentTimeMillis());
                    }else if (f.getType() == Double.class){
                        v = 0.0;
                    }
                }

                if (v != null){
                    values.put(name, v);
                }
            }
        }

        insert(table, values);
    }

    protected final void update(String table, Object obj, Criteria condi) throws IllegalAccessException {
        if(null == obj || condi == null){
            return;
        }

        StringBuffer cols = new StringBuffer();
        List<Object> args = new ArrayList<Object>();
        String dot = "";
        String[] tableFields = getTableFields(table);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields){
            f.setAccessible(true);
            String name = f.getName();

            if(isTableField(name, tableFields)){
                Object v = f.get(obj);

                if (v != null){
                    cols.append(dot).append(name).append("=?");
                    args.add(v);
                    dot = ",";
                }
            }
        }

        if (args.isEmpty()){
            return;
        }

        StringBuffer sql = new StringBuffer("UPDATE ").append(table)
                .append(" SET ").append(cols)
                .append(condi.getSql());
        args.addAll(condi.getArgsList());
        getJdbcTemplate().update(sql.toString(), args.toArray());
    }

    protected final void delete(String table, Criteria condi){
        if (condi == null){
            return;
        }

        StringBuffer sql = new StringBuffer("DELETE FROM ").append(table).append(condi.getSql());
        getJdbcTemplate().update(sql.toString(), condi.getArgs());
    }

    protected final Map<String, Object> queryForMap(String table, String columns, Criteria criteria){
        List<Map<String, Object>> list = queryForList(table, columns, criteria);
        return list.size() > 0 ? list.get(0) : null;
    }

    protected final List<Map<String, Object>> queryForList(String sql, Object... args){
        return getJdbcTemplate().queryForList(sql, args);
    }

    private List<Map<String, Object>> queryForList(String table, String columns, Criteria criteria){
        if (StringUtil.isEmpty(columns)){
            columns = "*";
        }

        String sql = "SELECT DISTINCT " + columns + " FROM " + table + criteria.getSql();
        return getJdbcTemplate().queryForList(sql, criteria.getArgs());
    }

    private String[] getTableFields(String table){
        String sql = "SELECT * FROM " + table + " LIMIT 1";
        SqlRowSet rowSet = getJdbcTemplate().queryForRowSet(sql);
        return rowSet.getMetaData().getColumnNames();
    }

    private boolean isTableField(String field, String[] tableFields){
        for(String str : tableFields){
            if(str.equals(field)) {
                return true;
            }
        }

        return false;
    }

    private void insert(String table, Map<String, Object> data){
        if (data == null || data.size() == 0){
            return;
        }

        StringBuffer sqlName = new StringBuffer();
        StringBuffer sqlValue = new StringBuffer();
        String dot = "";
        List<Object> args = new ArrayList<Object>();
        for (Map.Entry<String, Object> entry : data.entrySet()){
            String fieldName = entry.getKey();
            Object filedValue = entry.getValue();
            if(fieldName == null || filedValue == null){
                break;
            }

            sqlName.append(dot).append(fieldName);
            sqlValue.append(dot).append("?");
            args.add(filedValue);
            dot = ",";
        }

        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ").append(table)
                .append("(").append(sqlName).append(")")
                .append(" VALUES(").append(sqlValue).append(")");

        getJdbcTemplate().update(sql.toString(), args.toArray());
    }
}
