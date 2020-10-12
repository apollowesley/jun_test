package org.pan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SqlReader {
    static Logger LOGGER = LoggerFactory.getLogger(SqlReader.class);

    public static String fomart = "jdbc:jtds:sqlserver://ip:port;DatabaseName=databaseName";
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
    public static String ip = "127.0.0.1";
    public static String port = "1433";
    public static String username = "sa";
    public static String password = "1";
    public static String databaseName = "onecardTable";

    public static Connection getConnection() throws Exception {
        String url = SqlReader.fomart.replace("ip", ip).replace("port", port).replace("databaseName",databaseName);
        return DriverManager.getConnection(url, username, password);
    }

    public static List<String> getHeaderName(Path path){
        List<String> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = new String(Files.readAllBytes(path),"utf-8");
            LOGGER.debug("load sql:{}",sql);
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = resultSet.getMetaData().getColumnName(i);
                LOGGER.debug("read columnName:{}",columnName);
                result.add(columnName);
            }
        } catch (Exception e) {
            LOGGER.error("获取列表名称时发生错误",e);
        } finally {
            close(connection,preparedStatement,resultSet);
        }
        return result;
    }

    public static List<String[]> getRows(Path path) {
        List<String[]> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = new String(Files.readAllBytes(path),"utf-8");
            LOGGER.debug("load sql:{}",sql);
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            int columnSize = resultSet.getMetaData().getColumnCount();

            Map<Integer,Integer> columnTypeMap = new HashMap<>();
            for (int i = 1; i <= columnSize; i++) {
                columnTypeMap.put(i,resultSet.getMetaData().getColumnType(i));
            }

            while (resultSet.next()) {
                String[] row = parseRow(resultSet,columnSize,columnTypeMap);
                result.add(row);
            }
        } catch (Exception e) {
            LOGGER.error("获取列表名称时发生错误",e);
        } finally {
            close(connection,preparedStatement,resultSet);
        }
        return result;
    }

    public static String[] parseRow(ResultSet resultSet,Integer columnSize,Map<Integer,Integer> columnTypeMap){
        String[] row = new String[columnSize];
        for (int i = 1; i <= columnSize; i++) {
            row[i-1] = parseColumn(resultSet, i, columnTypeMap.get(i));
        }
        return row;
    }

    public static String parseColumn(ResultSet resultSet,Integer columnIndex,Integer columnType){
        String result = "";
        try {
            switch (columnType) {
                case Types.BIGINT:
                    result = String.valueOf(resultSet.getLong(columnIndex));
                    break;
                case Types.CHAR:
                    result = String.valueOf(resultSet.getString(columnIndex));
                    break;
                case Types.VARCHAR:
                    result = resultSet.getString(columnIndex);
                    break;
                case Types.DECIMAL:
                    result = String.valueOf(resultSet.getBigDecimal(columnIndex));
                    break;
                case Types.DATE:
                    result = simpleDateFormat.format(resultSet.getDate(columnIndex));
                    break;
                case Types.INTEGER:
                    result = String.valueOf(resultSet.getInt(columnIndex));
                    break;
                case Types.NUMERIC:
                    double aDouble = resultSet.getDouble(columnIndex);
                    result = String.valueOf(aDouble);
                    break;
                default:
                    throw new RuntimeException("未知的数据类型:" + columnType);
            }
            LOGGER.debug("read column value :{}",result);
        } catch (SQLException e) {
            LOGGER.error("获取列数据时发生错误",e);
        }
        return result;
    }

    public static void close(AutoCloseable... closeables) {
        for (AutoCloseable closeable : closeables) {
            if (closeable == null) {
                continue;
            }
            try {
                closeable.close();
            } catch (Exception e) {
                LOGGER.error("关闭连接对象时发生错误",e);
            }
        }
    }

}
