package com.team.blogs.model.picturesort;

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
@Table(name = "t_picture_sort", schema = "jarvis_blog", catalog = "")
@ApiModel("相册分类表")
public class PictureSortEntity {

    private static final long serialVersionUID = 3454006152368184626L;


    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "parent_uid")
    @ApiModelProperty("父ID")
    private Long parentUid;

    @Basic
    @Column(name = "name")
    @ApiModelProperty("分类名")
    private String name;

    @Basic
    @Column(name = "file_uid")
    @ApiModelProperty("分类图片Uid")
    private Long fileUid;


    @Basic
    @Column(name = "sort")
    @ApiModelProperty("排序字段，数值越大，越靠前")
    private int sort;

    @Transient
    private List<String> photoList; //分类图



}
