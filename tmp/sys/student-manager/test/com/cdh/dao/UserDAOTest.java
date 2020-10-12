package com.cdh.dao;


import com.cdh.model.User;

public class UserDAOTest {

    private static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) {
        String sql = "select user_id,log_name,log_pwd,log_status,user_type,create_time from user where user_id = ? ";
        User user = userDAO.queryOne(sql, User.class, 4);
        System.out.println(user);
    }
}