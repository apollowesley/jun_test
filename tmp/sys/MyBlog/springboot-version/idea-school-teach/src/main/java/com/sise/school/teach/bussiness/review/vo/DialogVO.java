package com.sise.school.teach.bussiness.review.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 评论底下的对话
 *
 * @author idea
 * @data 2018/11/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DialogVO {

    /**
     * 评论代码
     */
    private String reviewCode;

    /**
     * 对话代码
     */
    private String dialogCode;

    /**
     * 视频的code
     */
    private String videoCode;

    /**
     * 提问者名字
     */
    private String askerName;

    /**
     * 回答者名字
     */
    private String anserName;

    /**
     * 提问内容
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
     * 已经点赞过
     */
    private Boolean goodIsComment;

    /**
     * 已经差评过
     */
    private Boolean badIsComment;

    /**
     * 创建时间
     */
    private Date createTime;
}
