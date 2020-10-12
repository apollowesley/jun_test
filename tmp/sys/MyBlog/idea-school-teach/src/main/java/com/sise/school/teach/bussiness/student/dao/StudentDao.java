package com.sise.school.teach.bussiness.student.dao;

import com.sise.school.teach.bussiness.student.po.StudentPO;
import com.sise.school.teach.common.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * @author idea
 * @data 2018/10/3
 */
@Mapper
public interface StudentDao extends BaseDao<StudentPO> {

    /**
     * 更新学生对象
     * @param map
     */
    void updateByCode(HashMap<String, Object> map);

}
