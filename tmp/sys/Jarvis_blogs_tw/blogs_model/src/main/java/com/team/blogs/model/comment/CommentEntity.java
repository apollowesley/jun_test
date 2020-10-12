package com.team.blogs.model.comment;

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
@Table(name = "t_comments", schema = "jarvis_blog", catalog = "")
@ApiModel("评论表")
public class CommentEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Basic
    @Column(name = "user_uid")
    @ApiModelProperty("用户uid")
    private String userUid;


    @Basic
    @Column(name = "to_uid")
    @ApiModelProperty("回复某条评论的uid")
    private String toUid;


    @Basic
    @Column(name = "to_user_uid")
    @ApiModelProperty("回复某个人的uid")
    private String toUserUid;


    @Basic
    @Column(name = "user_name")
    @ApiModelProperty("用户名")
    private String userName;


    @Basic
    @Column(name = "content")
    @ApiModelProperty("评论内容")
    private String content;


    @Basic
    @Column(name = "blog_uid")
    @ApiModelProperty("博客uid")
    private String blogUid;


}
