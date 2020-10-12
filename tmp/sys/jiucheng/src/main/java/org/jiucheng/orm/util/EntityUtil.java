package org.jiucheng.orm.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jiucheng.orm.DataAccessException;
import org.jiucheng.orm.Sql;
import org.jiucheng.orm.dialect.Dialect;

public class EntityUtil {

    public static <T> void saveAll(Dialect dialect, Connection conn,
            List<T> entities) {
        for (T entity : entities) {
            save(dialect, conn, entity);
        }
    }

    public static <T> Serializable save(Dialect dialect, Connection conn,
            T entity) {
        Sql sh = new Sql();
        dialect.forSave(entity, sh);
        return JdbcUtil.save(conn, sh);
    }

    public static <T> Serializable saveOrUpdate(Dialect dialect,
            Connection conn, T entity) {
        Object id = TableUtil.getPrimayKeyValue(entity);
        if (null == id) {
            return save(dialect, conn, entity);
        }
        Sql sh = new Sql();
        dialect.forFindById(entity, sh);
        List<T> listT = list(dialect, conn, entity);
        if (listT.size() > 0) {
            update(dialect, conn, entity);
            return (Serializable) id;
        } else {
            TableUtil.setPrimayKeyValue(entity, null);
            return save(dialect, conn, entity);
        }
    }

    public static <T> boolean update(Dialect dialect, Connection conn, T entity) {
        Sql sh = new Sql();
        dialect.forUpdateById(entity, sh);
        int count = JdbcUtil.update(conn, sh);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public static <T> int delete(Dialect dialect, Connection conn, T entity) {
        Sql sh = new Sql();
        dialect.forDelete(entity, sh);
        return JdbcUtil.delete(conn, sh);
    }

    public static <T> List<T> list(Dialect dialect, Connection conn, T entity) {
        Sql sh = new Sql();
        dialect.forFind(entity, sh);
        ResultSet rs = JdbcUtil.getResultSet(conn, sh);
        return (List<T>) resultSetToListEntity(dialect, rs, entity.getClass());
    }

    public static <T> List<T> resultSetToListEntity(Dialect dialect,
            ResultSet rs, Class<T> clazz) throws DataAccessException {
        if (null == rs) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        try {
            String[] metaDatas = metaDataOfResultSet(rs);
            while (rs.next()) {
                result.add(resultSetToEntity1(metaDatas, dialect, rs, clazz));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return result;
    }

    public static <T> T resultSetToEntity(Dialect dialect, ResultSet rs,
            Class<T> clazz) {
        try {
            if (rs.isBeforeFirst() || rs.isAfterLast()) {
                return null;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        String[] metaDatas = metaDataOfResultSet(rs);
        return resultSetToEntity1(metaDatas, dialect, rs, clazz);
    }

    private static <T> T resultSetToEntity1(String[] metaDatas,
            Dialect dialect, ResultSet rs, Class<T> clazz) {
        try {
            int count = metaDatas.length;
            String colName;
            Object obj = clazz.newInstance();
            Field field;
            for (int i = 1; i <= count; i++) {
                colName = metaDatas[i - 1];
                field = TableUtil.getField(clazz,
                        dialect.forColumnLabel(colName));
                if (null != field) {
                    field.set(obj, rs.getObject(i));
                }
            }
            return (T) obj;
        } catch (InstantiationException e) {
            throw new DataAccessException(e);
        } catch (IllegalAccessException e) {
            throw new DataAccessException(e);
        } catch (IllegalArgumentException e) {
            throw new DataAccessException(e);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private static String[] metaDataOfResultSet(ResultSet rs)
            throws DataAccessException {
        String[] result = null;
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            result = new String[cols];
            for (int i = 1; i <= cols; i++) {
                result[i - 1] = rsmd.getColumnLabel(i);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return result;
    }
}
