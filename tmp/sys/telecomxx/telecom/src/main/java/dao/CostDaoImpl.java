package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Cost;
import util.DBUtil;

public class CostDaoImpl implements Serializable, CostDao {

	public List<Cost> findAll() {
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "SELECT * "
					+ "FROM cost ";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			List<Cost> list = new ArrayList<Cost>();
			while(rs.next()) {
				Cost c = new Cost();
				c.setCostId(rs.getInt("cost_id"));
				c.setName(rs.getString("name"));
				c.setBaseDuration(rs.getDouble("base_duration"));
				c.setBaseCost(rs.getDouble("base_cost"));
				c.setUnitCost(rs.getDouble("unit_cost"));
				c.setStatus(rs.getString("status"));
				c.setDescr(rs.getString("descr"));
				c.setCreatime(rs.getTimestamp("creatime"));
				c.setStartime(rs.getTimestamp("startime"));
				list.add(c);
			}
			return list;
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询业务失败" + e);
		} finally {
			DBUtil.closeConnection();
		}
	}

	public void save(Cost c) {
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "INSERT INTO cost "
					+ "(name, base_duration, base_cost, unit_cost, status,  descr, creatime, startime, cost_type ) "
					+ "VALUES "
					+ "(?,?,?,?,'0',?,CURRENT_TIMESTAMP,NULL,?) ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.getName());
			ps.setDouble(2, c.getBaseDuration());
			ps.setDouble(3, c.getBaseCost());
			ps.setDouble(4, c.getUnitCost());
			ps.setString(5, c.getDescr());
			ps.setString(6, c.getCostType());
			
			Integer judge = ps.executeUpdate();
			if(0 != judge) {
				System.out.println("增加资费成功");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection();
		}
	}

}
