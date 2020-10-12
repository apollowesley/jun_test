package com.team.blogs.model.category;

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
@Table(name = "t_category_menu", schema = "jarvis_blog", catalog = "")
@ApiModel("菜单表")
public class CategoreMenuEntity {

    private static final long serialVersionUID = 1L;


    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Basic
    @Column(name = "name")
    @ApiModelProperty("菜单名称")
    private String name;


    @Basic
    @Column(name = "menu_level")
    @ApiModelProperty("菜单级别 （一级分类，二级分类）")
    private Integer menuLevel;


    @Basic
    @Column(name = "summary")
    @ApiModelProperty("介绍")
    private String summary;


    @Basic
    @Column(name = "icon")
    @ApiModelProperty("Icon图标")
    private String icon;


    @Basic
    @Column(name = "parent_uid")
    @ApiModelProperty("父UID")
    private String parentUid;


    @Basic
    @Column(name = "url")
    @ApiModelProperty("URL地址")
    private String url;


    @Basic
    @Column(name = "sort")
    @ApiModelProperty("排序字段(越大越靠前)")
    private Integer sort;


    @ApiModelProperty("父菜单")
    @Transient
    private CategoreMenuEntity parentCategoryMenu;


    @ApiModelProperty("子菜单")
    @Transient
    private List<CategoreMenuEntity> childCategoryMenu;
}
