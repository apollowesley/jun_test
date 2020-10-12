package com.team.blogs.model.blogsort;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/5/31
 * @Version: 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_blog_sort", schema = "jarvis_blog", catalog = "")
@ApiModel("博客分类表")
public class BlogSortEntity {

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
    @Column(name = "sort")
    @ApiModelProperty("排序字段，数值越大，越靠前")
    private int sort;
}
