package com.tentcoo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.tentcoo.dao.UserDao;
import com.tentcoo.entity.User;


@Repository
public class UseDaoImpl implements UserDao{

	
	
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	HibernateTemplate hibernateTemplate;
	@Resource(name = "mysqlJdbcTemplate")
	JdbcTemplate jdbcTemplate;
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate OjdbcTemplate;
	
	
	
	
	
	@Override
	public boolean addUser(User user) {
		try {
			hibernateTemplate.save(user);
			System.out.println("执行添加");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateUser(User user) {
		try {
			hibernateTemplate.update(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<User> getAllUser() {
		List<User> query = new ArrayList<User>();
		String hql = "from User";
		try{
			query = (List<User>) hibernateTemplate.find(hql);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		if(query.size()==0){
			return null;
		}else{
			return query;
		}
	}

	@Override
	public User getUserByNo(String sno) {
		List<User> query = new ArrayList<User>();
		String hql = "from User where userNo = ?";
		try {
			query = (List<User>) hibernateTemplate.find(hql, sno);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (query.size() == 0) {
			return null;
		} else {
			return query.get(0);
		}
	}

	@Override
	public User getUserByCode(String code) {
		List<User> query=new ArrayList<User>();
		String hql = "from User where userCode = ?";
		try {
			query = (List<User>) hibernateTemplate.find(hql, code);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (query.size() == 0) {
			return null;
		} else {
			return query.get(0);
		}
	}

}
