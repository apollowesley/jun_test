package org.nature4j.framework.db;


import org.nature4j.framework.helper.ConfigHelper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {
    private static ThreadLocal<Map<String,ConnectionWrapper>> connectionWrapperMapThreadLocal = new ThreadLocal<>();

    public static ConnectionWrapper getConnectionWrapper(String dbName){
        ConnectionWrapper connectionWrapper = null;
        try {
            Map<String,ConnectionWrapper> connectionWrapperMap = connectionWrapperMapThreadLocal.get();
            if (connectionWrapperMap==null){
                connectionWrapperMap = new HashMap<>();
            }
            connectionWrapper = connectionWrapperMap.get(dbName);
            if (connectionWrapper==null){
                DataSource dataSource = DataSourceFactory.getDataSourceMap().get(dbName);
                connectionWrapper = new ConnectionWrapper();
                connectionWrapper.setConnection(dataSource.getConnection());
                connectionWrapper.setDbName(dbName);
                connectionWrapper.setJdbcDriver(ConfigHelper.getJdbcDriver(dbName));
                connectionWrapper.setSequence(0);
                connectionWrapperMap.put(dbName,connectionWrapper);
            }
            connectionWrapperMapThreadLocal.set(connectionWrapperMap);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionWrapper;
    }

    public static String getDriver(String dbName){
        ConnectionWrapper connectionWrapper = getConnectionWrapper(dbName);
        if (connectionWrapper!=null){
            return connectionWrapper.getJdbcDriver();
        }
        return "";
    }

    public static Connection getConn(String dbName){
        return getConnectionWrapper(dbName).getConnection();
    }

    public static void closeConn(String dbName){
        Connection connection = getConn(dbName);
        try {
            if (connection!=null&&!connection.isClosed()){
                connection.close();
                removeConnectionWrapper(dbName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeConnectionWrapper(String dbName){
        Map<String, ConnectionWrapper> connectionWrapperMap = connectionWrapperMapThreadLocal.get();
        if (connectionWrapperMap!=null){
            connectionWrapperMap.remove(dbName);
        }
    }



    public static Connection getSequenceConn(String dbName){
        ConnectionWrapper connectionWrapper = getConnectionWrapper(dbName);
        connectionWrapper.setSequence(connectionWrapper.getSequence()+1);
        return connectionWrapper.getConnection();
    }

   public static void closeSequenceConn(String dbName){
       ConnectionWrapper connectionWrapper = getConnectionWrapper(dbName);
       if (connectionWrapper!=null){
           int sequence = connectionWrapper.getSequence()-1;
           if (sequence==0){
               closeConn(dbName);
           }
       }
   }

}
