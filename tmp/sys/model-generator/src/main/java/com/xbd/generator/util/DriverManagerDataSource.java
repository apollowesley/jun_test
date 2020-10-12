package com.xbd.generator.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleConnection;

public class DriverManagerDataSource {

	private String driverClassName;

	private String password;

	private String url;

	private String username;

	public Connection getConnection() throws SQLException {
		if (this.url.indexOf("oracle") > -1) {
			OracleConnection conn = (OracleConnection) DriverManager.getConnection(url, this.username, this.password);
			// conn.setRemarksReporting(true); // 获取注释
			return conn;
		} else {
			// props.put("remarksReporting", true); // mysql 获取注释
			return DriverManager.getConnection(url, this.username, this.password);
		}
	}

	public String getDriverClassName() {
		return this.driverClassName;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUrl() {
		return this.url;
	}

	public String getUsername() {
		return this.username;
	}

	public void setDriverClassName(String driverClassName) throws Exception {
		this.driverClassName = driverClassName.trim();
		try {
			Class.forName(this.driverClassName);
		} catch (ClassNotFoundException ex) {
			throw new Exception("Could not load JDBC driver class [" + this.driverClassName + "]", ex);
		}
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUrl(String url) {
		this.url = url.trim();
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
