package com.foo.common.base.utils;

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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.core.io.ClassPathResource;

import com.foo.common.base.utils.laboratory.OracleUserTabColumnsModel;
import com.google.common.collect.Lists;

/**
 * 获得所有hibernate和jdbc的连接
 * 
 * @author Steve
 * 
 */
public class DataSourceHelper {

	private static Connection jdbcConnection_250;

	public static Session getHibernateSession_Itms_250() throws Exception {
		ClassPathResource myPath = new ClassPathResource(
				"hibernate-itmsPlus250-oracle.properties");

		Properties p = new Properties();
		p.load(myPath.getInputStream());

		Configuration configuration = new Configuration().setProperties(p);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		SessionFactory myFactory = configuration.addAnnotatedClass(
				OracleUserTabColumnsModel.class).buildSessionFactory(
				serviceRegistry);
		Session session = myFactory.openSession();
		return session;
	}

	public static Connection getJdbcConnection_Itms_250() throws Exception {
		if (jdbcConnection_250 != null) {
			return jdbcConnection_250;
		}
		ClassPathResource myPath = new ClassPathResource(
				"hibernate-itmsPlus250-oracle.properties");

		Properties p = new Properties();

		p.load(myPath.getInputStream());

		Class.forName(p.getProperty("hibernate.connection.driver_class"));

		jdbcConnection_250 = DriverManager.getConnection(
				p.getProperty("hibernate.connection.url"),
				p.getProperty("hibernate.connection.username"),
				p.getProperty("hibernate.connection.password"));
		return jdbcConnection_250;
	}

	public static void closeJdbcConnection_Itms_250() throws Exception {
		if (jdbcConnection_250 != null) {
			jdbcConnection_250.close();
		}
		jdbcConnection_250 = null;
	}

	public static void main(String[] args) throws Exception {
		ClassPathResource myPath = new ClassPathResource(
				"hibernate-itmsPlus250-oracle.properties");

		Properties p = new Properties();

		p.load(myPath.getInputStream());

		Class.forName(p.getProperty("hibernate.connection.driver_class"));

		Connection myConnection = DriverManager.getConnection(
				p.getProperty("hibernate.connection.url"),
				p.getProperty("hibernate.connection.username"),
				p.getProperty("hibernate.connection.password"));

		doSimpleTask(myConnection);
		doSimpleTask2(myConnection);

		// doInBatch(myConnection);

		myConnection.close();
	}

	private static void doSimpleTask2(Connection conn) throws SQLException,
			IllegalArgumentException, IntrospectionException,
			IllegalAccessException, InvocationTargetException {

		String searchSql = "Select ITMS_SEQ_ORDER.nextval  from dual ";

		int x = 0;

		PreparedStatement st = null;
		ResultSet rs = null;
		st = conn.prepareStatement(searchSql);
		rs = st.executeQuery();
		while (rs.next()) {
			x = rs.getInt(1);
			break;
		}
		conn.commit();
		rs.close();
		st.close();

		System.out.println(x);

	}

	private static void doSimpleTask(Connection conn) throws SQLException,
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
			// int[] count = batchStmt.executeBatch();
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
