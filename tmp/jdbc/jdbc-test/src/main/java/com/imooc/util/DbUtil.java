package com.imooc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据库操作工具类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2020/1/6 15:32
 */
public class DbUtil {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DbUtil(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public <T> List<T> executeQuery(String sql, RowMapper<T> rowMapper, Object... params) {
        try {
            //1、获取连接
            Connection connection = this.getConnection();
            //2、创建语句集
            PreparedStatement prepareStatement = this.createPrepareStatement(connection, sql);
            //3、执行语句集
            ResultSet resultSet = this.executeQuery(prepareStatement, params);
            //4、处理结果集
            List<T> result = this.paresResultSet(resultSet, rowMapper);
            //5、关闭结果集
            this.closeResultSet(resultSet);
            //6、关闭语句集
            this.closeStatement(prepareStatement);
            //7、关闭连接
            this.closeConnection(connection);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void closeConnection(Connection conn) throws Exception {
        // TODO: 如果使用连接池，就不是关闭 connection
        conn.close();
    }

    private void closeStatement(PreparedStatement pstm) throws Exception {
        pstm.close();
    }

    private void closeResultSet(ResultSet rs) throws Exception {
        rs.close();
    }

    private <T> List<T> paresResultSet(ResultSet rs, RowMapper<T> rowMapper) throws Exception {
        List<T> result = new ArrayList<>();
        int rowNum = 1;
        while (rs.next()) {
            result.add(rowMapper.mapRow(rs, rowNum++));
        }
        return result;
    }

    private ResultSet executeQuery(PreparedStatement preparedStatement, Object... values) throws Exception {
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i, values[i]);
        }
        return preparedStatement.executeQuery();
    }

    private PreparedStatement createPrepareStatement(Connection conn, String sql) throws Exception {
        return conn.prepareStatement(sql);
    }

    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

}
