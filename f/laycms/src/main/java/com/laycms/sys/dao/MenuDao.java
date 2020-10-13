package com.laycms.sys.dao;

import org.springframework.stereotype.Repository;

import com.laycms.base.HibernateBaseDao;
import com.laycms.sys.entity.Menu;

@Repository
public class MenuDao extends HibernateBaseDao<Menu,Integer>{

	@Override
	public Class<Menu> getEntityClass() {
		return Menu.class;
	}
}
