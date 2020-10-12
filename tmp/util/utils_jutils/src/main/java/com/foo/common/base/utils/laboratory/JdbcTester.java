package com.foo.common.base.utils.laboratory;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

import com.foo.common.base.utils.FooUtils;
import com.google.common.collect.Lists;

/**
 * 简单jdbc连接和测试,以配置文件来实现全数据库兼容
 * 
 * @author Steve
 * 
 */
public class JdbcTester {
	public static void main(String[] args) throws Exception {
		// ClassPathResource myPath = new ClassPathResource(
		// "hibernate-itmsPlus250-oracle.properties");
		ClassPathResource myPath = new ClassPathResource(
				"hibernate-itmsPlus34-oracle.properties");

		Properties p = new Properties();

		p.load(myPath.getInputStream());

		Class.forName(p.getProperty("hibernate.connection.driver_class"));

		Connection myConnection = DriverManager.getConnection(
				p.getProperty("hibernate.connection.url"),
				p.getProperty("hibernate.connection.username"),
				p.getProperty("hibernate.connection.password"));

		// doSimpleTask(myConnection);
		doSimpleTask2(myConnection);

		// doInBatch(myConnection);

		myConnection.close();
	}

	private static void doSimpleTask2(Connection conn) throws SQLException,
			IllegalArgumentException, IntrospectionException,
			IllegalAccessException, InvocationTargetException {

		// String searchSql = "Select ITMS_SEQ_ORDER.nextval  from dual ";
		String searchSql = "SELECT DEV_ID FROM ITMS_DEVICE_STATIC WHERE DEV_MAC='AC:4A:FE:2B:AA:9F' AND DEV_TYPE='1' AND ROWNUM=1";

		int x = 0;

		PreparedStatement st = null;
		ResultSet rs = null;
		st = conn.prepareStatement(searchSql);
		rs = st.executeQuery();
		while (rs.next()) {
			x = Integer.parseInt(rs.getString(1));
			break;
		}
		conn.commit();
		rs.close();
		st.close();

		System.out.println(FooUtils.getInteger(x, -1));

	}

	public static void doSimpleTask(Connection conn) throws SQLException,
			IllegalArgumentException, IntrospectionException,
			IllegalAccessException, InvocationTargetException {

		String searchSql = "SELECT * FROM ITMS_ORDER_SERVICE WHERE ORDER_ID=5417 ";

		List<String> list = Lists.newArrayList();
		PreparedStatement st = null;
		ResultSet rs = null;
		st = conn.prepareStatement(searchSql);
		rs = st.executeQuery();
		while (rs.next()) {
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				list.add(rs.getString(i));
			}
			break;
		}
		conn.commit();
		rs.close();
		st.close();

		System.out.println(list.toString());

	}

	public static void doInTransaction(Connection con) throws SQLException {

		Statement batchStmt = null;

		String updateSql1 = "UPDATE GAME SET CN_NAME='fuck' WHERE id='01d6c062-7202-43d7-bf2e-364f9863a5ce'";

		String updateSql2 = "UPDATE ITMS_DEVICE_STATIC SET CUST_ID=0  WHERE DEV_ID=49000";

		try {
			con.setAutoCommit(false);
			batchStmt = con.createStatement();
			batchStmt.addBatch(updateSql1);
			batchStmt.addBatch(updateSql2);
			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				try {
					FooUtils.info("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
				}
			}
		} finally {
			if (batchStmt != null) {
				batchStmt.close();
			}
			con.setAutoCommit(true);
		}
	}

}
