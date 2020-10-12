package com.team.blogs.model.adminrole;


import com.team.blogs.model.admin.AdminEntity;
import com.team.blogs.model.role.RoleEntity;
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
@Table(name = "t_admin_role", schema = "jarvis_blog", catalog = "")
@ApiModel("管理员和角色关系表")
public class AdminRoleEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Basic
    @Column(name = "admin_uid")
    @ApiModelProperty("管理员id")
    private Long adminUid;


    @Basic
    @Column(name = "role_uid")
    @ApiModelProperty("角色id")
    private Long roleUid;


    //以下字段不存入数据库，封装为了更好使用

    /**
     * 管理员
     */
    @Transient
    private AdminEntity admin;

    /**
     * 角色
     */
    @Transient
    private RoleEntity role;


}
