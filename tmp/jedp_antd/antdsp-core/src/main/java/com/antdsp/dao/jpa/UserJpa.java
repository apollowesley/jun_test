package com.antdsp.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.antdsp.data.entity.User;

@Repository("userJpa")
public interface UserJpa extends AntdspBaseRepository<User, Long>{
	
	@Query("From User WHERE loginname = :loginName AND status = 'NORMAL'")
	User queryUserByLoginName(@Param("loginName") String loginName);
	
	@Query(value="SELECT s.role_id FROM `tb_role_user` s WHERE s.user_id = :userId ", nativeQuery=true)
	List<Long> queryUserRoleId(@Param("userId") Long userId);
	
	@Modifying
	@Query(value="DELETE FROM `tb_role_user` WHERE user_id = :userId " , nativeQuery=true)
	void deleteUserRoleId(@Param("userId") Long userId);

}
