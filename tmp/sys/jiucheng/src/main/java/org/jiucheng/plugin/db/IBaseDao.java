package org.jiucheng.plugin.db;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.jiucheng.orm.Sql;
import org.jiucheng.orm.dialect.Dialect;

public interface IBaseDao {
    public abstract Dialect getDialect();
    public abstract DataSource getDataSource();
    public <T> Serializable save(T entity);
    public Serializable saveBySQL(Sql sh);
    public <T> Serializable saveOrUpdate(T entity);
    public <T> boolean update(T entity);
    public boolean updateBySQL(Sql sh);
    public <T> int delete(T entity);
    public boolean deleteBySQL(Sql sh);
    public <T> List<T> list(T entity);
	@SuppressWarnings("rawtypes")
    public List<Map> listBySQL(Sql sh);
    public <T> List<T> listBySQL(Class<T> clazz, Sql sh);
    public Connection getConn();
}
