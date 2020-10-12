package org.neuedu.fly.entity;

import java.util.Date;
import lombok.Data;

/**
  * @description 
  * @auther: CDHONG.IT
  * @date: 2019/10/28-9:37
  **/
@Data
public class Reply {
    /**
    * 编号
    */
    private Integer id;

    /**
    * 帖子编号
    */
    private Integer postId;

    /**
    * 回复内容
    */
    private String replayContext;

    /**
    * 点赞数
    */
    private Integer praiseCount;

    /**
    * 回复状态 0未审核 1审核通过
    */
    private Integer replayStatus;

    /**
    * 回复人
    */
    private Integer userId;

    /**
    * 回复时间
    */
    private Date createTime;
}