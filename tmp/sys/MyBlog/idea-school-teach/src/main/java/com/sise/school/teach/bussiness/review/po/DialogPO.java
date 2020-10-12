package com.sise.school.teach.bussiness.review.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

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
public class DialogPO{

    @Id
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
     * 创建时间
     */
    private Date createTime;
}
