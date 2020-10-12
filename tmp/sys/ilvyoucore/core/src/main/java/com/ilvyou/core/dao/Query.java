package com.ilvyou.core.dao;

import com.ilvyou.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by GuanYuCai on 16/9/18.
 */
public class Query {
    private final int PAGE_SIZE = 10;
    private String table;
    private String columns;
    private Criteria criteria;
    private String order;
    private String alias;
    private int pageNo;
    private int pageSize;
    private List<Query> queries = new ArrayList<Query>();
    Map<String, CommonDao.Table> tables;

    public Query(Class table, String columns) {
        this.table = table.getSimpleName();
        this.columns = columns;
        alias = getAlias(columns);
    }

    public Query(Class table, String columns, String alias) {
        this.table = table.getSimpleName();
        this.columns = columns;
        this.alias = alias;
    }

    public Query where(Criteria criteria) {
        this.criteria = criteria;
        return this;
    }

    public Query order(String order) {
        this.order = order;
        return this;
    }

    public Query leftJoin(Class table, String columns, Criteria on) {
        Query query = new Query(table, columns).where(on);
        query.alias = getAlias(columns);
        queries.add(query);
        return this;
    }

    public Query leftJoin(Class table, String columns, String alias, Criteria on) {
        Query query = new Query(table, columns).where(on);
        query.alias = alias;
        queries.add(query);
        return this;
    }

    public Query page(int pageNo, int pageSize) {
        this.pageNo = pageNo < 1 ? 1 : pageNo;
        this.pageSize = pageSize < 1 ? PAGE_SIZE : pageSize;
        return this;
    }

    public String getSql(Map<String, CommonDao.Table> tables) {
        this.tables = tables;
        StringBuffer sql = new StringBuffer("SELECT DISTINCT ").append(StringUtil.isEmpty(columns) ? "*" : columns.toLowerCase());

        StringBuffer cols = new StringBuffer();
        StringBuffer join = new StringBuffer();
        for (Query q : queries) {
            if (!StringUtil.isEmpty(q.columns)) {
                cols.append(",").append(q.columns.toLowerCase());
            }

            join.append(" LEFT JOIN ").append(tables.get(q.table).getTableName()).append(" ").append(q.alias)
                    .append(" ON ").append(q.criteria.getConditions());
        }
        sql.append(cols).append(" FROM ").append(tables.get(table).getTableName());

        if (!StringUtil.isEmpty(alias)) {
            sql.append(" ").append(alias);
        }

        sql.append(join).append(criteria == null ? "" : criteria.getSql());

        if (!StringUtil.isEmpty(order)) {
            sql.append(" ORDER BY ").append(order);
        }

        if (pageSize > 0 && pageNo > 0) {
            sql.append(" LIMIT " + pageSize).append(" OFFSET " + ((pageNo - 1) * pageSize));
        }
        return sql.toString();
    }

    public Object[] getArgs() {
        List<Object> args = new ArrayList<Object>();

        if (criteria != null) {
            args.addAll(criteria.getArgsList());
        }

        for (Query q : queries) {
            args.addAll(q.criteria.getArgsList());
        }

        return args.toArray();
    }

    private String getAlias(String columns) {
        if (!StringUtil.isEmpty(columns)) {
            int i = columns.indexOf(".");
            if (i > 0) {
                return columns.substring(0, i).trim();
            }
        }

        return "";
    }
}
