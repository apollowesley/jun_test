package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entity.Admin;
import util.DBUtil;

public class AdminDaoImpl implements AdminDao, Serializable {

	public Admin findByAdminCode(String Code) {
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "SELECT * "
					+ "FROM admin_info "
					+ "WHERE admin_code = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Code);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Admin a = new Admin();
				a.setAdminId(rs.getInt("admin_id"));
				a.setAdminCode(rs.getString("admin_code"));
				a.setPassword(rs.getString("password"));
				a.setName(rs.getString("name"));
				a.setTelephone(rs.getString("telephone"));
				a.setEmail(rs.getString("email"));
				a.setEnrolldate(rs.getTimestamp("enrolldate"));
				return a;
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询管理员失败：" + e);
		} finally {
			DBUtil.closeConnection();
		}
		return null;
	}

}
