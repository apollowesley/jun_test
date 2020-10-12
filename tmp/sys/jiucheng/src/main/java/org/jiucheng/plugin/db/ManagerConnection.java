/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.plugin.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.jiucheng.log.Logger;
import org.jiucheng.orm.util.JdbcUtil;

/**
 * 
 * @author jiucheng
 *
 */
public class ManagerConnection {
    
    private static final Logger log = Logger.getLogger(ManagerConnection.class);
    private static ThreadLocal<Map<DataSource, Connection>> conns = new ThreadLocal<Map<DataSource, Connection>>();
    private static ThreadLocal<Boolean> autoCommit = new ThreadLocal<Boolean>();
    
    public static void setAutoCommit(Boolean val) {
        if(Boolean.FALSE.equals(val)) {
            autoCommit.set(val);
        }else {
            Collection<Connection> ccs = list();
            if(ccs != null) {
                try {
                    for(Connection conn : ccs) {
                        if(conn.getAutoCommit() == false) {
                            conn.setAutoCommit(true);
                        }
                    }
                }catch(SQLException e) {
                    if(log.isErrorEnabled()) {
                        log.error("set true connection auto commit error", e);
                    }
                }
            }
            autoCommit.set(val);
        }
    }
    
    public static void put(DataSource dataSource, Connection conn) {
        if(dataSource == null || conn == null) {
            return;
        }
        Map<DataSource, Connection> mdc = conns.get();
        if(mdc == null) {
            mdc = new ConcurrentHashMap<DataSource, Connection>();
        }
        mdc.put(dataSource, conn);
        conns.set(mdc);
    }
    
    public static Connection get(DataSource dataSource) {
        if(dataSource == null) {
            return null;
        }
        Map<DataSource, Connection> mdc = conns.get();
        Connection conn;
        if(mdc != null) {
            conn = mdc.get(dataSource);
            if(conn != null) {
                setAutoCommit0(conn);
                return conn;
            }
        }
        try {
            conn = dataSource.getConnection();
            if(conn != null) {
                setAutoCommit0(conn);
                put(dataSource, conn);
                return conn;
            }
        }catch (SQLException e) {
            if(log.isErrorEnabled()) {
                log.error("get connection error", e);
            }
        }
        return null;
    }
    
    private static void setAutoCommit0(Connection conn) {
        if(Boolean.FALSE.equals(autoCommit.get())) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                if(log.isErrorEnabled()) {
                    log.error("set false connection auto commit error", e);
                }
            }
        }
    }
    
    public static Collection<Connection> list() {
        Map<DataSource, Connection> mdc = conns.get();
        if(mdc != null) {
            return mdc.values();
        }
        return null;
    }
    
    public static void commit() {
        if(Boolean.FALSE.equals(autoCommit.get())) {
            Collection<Connection> ccs = list();
            if (ccs != null) {
                try {
                    for (Connection conn : ccs) {
                        if (conn.getAutoCommit() == false) {
                            conn.commit();
                        }
                    }
                } catch (SQLException e) {
                    if(log.isErrorEnabled()) {
                        log.error("connection commit error", e);
                    }
                }
            }
        }
    }
    
    public static void rollback() {
        if(Boolean.FALSE.equals(autoCommit.get())) {
            Collection<Connection> ccs = list();
            if(ccs != null) {
                try {
                    for(Connection conn : ccs) {
                        if(conn.getAutoCommit() == false) {
                            conn.rollback();
                        }
                    }
                }catch(SQLException e) {
                    if(log.isErrorEnabled()) {
                        log.error("connection rollback error", e);
                    }
                }
            }
        }
    }
    
    public static void close() {
        Collection<Connection> ccs = list();
        if(ccs != null) {
            for(Connection conn : ccs) {
                JdbcUtil.close(conn);
            }
        }
    }
    
    public static void clear() {
        conns.remove();
        autoCommit.remove();
    }
}
