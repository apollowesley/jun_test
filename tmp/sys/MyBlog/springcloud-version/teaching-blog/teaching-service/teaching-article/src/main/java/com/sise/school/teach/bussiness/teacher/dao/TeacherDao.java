package com.sise.school.teach.bussiness.teacher.dao;

import com.sise.school.teach.bussiness.teacher.po.TeacherPO;
import com.sise.school.teach.common.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author idea
 * @data 2018/10/1
 */
@Mapper
public interface TeacherDao extends BaseDao<TeacherPO> {

    TeacherPO login(@Param("code") String code);
}
