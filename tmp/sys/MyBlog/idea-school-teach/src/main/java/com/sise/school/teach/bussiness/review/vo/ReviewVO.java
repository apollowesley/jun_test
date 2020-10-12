package com.sise.school.teach.bussiness.review.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * 评论
 *
 * @author idea
 * @data 2018/11/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewVO {

    /**
     * 评论代码
     */
    private String reviewCode;

    /**
     * 用户名
     */
    private String username;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数量
     */
    private Integer goods;

    /**
     * 差评数量
     */
    private Integer bad;

    /**
     * 评论视频code
     */
    private String videoCode;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 已经点赞过
     */
    private Boolean goodIsComment=false;

    /**
     * 已经差评过
     */
    private Boolean badIsComment=false;

    /**
     * 对话数组
     */
    private List<DialogVO> dialogList;
}
