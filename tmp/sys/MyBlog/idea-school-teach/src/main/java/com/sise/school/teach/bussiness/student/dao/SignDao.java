package com.sise.school.teach.bussiness.student.dao;

import com.sise.school.teach.bussiness.student.po.SignPO;
import com.sise.school.teach.common.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author idea
 * @data 2018/10/3
 */
@Mapper
public interface SignDao extends BaseDao<SignPO> {

    /**
     * 根据学号查询
     *
     * @param stuNo
     * @return
     */
    List<SignPO> selectAllByNo(@Param("stuNo") String stuNo);
}
