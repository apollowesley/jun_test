package com.jake.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

import com.jake.bean.Admin;
import com.jake.bean.Message;


public class DBC {
	//得到所有的messages
	public ArrayList<Message> getAllMess(){
		ArrayList<Message> messages = new ArrayList<Message>();
		DBConn db = new DBConn();
		Connection conn = db.getConn();
		String sql = "select * from mess order by id desc";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Message mess = new Message();
				mess.setId(rs.getInt("id"));
				mess.setHeading(rs.getInt("heading"));
				mess.setName(rs.getString("name"));
				mess.setTitle(rs.getString("title"));
				mess.setContent(rs.getString("content"));
				mess.setQq(rs.getString("qq"));
				mess.setEmail(rs.getString("email"));
				mess.setHttp(rs.getString("http"));
				mess.setMood(rs.getInt("mood"));
				mess.setSex(rs.getInt("sex"));
				mess.setPubtime(rs.getString("pubtime"));
				mess.setRepcontent(rs.getString("repcontent"));
				mess.setReptime(rs.getString("reptime"));
				mess.setIp(rs.getString("ip"));
				messages.add(mess);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	//得到管理员选项
	public Admin getAdminById(int id) throws SQLException{
		Admin admin = new Admin();
		DBConn db = new DBConn();
		Connection conn = db.getConn();
		String sql = "select * from admin where id =?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			admin.setId(rs.getInt("id"));
			admin.setWebtitle(rs.getString("webtitle"));
			admin.setAdminhttp(rs.getString("adminhttp"));
			admin.setAdminname(rs.getString("adminname"));
			admin.setAdminpass(rs.getString("adminpass"));
			admin.setAdminmail(rs.getString("adminmail"));
			admin.setWebadvice(rs.getString("webadvice"));
			admin.setAdminqq(rs.getString("adminqq"));
		}
		ps.close();
		conn.close();
		return admin;
	}
	
	//修改管理员选项
	public void updateAdmin(Admin admin) throws SQLException{
		DBConn db = new DBConn();
		Connection conn = db.getConn();
		String sql = "update admin values(?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, admin.getId());
		ps.setString(2, admin.getWebtitle());
		ps.setString(3, admin.getAdminhttp());
		ps.setString(4, admin.getAdminname());
		ps.setString(5, admin.getAdminpass());
		ps.setString(6, admin.getAdminmail());
		ps.setString(7, admin.getWebadvice());
		ps.setString(7, admin.getAdminqq());
		ps.executeUpdate();
		
		ps.close();
		conn.close();
	}
	
	//得到message对象
	public Message getMessById(int id) throws SQLException{
		Message mess = new Message();
		DBConn db = new DBConn();
		Connection conn = db.getConn();
		String sql = "select * from mess where id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			mess.setId(rs.getInt("id"));
			mess.setHeading(rs.getInt("heading"));
			mess.setName(rs.getString("name"));
			mess.setTitle(rs.getString("title"));
			mess.setContent(rs.getString("content"));
			mess.setQq(rs.getString("qq"));
			mess.setEmail(rs.getString("email"));
			mess.setHttp(rs.getString("http"));
			mess.setMood(rs.getInt("mood"));
			mess.setSex(rs.getInt("sex"));
			mess.setPubtime(rs.getString("pubtime"));
			mess.setRepcontent(rs.getString("repcontent"));
			mess.setReptime(rs.getString("reptime"));
			mess.setIp(rs.getString("ip"));
		}
		ps.close();
		conn.close();
		return mess;
	}
	
	//添加Message
	public void addMess(Message mess) throws SQLException{
		DBConn db = new DBConn();
		Connection conn = db.getConn();
		String sql = "insert into mess(heading,name,title,content,qq,email,http,mood,sex,pubtime,repcontent,reptime,ip) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1,mess.getHeading());
		ps.setString(2,mess.getName());
		ps.setString(3,mess.getTitle());
		ps.setString(4, mess.getContent());
		ps.setString(5 , mess.getQq());
		ps.setString(6, mess.getEmail());
		ps.setString(7, mess.getHttp());
		ps.setInt(8, mess.getMood());
		ps.setInt(9, mess.getSex());
		ps.setString(10, mess.getPubtime());
		ps.setString(11, mess.getRepcontent());
		ps.setString(12, mess.getReptime());
		ps.setString(13, mess.getIp());
		int i = ps.executeUpdate();
		if(i == 1){
			System.out.println("添加message成功");
		}
		ps.close();
		conn.close();
	}
}
