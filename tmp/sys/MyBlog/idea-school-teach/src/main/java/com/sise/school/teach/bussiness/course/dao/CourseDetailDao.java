package com.sise.school.teach.bussiness.course.dao;

import com.sise.school.teach.bussiness.course.po.CourseDetailPO;
import com.sise.school.teach.common.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author idea
 * @data 2018/10/2
 */
@Mapper
public interface CourseDetailDao extends BaseDao<CourseDetailPO> {

    /**
     * 文章标题查找
     *
     * @param title
     * @return
     */
    List<CourseDetailPO> selectOneByTitle(@Param("title") String title);

    /**
     * 根据课程的id进行删除
     *
     * @param courseId
     */
    void deleteByCourseId(@Param("courseId") Integer courseId);

    /**
     * 查找相关课程的某一小结内容
     *
     * @param title
     * @return
     */
    CourseDetailPO selectNewCourseDetailByTitle(@Param("title") String title);

    /**
     * 更新课程详情信息
     *
     * @param courseDetailPO
     */
    void update(CourseDetailPO courseDetailPO);


    /**
     * 根据课程代码搜索
     *
     * @param courseCode
     * @return
     */
    CourseDetailPO selectCourseByCode(@Param("courseCode") String courseCode);

    /**
     * 获取第一个课程的信息
     *
     * @return
     */
    CourseDetailPO getFirstOneBySpecialId(@Param("specialId") Integer id);
}
