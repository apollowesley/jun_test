package com.laycms.sys.dao;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.laycms.base.HibernateBaseDao;
import com.laycms.sys.entity.Role;

@Repository
public class RoleDao extends HibernateBaseDao<Role, Integer> {

	@Override
	public Class<Role> getEntityClass() {
		return Role.class;
	}
	
	
	public Set<String> getRoleNamesByNickname(String nickname) throws SQLException {
        
		String userRolesQuery = "SELECT r.roleName as roleName FROM member u,memberrole ur,role r WHERE u.id=ur.memberId AND ur.roleId=r.id AND u.username=?";
		List<Map<String, Object>> list = this.findList(userRolesQuery,new Object[]{nickname});
		Set<String> roleNames = new LinkedHashSet<String>(); ;
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
			roleNames.add((String)map.get("roleName"));
		}
        return roleNames;
    }
}
