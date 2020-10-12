package com.sise.school.teach.bussiness.student.dao;

import com.sise.school.teach.bussiness.student.po.StudentCollectionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author idea
 * @data 2018/12/4
 */
@Mapper
public interface StudentCollectionDao {

    /**
     * 插入对象
     *
     * @param studentCollectionPO
     */
    void insert(StudentCollectionPO studentCollectionPO);

    /**
     * 根据代码查询
     *
     * @param stuCode
     * @return
     */
    List<StudentCollectionPO> selectAllByCode(@Param("stuCode") String stuCode);


    /**
     * 根据学生代码和收藏代码查询
     *
     * @param stuCode
     * @param colCode
     * @return
     */
    StudentCollectionPO selectOneByStuCodeAndColCode(@Param("stuCode") String stuCode, @Param("colCode") String colCode);


    /**
     * 根据代码删除
     *
     * @param stuCode
     */
    void deleteByCode(@Param("stuCode") String stuCode, @Param("collectionCode") String collectionCode);
}
