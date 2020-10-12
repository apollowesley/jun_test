package com.cdh.util;

import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class DBUtil {
    private static ResourceBundle rb = ResourceBundle.getBundle("jdbc");

    public static Connection getConnection(){
        Connection con = null;
        try {
            con =  DriverManager.getConnection(rb.getString("mysql.url"),rb.getString("mysql.user"),rb.getString("mysql.pwd"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void closeAll(ResultSet rs, PreparedStatement ps,Connection con){
        try {
            if(Objects.nonNull(rs)){
                rs.close();
            }
            if(Objects.nonNull(ps)){
                ps.close();
            }
            if(Objects.nonNull(con)){
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
