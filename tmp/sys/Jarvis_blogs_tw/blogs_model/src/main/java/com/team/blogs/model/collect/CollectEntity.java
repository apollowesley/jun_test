package com.team.blogs.model.collect;

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
@Table(name = "t_collect", schema = "jarvis_blog", catalog = "")
@ApiModel("收藏表")
public class CollectEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Basic
    @Column(name = "user_uid")
    @ApiModelProperty("用户uid")
    private Long userUid;

    @Basic
    @Column(name = "blog_uid")
    @ApiModelProperty("博客uid")
    private Long blogUid;
}
