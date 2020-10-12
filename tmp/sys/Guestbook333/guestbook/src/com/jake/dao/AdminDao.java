package com.jake.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jake.bean.Message;

public class AdminDao {
	// 验证管理员账号密码
	public boolean validate(String admins, String pass) throws SQLException {
		boolean b = false;
		DBConn db = new DBConn();
		Connection conn = db.getConn();
		String sql = "select * from admin where adminname = ? and adminpass =?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, admins);
		ps.setString(2, pass);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			b = true;
		}
		ps.close();
		conn.close();
		return b;
	}

	// 删除message
	public void deleteMess(int id) throws SQLException {
		DBConn db = new DBConn();
		Connection conn = db.getConn();
		String sql = "delete from mess where id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		int i = ps.executeUpdate();
		if (i == 1) {
			System.out.println("删除message成功");
		}
		ps.close();
		conn.close();
	}

	// 修改message
	public void updateMess(Message mess) throws SQLException {
		DBConn db = new DBConn();
		Connection conn = db.getConn();
		String sql = "update mess set heading=? , name=? , title=?,content=?,qq=?,email=?,http=?,mood=?,sex=?,pubtime=?,repcontent=?,reptime=?,ip=? where id =?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, mess.getHeading());
		ps.setString(2, mess.getName());
		ps.setString(3, mess.getTitle());
		ps.setString(4, mess.getContent());
		ps.setString(5, mess.getQq());
		ps.setString(6, mess.getEmail());
		ps.setString(7, mess.getHttp());
		ps.setInt(8, mess.getMood());
		ps.setInt(9, mess.getSex());
		ps.setString(10, mess.getPubtime());
		ps.setString(11, mess.getRepcontent());
		ps.setString(12, mess.getReptime());
		ps.setString(13, mess.getIp());
		ps.setInt(14, mess.getId());
		int i = ps.executeUpdate();
		if (i == 1) {
			System.out.println("修改message对象成功");
		}
		ps.close();
		conn.close();
	}
}
