package com.yutong.smart.util;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;


/**
 * 数据库查询工具类
 * @author yuxiangtong
 *
 */
public class DBUtils<T> {

    private static final Logger LOGGER = Logger.getLogger(DBUtils.class);


    /**
     * 获取 Map<String, Object>
     * @param conn
     *        数据库连接对象
     * @param sql
     * @param closeConn
     *        查询完成后是否关闭连接
     * @return 成功:Map<String, Object> <br/>
     *         失败:null
     */
    public static Map<String, Object> queryMap(Connection conn, String sql,
        boolean closeConn) {
        return queryMap(conn, sql, null, closeConn);
    }


    /**
     * 获取 Map<String, Object>
     * @param conn
     *        数据库连接对象
     * @param sql
     * @param params
     * @param closeConn
     *        查询完成后是否关闭连接
     * @return 成功:Map<String, Object> <br/>
     *         失败:null
     */
    public static Map<String, Object> queryMap(Connection conn, String sql,
        Object[] params, boolean closeConn) {
        Map<String, Object> map = null;
        QueryRunner queryRunner = new QueryRunner();
        try {
            map = queryRunner.query(conn, sql, new MapHandler(), params);
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            if (closeConn == true) {
                DbUtils.closeQuietly(conn);
            }
        }
        return map;
    }


    /**
     * 获取 List<Map<String, Object>>
     * @param conn
     *        数据库连接对象
     * @param sql
     * @param closeConn
     *        查询完成后是否关闭连接
     * @return 成功:List<Map<String, Object>> <br/>
     *         失败:null
     */
    public static List<Map<String, Object>> queryMapList(Connection conn,
        String sql, boolean closeConn) {
        return queryMapList(conn, sql, null, closeConn);
    }


    /**
     * 获取 List<Map<String, Object>>
     * @param conn
     *        数据库连接对象
     * @param sql
     * @param params
     * @param closeConn
     *        查询完成后是否关闭连接
     * @return 成功:List<Map<String, Object>> <br/>
     *         失败:null
     */
    public static List<Map<String, Object>> queryMapList(Connection conn,
        String sql, Object[] params, boolean closeConn) {
        List<Map<String, Object>> mapList = null;
        QueryRunner queryRunner = new QueryRunner();
        try {
            mapList = queryRunner.query(conn, sql, new MapListHandler());
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            if (closeConn == true) {
                DbUtils.closeQuietly(conn);
            }
        }
        return mapList;
    }


    public static long queryCount(Connection conn, String sql,
        boolean closeConn) {
        return queryCount(conn, sql, null, closeConn);
    }


    public static long queryCount(Connection conn, String sql, Object[] params,
        boolean closeConn) {
        QueryRunner queryRunner = new QueryRunner();
        long count = 0;
        try {
            Object object =
                    queryRunner.query(conn, sql, new ScalarHandler<Object>(1));
            count = Long.valueOf(object.toString());
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            if (closeConn == true) {
                DbUtils.closeQuietly(conn);
            }
        }
        return count;
    }


    public T queryT(Connection conn, String sql, Class<T> clazz,
        boolean closeConn) {
        return queryT(conn, sql, null, clazz, closeConn);
    }

    public T queryT(Connection conn, String sql, Object[] params,
        Class<T> clazz, boolean closeConn) {
        T classbuff = null;
        QueryRunner queryRunner = new QueryRunner();
        try {
            classbuff = queryRunner.query(conn, sql, new BeanHandler<T>(clazz),
                    params);
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            if (closeConn == true) {
                DbUtils.closeQuietly(conn);
            }
        }
        return classbuff;
    }


    public List<T> queryTList(Connection conn, String sql, Class<T> clazz,
        boolean closeConn) {
        return queryTList(conn, sql, null, clazz, closeConn);
    }


    public List<T> queryTList(Connection conn, String sql, Object[] params,
        Class<T> clazz, boolean closeConn) {
        List<T> classListBuff = null;
        QueryRunner queryRunner = new QueryRunner();
        try {
            classListBuff = queryRunner.query(conn, sql,
                    new BeanListHandler<T>(clazz), params);
        }
        catch (Exception e) {
            LOGGER.error(e);
        }
        finally {
            if (closeConn == true) {
                DbUtils.closeQuietly(conn);
            }
        }
        return classListBuff;
    }
}
