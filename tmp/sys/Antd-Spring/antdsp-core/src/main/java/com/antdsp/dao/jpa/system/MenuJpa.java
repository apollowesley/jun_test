package com.antdsp.dao.jpa.system;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.antdsp.dao.jpa.AntdspBaseRepository;
import com.antdsp.data.entity.system.SystemMenu;

@Repository("menuJpa")
public interface MenuJpa extends AntdspBaseRepository<SystemMenu, Long>{
	
	/**
	 * 根据userId查询所有权限菜单
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT m.* FROM `tb_system_menu` m , `tb_role_menu` rm , `tb_role_user` ru WHERE "
			+ " rm.role_id = ru.role_id AND rm.menu_id = m.id AND ru.user_id = :userId " , nativeQuery=true)
	List<SystemMenu> findPermissMenus(@Param("userId") Long userId);

}
