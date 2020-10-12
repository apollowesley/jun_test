package com.sise.school.teach.bussiness.video.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author idea
 * @data 2018/10/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoReqVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 视频代码
     */
    private String videoCode;

    /**
     * 视频链接
     */
    private String videoUrl;

    /**
     * 视频介绍
     */
    private String des;

    /**
     * 视频图片
     */
    private String picture;

    /**
     * 创建者名称
     */
    private String createUser;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 更新日期
     */
    private Date updateTime;

    /**
     * 页数
     */
    private Integer page;

}
