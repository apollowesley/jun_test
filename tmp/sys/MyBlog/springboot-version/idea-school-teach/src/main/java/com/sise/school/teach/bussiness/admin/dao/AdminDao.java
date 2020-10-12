package com.sise.school.teach.bussiness.admin.dao;

import com.sise.school.teach.bussiness.admin.po.AdminPO;
import com.sise.school.teach.common.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author idea
 * @data 2018/10/14
 */
@Mapper
public interface AdminDao extends BaseDao<AdminPO> {

    /**
     * 登录
     *
     * @param name
     * @param status 管理员的角色（0，普通管理员,1 超级管理员）
     * @return
     */
    AdminPO selectOneByUsernameAndStatus(@Param("name") String name, @Param("status") String status);

}
