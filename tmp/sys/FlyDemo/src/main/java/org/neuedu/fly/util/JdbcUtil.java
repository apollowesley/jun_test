package org.neuedu.fly.util;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/8/16-11:04
 **/
public final class JdbcUtil {

    //解析配置文件
   private static ResourceBundle rb = ResourceBundle.getBundle("jdbc");

    //注册驱动
    static{
        try {
            Class.forName(rb.getString("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(rb.getString("url"),rb.getString("user"),rb.getString("pwd"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭连接   ResultSet   PreparedStatement  Connection
    //关闭原则   先开的后关
    public static void closeAll(ResultSet rs, PreparedStatement ps,Connection con){
        try{
            if(rs!=null){
                rs.close();
            }
            if(ps!=null){
                ps.close();
            }
            if(con!=null){
                con.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
