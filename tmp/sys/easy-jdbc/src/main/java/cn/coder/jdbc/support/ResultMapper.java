package cn.coder.jdbc.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface ResultMapper<T> {
	
	Statement makeStatement(Connection conn) throws SQLException;
	
	T doStatement(Statement stmt) throws SQLException;

}
