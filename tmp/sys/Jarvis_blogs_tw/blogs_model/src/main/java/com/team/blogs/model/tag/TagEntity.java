package com.team.blogs.model.tag;

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
@Table(name = "t_tags", schema = "jarvis_blog", catalog = "")
@ApiModel("标签表")
public class TagEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "content")
    @ApiModelProperty("标签内容")
    private String content;


    @Basic
    @Column(name = "click_count")
    @ApiModelProperty("标签简介")
    private int clickCount;


    @Basic
    @Column(name = "sort")
    @ApiModelProperty("排序字段，数值越大，越靠前")
    private int sort;


}
