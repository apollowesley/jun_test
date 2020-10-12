package com.sise.school.teach.bussiness.question.dao;

import com.sise.school.teach.bussiness.question.po.QuestionPO;
import com.sise.school.teach.common.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author idea
 * @data 2018/10/7
 */
@Mapper
public interface QuestionDao extends BaseDao<QuestionPO> {

    List<QuestionPO> selectOneByCourseDetailId(@Param("detailId") int detailId);
}
