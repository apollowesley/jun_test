package com.tentcoo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.tentcoo.dao.MessageDao;
import com.tentcoo.entity.User;
import com.tentcoo.entity.UserMessage;

@Repository
public class MessageDaoImpl implements MessageDao{
	
	
	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	HibernateTemplate hibernateTemplate;
	@Resource(name="mysqlJdbcTemplate")
	JdbcTemplate jdbcTemplate;
	@Resource(name="jdbcTemplate")
	private JdbcTemplate OjdbcTemplate;
	
	
	
	
	@Override
	public List<UserMessage> getAllMessage() {
		List<UserMessage> query = new ArrayList<UserMessage>();
		String hql = "from UserMessage";
		try{
			query = (List<UserMessage>) hibernateTemplate.find(hql);
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
	public List<UserMessage> getUserMessage(String userNo) {
		List<UserMessage> query = new ArrayList<UserMessage>();
		String hql = "from UserMessage where userNo = ?";
		try {
			query = (List<UserMessage>) hibernateTemplate.find(hql, userNo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (query.size() == 0) {
			return null;
		} else {
			return query;
		}
	}

	@Override
	public boolean addMessage(UserMessage message) {
		
		try {
		hibernateTemplate.save(message);
		
		}catch(Exception e) {
			e.printStackTrace();
			return false;
			
		}
		
		return true;
	}


	
	
	
	
}
