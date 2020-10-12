package org.jiucheng.orm;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.jiucheng.exception.UncheckedException;

public abstract class PoolDataSource implements DataSource {
    
    private Object lock = new Object();
    private volatile boolean inited;
    
    private DataSourceCollection dataSourceCollection = new DataSourceCollection();
    private List<PoolConnection> conns = new Vector<PoolConnection>();
    private CloseEventListener closeEventListener = new CloseEventListenerEx();
    
    public abstract String getDriverClass();

    public abstract String getUrl();

    public abstract String getUsername();

    public abstract String getPassword();
    
    public abstract int getMinActive();
    
    public abstract int getMaxActive();
    
    public long getMaxWait() {
        return 3000;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        
    }

    public void setLoginTimeout(int seconds) throws SQLException {

    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public Connection getConnection() throws SQLException {
        return getConnection(getUsername(), getPassword());
    }

    public Connection getConnection(String username, String password) throws SQLException {
        init();
        return getPoolConnection(getUrl(), username, password);
    }
    
    private PoolConnection getPoolConnection(String url, String username, String password) throws SQLException {
        PoolConnection poolConnection = getPoolConnection0(url, username, password);
        if(poolConnection != null) {
            return poolConnection;
        }
        synchronized(this) {
            try {
                wait(getMaxWait());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        poolConnection = getPoolConnection0(url, username, password);
        if(poolConnection != null) {
            return poolConnection;
        }
        throw new DataAccessException("Connection Pool maxActive");
    }
    
    private PoolConnection getPoolConnection0(String url, String username, String password) throws SQLException {
        for(PoolConnection poolConnection : conns) {
            if(poolConnection.lock.tryLock()) {
                try {
                    if(!poolConnection.used() && poolConnection.getUsedNum() < 256) {
                        poolConnection.use();
                        return poolConnection;
                    }
                }finally {
                    poolConnection.lock.unlock();
                }
            }
        }
        if(conns.size() < getMaxActive()) {
            PoolConnection poolConnection = getNewPoolConnection(getUrl(), getUsername(), getPassword());
            poolConnection.use();
            conns.add(poolConnection);
            return poolConnection;
        }
        if(dataSourceCollection.lock.tryLock()) {
            try {
                dataSourceCollection.notifyAll();
            }finally {
                dataSourceCollection.lock.unlock();
            }
        }
        return null;
    }
    
    private PoolConnection getNewPoolConnection(String url, String username, String password) throws SQLException {
        PoolConnection poolConnection = new PoolConnection(DriverManager.getConnection(url, username, password));
        poolConnection.addCloseEventListener(closeEventListener);
        return poolConnection;
    }

    private void init() {
        if(inited) {
            return;
        }
        synchronized (lock) {
            if(inited) {
                return;
            }
            dataSourceCollection.start();
            try {
                Class<?> clazz = Class.forName(getDriverClass());
                DriverManager.registerDriver((Driver)clazz.newInstance());
                initMinActive();
            } catch (ClassNotFoundException e) {
                throw new UncheckedException(e);
            } catch (InstantiationException e) {
                throw new UncheckedException(e);
            } catch (IllegalAccessException e) {
                throw new UncheckedException(e);
            } catch (SQLException e) {
                throw new UncheckedException(e);
            }finally {
                inited = true;
            }
        }
    }
    
    private synchronized void initMinActive() throws SQLException {
        int minActive = getMinActive();
        for(int i = 0; i < minActive; i++) {
            conns.add(getNewPoolConnection(getUrl(), getUsername(), getPassword()));
        }
    }
    
    List<PoolConnection> getConns() {
        return conns;
    }
    
    private class DataSourceCollection extends Thread {
        Lock lock = new ReentrantLock();
        
        public DataSourceCollection() {
            setDaemon(true);
        }
        
        @Override
        public void run() {
            while(true) {
                List<PoolConnection> conns = getConns();
                for(PoolConnection poolConnection : conns) {
                    if(poolConnection.lock.tryLock()) {
                        try {
                            if(!poolConnection.used()) {
                                if(poolConnection.getUsedNum() >= 256) {
                                    conns.remove(poolConnection);
                                    poolConnection.removeCloseEventListener(closeEventListener);
                                    try {
                                        poolConnection.realClose();
                                    }catch(SQLException e) {
                                    }
                                    break;
                                }
                                try {
                                    if(!poolConnection.isValid(10)) {
                                        conns.remove(poolConnection);
                                        poolConnection.removeCloseEventListener(closeEventListener);
                                        break;
                                    }
                                }catch(SQLException e1) {
                                    conns.remove(poolConnection);
                                    poolConnection.removeCloseEventListener(closeEventListener);
                                    break;
                                }
                            }
                        }finally {
                            poolConnection.lock.unlock();
                        }
                    }
                }
                synchronized (this) {
                    try {
                        this.wait(5 * 60 * 1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
        
        @Override
        protected void finalize() throws Throwable {
            List<PoolConnection> conns = getConns();
            for(PoolConnection poolConnection : conns) {
                try {
                    if(!poolConnection.isRealClosed()) {
                        poolConnection.realClose();
                    }
                }catch(Throwable e) {
                }
            }
        }
    }
    
    private class CloseEventListenerEx implements CloseEventListener {
        public void closeFailure(CloseEvent closeEvent) {
            PoolConnection poolConnection = (PoolConnection) closeEvent.getSource();
            if(poolConnection.lock.tryLock()) {
                try {
                    getConns().remove(poolConnection);
                    poolConnection.removeCloseEventListener(closeEventListener);
                }finally {
                    poolConnection.lock.unlock();
                }
            }
        }
    }
}
