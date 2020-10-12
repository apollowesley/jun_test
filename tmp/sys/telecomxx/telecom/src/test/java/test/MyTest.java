package test;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.junit.Test;

import dao.AdminDaoImpl;
import dao.CostDao;
import dao.CostDaoImpl;
import entity.Admin;
import entity.Cost;
import util.DBUtil;

public class MyTest implements Serializable {
	
	@Test
	public void testDBUtil() {
		try {
			Connection conn = DBUtil.getConnection();
			Statement st = conn.createStatement();
			String sql = "SELECT * "
					+ "FROM cost ";
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				System.out.println("配置文件没有问题");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
	}

	@Test
	public void testCostDaoImpl_findAll() {
		CostDao cd = new CostDaoImpl();
		List<Cost> list = cd.findAll();
		for(Cost c : list) {
			System.out.println(c.getName());
		}
	}
	
	@Test
	public void testCostDaoImpl_save() {
		Cost c = new Cost();
		c.setName("warrior");
		c.setBaseDuration(1000.0);
		c.setBaseCost(10.0);
		c.setUnitCost(1.0);
		c.setDescr("这个有点贵");
		c.setCostType("1");
		new CostDaoImpl().save(c);
	}
	
	@Test
	public void testAdminDaoImpl_find() {
		Admin a = new AdminDaoImpl().findByAdminCode("caocao");
		System.out.println(a.getAdminCode());
		System.out.println(a.getPassword());
		System.out.println(a.getName());
	}
}





















