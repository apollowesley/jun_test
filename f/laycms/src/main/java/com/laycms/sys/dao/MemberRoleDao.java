/**
 * 
 */
package com.laycms.sys.dao;

import org.springframework.stereotype.Repository;

import com.laycms.base.HibernateBaseDao;
import com.laycms.sys.entity.MemberRole;

/**
 * @author <p>Innate Solitary 于 2012-5-31 上午11:01:12</p>
 *
 */
@Repository
public class MemberRoleDao extends HibernateBaseDao<MemberRole, Integer> {

	@Override
    public Class<MemberRole> getEntityClass() {
	    return MemberRole.class;
    }

}
