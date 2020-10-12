package com.guest.assessment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guest.assessment.entity.Admin;

/**
 * 管理员 repository
 * 
 * @author xuzhixiang
 * @date 2018年10月6日下午3:14:22
 */
public interface AdminRepository extends JpaRepository<Admin, String> {

 
}
