package com.antdsp.dao.jpa.system;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.antdsp.dao.jpa.AntdspBaseRepository;
import com.antdsp.data.entity.system.SystemRole;

@Repository("roleJpa")
public interface RoleJpa extends AntdspBaseRepository<SystemRole , Long>{
	
	@Query("FROM SystemRole s ORDER BY s.created")
	List<SystemRole> findRoleNameAndIds();
	
	/**
	 * 根据roleId查询角色下所有的菜单信息
	 * @param roleId
	 * @return
	 */
	@Query(value="SELECT r.menu_id FROM tb_role_menu r LEFT JOIN tb_system_menu sr ON r.menu_id = sr.id WHERE "
			+ " r.role_id = :roleId AND sr.menu_type = 'BUTTON' " , nativeQuery=true)
	List<Long> queryRoleMenuId(@Param("roleId")Long roleId);
	
	@Modifying
	@Query(value="DELETE FROM `tb_role_menu` WHERE `role_id` = :roleId", nativeQuery=true)
	void deleteRoleMenuByRoleId(@Param("roleId")Long roleId);

}
