package com.jake.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
	private static String url = "jdbc:mysql://localhost:3306/jake";
	private static String user = "root";
	private static String password = "080910";
	
	
	public Connection getConn(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
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
	public static void main(String[] args) {
		DBConn db = new DBConn();
		Connection conn = db.getConn();
		System.out.println(conn.toString());
	}
}
