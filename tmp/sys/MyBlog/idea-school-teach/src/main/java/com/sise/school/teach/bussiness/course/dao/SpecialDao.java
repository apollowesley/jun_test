package com.sise.school.teach.bussiness.course.dao;

import com.sise.school.teach.bussiness.course.po.SpecialPO;
import com.sise.school.teach.common.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author idea
 * @data 2018/10/14
 */
@Mapper
public interface SpecialDao extends BaseDao<SpecialPO> {


    List<SpecialPO> selectAllInPage(SpecialPO specialPO);

    /**
     * 查询最新的专题
     */
    List<SpecialPO> selectNewSpecial();


    /**
     * 根据id进行查询
     * @param id
     * @return
     */
    SpecialPO selectOneById(Integer id);

    /**
     * 根据课程名称更新状态
     *
     * @param specialName
     * @param status
     */
    void updateStatusByName(@Param("specialName") String specialName, @Param("status") Integer status);

    /**
     * 根据视频代码进行视频观看次数更新
     *
     * @param id
     * @return
     */
    int updateVideoVisitCount(@Param("id") Integer id);


}
