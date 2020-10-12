package com.team.blogs.model.webvlist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: xiaokai
 * @Description: web访问记录表
 * @Date: 2019/5/31
 * @Version: 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_user_web_list", schema = "jarvis_blog", catalog = "")
@ApiModel("web访问记录表")
public class WebListEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "user_id")
    @ApiModelProperty("用户关联ID")
    private Long userId;

    @Basic
    @Column(name = "ip")
    @ApiModelProperty("用户IP")
    private String ip;

    @Basic
    @Column(name = "access_behavior")
    @ApiModelProperty("访问行为（点击了文章，点击了标签，点击了分类，进行了搜索）")
    private String AccessBehavior;

    @Basic
    @Column(name = "module_uid")
    @ApiModelProperty("文章uid，标签uid，分类uid")
    private Long moduleUid;

    @Basic
    @Column(name = "other_data")
    @ApiModelProperty("附加数据")
    private String otherData;

    /**
     * 以下字段不存入数据库
     */
    @Transient
    private String content; //内容(点击的博客名，点击的标签名，搜索的内容，点击的作者)

    @Transient
    private String behaviorContent; //行为名称



}
