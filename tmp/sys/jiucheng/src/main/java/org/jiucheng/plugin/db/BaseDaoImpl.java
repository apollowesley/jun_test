package org.jiucheng.plugin.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.jiucheng.orm.Sql;
import org.jiucheng.orm.dialect.Dialect;
import org.jiucheng.orm.util.EntityUtil;
import org.jiucheng.orm.util.JdbcUtil;

public abstract class BaseDaoImpl implements IBaseDao {
    
    public abstract Dialect getDialect();
    
    public abstract DataSource getDataSource();
    
    public <T> Serializable save(T entity) {
        return EntityUtil.save(getDialect(), getConn(), entity);
    }
    
    public <T> Serializable saveOrUpdate(T entity) {
        return EntityUtil.saveOrUpdate(getDialect(), getConn(), entity);
    }
    
    public <T> boolean update(T entity) {
        return EntityUtil.update(getDialect(), getConn(), entity);
    }

    public <T> int delete(T entity) {
        return EntityUtil.delete(getDialect(), getConn(), entity);
    }

    public <T> List<T> list(T entity) {
        return EntityUtil.list(getDialect(), getConn(), entity);
    }
    
    public Connection getConn() {
        return ManagerConnection.get(getDataSource());
    }

    public Serializable saveBySQL(Sql sh) {
        return JdbcUtil.save(getConn(), sh);
    }

    public boolean updateBySQL(Sql sh) {
        if(JdbcUtil.update(getConn(), sh) > 0 ) {
            return true;
        }
        return false;
    }

    public boolean deleteBySQL(Sql sh) {
        if(JdbcUtil.delete(getConn(), sh) > 0) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public List<Map> listBySQL(Sql sh) {
        return JdbcUtil.list(getConn(), sh);
    }
    
    public <T> List<T> listBySQL(Class<T> clazz, Sql sh) {
    	ResultSet rs = JdbcUtil.getResultSet(getConn(), sh);
    	return EntityUtil.resultSetToListEntity(getDialect(), rs, clazz);
    }
}
