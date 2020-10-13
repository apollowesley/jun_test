package tom.test;


import java.sql.Connection;
import java.sql.SQLException;

import tom.cocook.jdbc.simple.DBUtil;

public class DemoService {

	public DemoService() throws SQLException {
		Connection conn = DBUtil.getConnection();
		System.out.println("DemoService init---"+conn);
		conn.close();
	}
}
