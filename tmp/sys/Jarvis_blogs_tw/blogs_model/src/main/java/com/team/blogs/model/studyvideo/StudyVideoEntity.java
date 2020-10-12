package com.team.blogs.model.studyvideo;

import com.team.blogs.model.resourcesort.ResourceSortEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/5/31
 * @Version: 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_study_video", schema = "jarvis_blog", catalog = "")
@ApiModel("学习视频表")
public class StudyVideoEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Basic
    @Column(name = "name")
    @ApiModelProperty("视频名称")
    private String name;


    @Basic
    @Column(name = "summary")
    @ApiModelProperty("视频简介")
    private String summary;


    @Basic
    @Column(name = "content")
    @ApiModelProperty("视频内容介绍")
    private String content;


    @Basic
    @Column(name = "baidu_path")
    @ApiModelProperty("百度云完整路径")
    private String baiduPath;


    @Basic
    @Column(name = "file_uid")
    @ApiModelProperty("视频封面图片UID")
    private Long fileUid;


    @Basic
    @Column(name = "resource_sort_uid")
    @ApiModelProperty("资源分类UID")
    private Long resourceSortUid;


    @Basic
    @Column(name = "role_name")
    @ApiModelProperty("点击数")
    private Integer clickCount;

    @Transient
    private List<String> photoList; //学习视频标题图

    @Transient
    private ResourceSortEntity resourceSort; //学习视频标题图

}
