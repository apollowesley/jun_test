package com.team.blogs.model.solrIndex;//package com.blogs.model.user.entity.solrIndex;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.jboss.logging.Field;
//
//import javax.persistence.*;
//import java.lang.annotation.Documented;
//import java.util.Date;
//
///**
// * @Author: xiaokai
// * @Description: 用于建立solr索引实体类
// * @Date: 2019/5/31
// * @Version: 1.0
// */
//@Data
//@Entity
//@NoArgsConstructor
//@Table(name = "t_solr_index", schema = "jarvis_blog", catalog = "")
//@ApiModel("用于建立solr索引实体表")
//public class SolrIndexEntity {
//
//    @Id
//    @Column
//    @ApiModelProperty("主键ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//
//    /**
//     * 博客uid
//     */
//    @Field("id")
//    private String id;
//
//    /**
//     * 图片Uid
//     */
//    @Field("file_uid")
//    private String fileUid;
//
//    /**
//     * 博客标题
//     */
//    @Field("blog_title")
//    private String title;
//
//    /**
//     * 博客简介
//     */
//    @Field("blog_summary")
//    private String summary;
//
//    /**
//     * 标签名
//     */
//    @Field("blog_tag")
//    private String tag;
//
//    /**
//     * 博客分类名
//     */
//    @Field("blog_sort")
//    private String blogSort;
//
//    /**
//     * 博客标签UID
//     */
//    @Field("blog_tag_uid")
//    private String blogTagUid;
//
//    /**
//     * 博客分类UID
//     */
//    @Field("blog_sort_uid")
//    private String blogSortUid;
//
//    /**
//     * 如果原创，作者为管理员名
//     */
//    @Field("blog_author")
//    private String author;
//
//    /**
//     * 文章更新时间
//     */
//    @Field("blog_updateTime")
//    private Date updateTime;
//
//    /**
//     * 以下字段不存入solr索引库中
//     *
//     */
//    @Field("photo_list")
//    private String photoList; //标题图
//}
