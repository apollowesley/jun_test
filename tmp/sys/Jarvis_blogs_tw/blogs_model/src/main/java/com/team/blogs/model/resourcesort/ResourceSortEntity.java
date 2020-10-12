package com.team.blogs.model.resourcesort;

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
@Table(name = "t_resource_sort", schema = "jarvis_blog", catalog = "")
@ApiModel("资源分类表")
public class ResourceSortEntity {

    private static final long serialVersionUID = 1L;


    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "sort_name")
    @ApiModelProperty("分类名")
    private String sortName;

    @Basic
    @Column(name = "content")
    @ApiModelProperty("分类介绍")
    private String content;


    @Basic
    @Column(name = "file_uid")
    @ApiModelProperty("分类图片UID")
    private Long fileUid;


    @Basic
    @Column(name = "click_count")
    @ApiModelProperty("分类点击数")
    private Integer clickCount;

    @Basic
    @Column(name = "sort")
    @ApiModelProperty("排序字段，数值越大，越靠前")
    private int sort;

    @Transient
    private List<String> photoList; //分类图


}
