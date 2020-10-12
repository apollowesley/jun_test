package com.team.blogs.model.feedback;

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
@Table(name = "t_feed_back_message", schema = "jarvis_blog", catalog = "")
@ApiModel("反馈表")
public class FeedBackEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Basic
    @Column(name = "user_uid")
    @ApiModelProperty("用户uid")
    private Long user_uid;


    @Basic
    @Column(name = "content")
    @ApiModelProperty("反馈的内容")
    private String content;


}
