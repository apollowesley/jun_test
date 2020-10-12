package com.zhoux.freemarker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConnectionDB {
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectionDB.class);
	
	private  String Driver="com.mysql.jdbc.Driver";
	
	private  String Url="jdbc:mysql://localhost:3306/freemarker";
	
	private  String UserName="root";
	
	private  String password="";
	
	private Connection connection;
	
	private void conDB() throws Exception{
		try {
			Class.forName(this.Driver);
			this.connection= DriverManager.getConnection(this.Url, this.UserName,this.password); 
		} catch (ClassNotFoundException e) {
			logger.error("没有找到对应得驱动");
			throw new  Exception();
		}
	}
	public static Connection getConnection() throws Exception{
		ConnectionDB connectionDB=new ConnectionDB();
		connectionDB.conDB();
		return connectionDB.connection;
	}
	
	public static ResultSetMetaData getTableColumn(String tableName) throws Exception{
		Connection connection=ConnectionDB.getConnection();
		String sql="select * from "+tableName;
		if(null==connection){
			throw new Exception("系统繁忙，无法获取session，请稍后再试！");
		}
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		ResultSetMetaData rsmd = statement.getMetaData();
		return rsmd;
	}
	@Test
	public void runTest() throws Exception{
		getTableColumn("user");
	}
	
}
