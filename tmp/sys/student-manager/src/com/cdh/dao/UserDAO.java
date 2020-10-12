package com.cdh.dao;

import com.cdh.model.User;
import com.cdh.util.DBUtil;
import com.cdh.util.MD5Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends BaseDAO {

    public User login(User user){
        User obj = null;
        con = DBUtil.getConnection();
        String sql = "select user_id,log_name,log_pwd,log_status,user_sex,user_type,create_time from user where log_name=? and log_pwd=? and user_type=? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,user.getLogName());
            ps.setString(2,user.getLogPwd());
            ps.setString(3,user.getUserType());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                obj = User.builder().logName(rs.getString("log_name")).logPwd(rs.getString("log_pwd"))
                        .userId(rs.getInt("user_id")).userSex(rs.getString("user_sex"))
                        .userType(rs.getString("user_type")).logStatus(rs.getInt("log_status"))
                        .createTime(rs.getTimestamp("create_time").toLocalDateTime()).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,ps,con);
        }
        return obj;
    }

    public boolean save(User user){
        String sql = "insert into user(log_name,log_pwd,user_sex,user_type,log_status) values(?,?,?,?,?)";
        return this.add(sql,user);
    }

    public boolean edit(User user){
        String sql = "update user set log_name=?,log_pwd=?,user_sex=?,user_type=? where user_id=? ";
        return this.update(sql,user);
    }

    public boolean del(Object id){
        String sql = "delete from user where user_id = ? ";
        return this.delById(sql,id);
    }

    public List<User> findAll(Class<?> clazz){
        String sql = "select user_id,log_name,log_pwd,user_sex,user_type,create_time,log_status from user ";
        return this.queryList(sql,clazz);
    }

    public User findById(Integer id){
        String sql = "select user_id,log_name,log_pwd,user_sex,user_type,create_time,log_status from user where user_id = ?";
        return this.queryOne(sql,User.class,id);
    }

    public boolean updatePwdByUserId(String newPwdVal, Integer userId) {
        String sql = "update user set log_pwd=? where user_id=?";
        User user = User.builder().userId(userId).logPwd(MD5Util.getMD5(newPwdVal)).build();
        return this.update(sql,user);
    }

    public String findLastLogName(String logName) {
        con = DBUtil.getConnection();
        String sql = "select log_name from user where log_name like ? order by log_name desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,"%"+logName+"%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("log_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,ps,con);
        }
        return null;
    }

    public User findUserByLogName(String logName) {
        String sql = "select user_id,log_name,log_status,user_sex from user where log_name = ?";
        return this.queryOne(sql, User.class, logName);
    }

    public boolean updateStatus(User user) {
        String sql = "update user set log_status=? where user_id=?";
        return this.update(sql,user);
    }
}
