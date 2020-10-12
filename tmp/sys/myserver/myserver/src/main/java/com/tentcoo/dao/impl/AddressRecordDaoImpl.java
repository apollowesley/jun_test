package com.tentcoo.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tentcoo.dao.AddressRecordDao;
import com.tentcoo.entity.Address;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class AddressRecordDaoImpl implements AddressRecordDao{

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	HibernateTemplate hibernateTemplete;
	@Resource(name = "mysqlJdbcTemplate")
	JdbcTemplate jdbcTemplate;
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate OjdbcTemplate;
	
	
	
	@Override
	public boolean addUserAddress(Address address) {
		try {
			hibernateTemplete.save(address);
			System.out.println("执行添加使用记录！");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
