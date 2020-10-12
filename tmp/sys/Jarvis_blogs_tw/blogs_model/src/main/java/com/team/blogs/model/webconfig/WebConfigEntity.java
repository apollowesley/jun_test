package com.team.blogs.model.webconfig;

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
@Table(name = "t_web_config", schema = "jarvis_blog", catalog = "")
@ApiModel("网站配置表")
public class WebConfigEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "logo")
    @ApiModelProperty("网站Logo")
    private String logo;

    @Basic
    @Column(name = "name")
    @ApiModelProperty("网站名称")
    private String name;

    @Basic
    @Column(name = "title")
    @ApiModelProperty("标题")
    private String title;

    @Basic
    @Column(name = "summary")
    @ApiModelProperty("描述")
    private  String summary;

    @Basic
    @Column(name = "keyword")
    @ApiModelProperty("关键字")
    private String keyword;

    @Basic
    @Column(name = "author")
    @ApiModelProperty("作者")
    private String author;

    @Basic
    @Column(name = "record_num")
    @ApiModelProperty("备案号")
    private String recordNum;

    @Basic
    @Column(name = "ali_pay")
    @ApiModelProperty("支付宝收款码FileId")
    private String aliPay;


    @Basic
    @Column(name = "weixin_pay")
    @ApiModelProperty("微信收款码FileId")
    private String weixinPay;


    @Basic
    @Column(name = "start_comment")
    @ApiModelProperty("是否开启评论(0:否， 1:是)")
    private Boolean startComment;

    /**
     * 以下字段不存入数据库，封装为了方便使用
     */
    @Transient
    private List<String> photoList; //标题图

    @Transient
    private String aliPayPhoto; //支付宝付款码

    @Transient
    private String weixinPayPhoto; //标题图
}
