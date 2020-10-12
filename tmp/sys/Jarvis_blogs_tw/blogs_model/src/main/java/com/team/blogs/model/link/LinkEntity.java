package com.team.blogs.model.link;

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
@Table(name = "t_links", schema = "jarvis_blog", catalog = "")
@ApiModel("友情链接表")
public class LinkEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Basic
    @Column(name = "title")
    @ApiModelProperty("友链标题")
    private String title;


    @Basic
    @Column(name = "summary")
    @ApiModelProperty("友链介绍")
    private String summary;


    @Basic
    @Column(name = "url")
    @ApiModelProperty("友链地址")
    private String url;


    @Basic
    @Column(name = "click_count")
    @ApiModelProperty("点击数")
    private Integer clickCount;


    @Basic
    @Column(name = "sort")
    @ApiModelProperty("排序字段")
    private Integer sort;


}
