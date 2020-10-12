package com.team.blogs.model.blog;

import com.team.blogs.model.blogsort.BlogSortEntity;
import com.team.blogs.model.tag.TagEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: xiaokai
 * @Description: 博客表
 * @Date: 2019/5/31
 * @Version: 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_blogs", schema = "jarvis_blog", catalog = "")
@ApiModel("博客表")
public class BlogsEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "title")
    @ApiModelProperty("博客标题")
    private String title;

    @Basic
    @Column(name = "blog_summary")
    @ApiModelProperty("博客简介")
    private String blog_summary;

    @Basic
    @Column(name = "blog_content")
    @ApiModelProperty("博客内容")
    private String blog_content;

    @Basic
    @Column(name = "tag_id")
    @ApiModelProperty("标签id")
    private Long tag_uid;

    @Basic
    @Column(name = "blog_sort_uid")
    @ApiModelProperty("博客分类uId")
    private String blog_sort_uid;

    @Basic
    @Column(name = "blog_click_count")
    @ApiModelProperty("博客点击数")
    private Integer blog_click_count;

    @Basic
    @Column(name = "blog_collect_count")
    @ApiModelProperty("博客收藏数")
    private Integer blog_collect_count;

    @Basic
    @Column(name = "file_uid")
    @ApiModelProperty("标题图片uid")
    private Long file_uid;

    @Basic
    @Column(name = "admin_uid")
    @ApiModelProperty("管理员UID")
    private Long admin_uid;

    @Basic
    @Column(name = "is_publish")
    @ApiModelProperty("是否发布")
    private Boolean is_publish;

    @Basic
    @Column(name = "is_original")
    @ApiModelProperty("是否原创")
    private Boolean is_original;

    @Basic
    @Column(name = "admin_auther")
    @ApiModelProperty("如果原创，作者为管理员名")
    private String admin_auther;

    @Basic
    @Column(name = "articles_part")
    @ApiModelProperty("文章出处")
    private String articles_part;

    @Basic
    @Column(name = "level")
    @ApiModelProperty("推荐级别，用于首页推荐 0：正常 1：一级推荐(轮播图) 2：二级推荐(top) 3：三级推荐 () 4：四级 推荐 (特别推荐)")
    private Integer level;

    @Transient
    private List<TagEntity> tagList; //标签,一篇博客对应多个标签

    @Transient
    private List<String> photoList;//标题图

    @Transient
    private BlogSortEntity blogSortEntity; //博客分类

    @Transient
    private Integer praiseCount; //点赞数


}
