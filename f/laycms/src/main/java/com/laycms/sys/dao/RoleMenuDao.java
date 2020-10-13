package com.laycms.sys.dao;

import org.springframework.stereotype.Repository;

import com.laycms.base.HibernateBaseDao;
import com.laycms.sys.entity.RoleMenu;

/**
 * @author <p>Innate Solitary 于 2012-5-30 下午6:16:46</p>
 *
 */
@Repository
public class RoleMenuDao extends HibernateBaseDao<RoleMenu, Integer> {

	@Override
    public Class<RoleMenu> getEntityClass() {
	    return RoleMenu.class;
    }

}
