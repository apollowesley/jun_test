package com.sise.school.teach.bussiness.course.dao;

import com.sise.school.teach.bussiness.course.po.CoursePO;
import com.sise.school.teach.common.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author idea
 * @data 2018/10/2
 */
@Mapper
public interface CourseDao extends BaseDao<CoursePO> {

    /**
     * 状态查询
     *
     * @param status
     * @return
     */
    List<CoursePO> selectAllByStatus(@Param("status") String status);

    /**
     * 根据专栏id查询
     *
     * @param coursePO
     * @return
     */
    List<CoursePO> selectAllBySpecialIdInPage(CoursePO coursePO);


    /**
     * 查询和该课程属于同一专题的其他课程
     *
     * @param specialId
     * @return
     */
    List<CoursePO> selectAllBySpecialId(@Param("specialId") Integer specialId);

    /**
     * 按照状态来更新
     *
     * @param status
     * @param id
     */
    void updateStatus(@Param("status") Integer status, @Param("id") Integer id);

    /**
     * 按照名称统计数目
     *
     * @param name
     * @return
     */
    Integer countAllByName(@Param("name") String name);

    /**
     * 课程更新
     */
    void update(CoursePO coursePO);

    void updateCourseStatusByCode(@Param("status") Integer status,@Param("courseCode") String courseCode);

    /**
     * 学生加入收藏信息中
     */
    void addLove();

    /**
     * 课程号查询
     *
     * @param idList
     * @return
     */
    List<CoursePO> selectAllInId(@Param("list") List<Integer> idList);
}
