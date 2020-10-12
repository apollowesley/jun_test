/**
 * 
 */
package com.sql2java.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sql2java.prop.PropUtils;

/**
 * @author Viken
 * 
 */
public class JdbcUtils {
    protected static final String PROPERTIES = "properties/datasource.properties";
    PropUtils PROPERTIES_UTILS = new PropUtils(PropUtils.getResource(PROPERTIES));

    String jdbcString = PROPERTIES_UTILS.getValue("jdbc.url");
    String dbDriver = PROPERTIES_UTILS.getValue("jdbc.driverClassName");
    String user = PROPERTIES_UTILS.getValue("jdbc.username");
    String upwd = PROPERTIES_UTILS.getValue("jdbc.password");

    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(dbDriver);
            con = DriverManager.getConnection(jdbcString, user, upwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public void closeConnection(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
