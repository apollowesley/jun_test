package com.itmuch.gen.db.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static String dirverClassName;
    private static String url;
    private static String user;
    private static String password;

    public static Connection getConnection(Properties prop) throws IOException {
        dirverClassName = (String) prop.get("db.dirverClassName");
        url = (String) prop.get("db.url");
        user = (String) prop.get("db.user");
        password = (String) prop.get("db.password");

        Connection conn = null;
        try {
            Class.forName(dirverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
