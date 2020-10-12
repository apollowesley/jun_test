package com.team.blogs.model.role;

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
@Table(name = "t_roles", schema = "jarvis_blog", catalog = "")
@ApiModel("角色信息表")
public class RoleEntity {

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "role_name")
    @ApiModelProperty("角色名称")
    private String role_name;

    @Basic
    @Column(name = "summary")
    @ApiModelProperty("介绍")
    private String summary;

    @Basic
    @Column(name = "category_menu_uid")
    @ApiModelProperty("该角色所能管辖的区域")
    private String category_menu_uid;


}
