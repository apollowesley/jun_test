package com.sise.school.teach.bussiness.video.dao;

import com.sise.school.teach.bussiness.video.po.VideoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author idea
 * @data 2018/10/27
 */
@Mapper
public interface VideoDao {

    /**
     * 查找最新的视频资源
     *
     * @return
     */
    List<VideoPO> selectNewVideo();


    /**
     * 根据视频状态进行查询
     *
     * @param status
     * @return
     */
    List<VideoPO> selectVideoByStatus(Integer status);


    /**
     * 查找所有的视频资源
     *
     * @return
     */
    List<VideoPO> selectAll(HashMap<String, Object> paramMap);


    /**
     * 查询单个视频资源
     *
     * @return
     */
    VideoPO selectOneByVideoCode(@Param("videoCode") String videoCode);

    /**
     * 根据视频名称查找
     *
     * @param videoName
     * @return
     */
    VideoPO selectOneByVideoName(@Param("videoName") String videoName);

    /**
     * 根据视频名称进行更新
     *
     * @param videoPO
     * @return
     */
    int updateByVideoName(VideoPO videoPO);

    /**
     * 根据视频代码进行视频观看次数更新
     *
     * @param videoCode
     * @return
     */
    int updateVideoVisitCount(@Param("videoCode") String videoCode);

    /**
     * 插入一个对象
     *
     * @param videoPO
     * @return
     */
    int insert(VideoPO videoPO);

    /**
     * 根据视频代码进行状态更新
     *
     * @param code
     * @param status
     * @return
     */
    int updateStatusByCode(@Param("videoCode") String code, @Param("status") Integer status);
}
