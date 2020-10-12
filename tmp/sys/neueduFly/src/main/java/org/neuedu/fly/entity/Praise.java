package org.neuedu.fly.entity;

import java.util.Date;
import lombok.Data;

/**
  * @description 
  * @auther: CDHONG.IT
  * @date: 2019/10/28-9:37
  **/
@Data
public class Praise {
    /**
    * 回贴ID
    */
    private Integer replyId;

    /**
    * 点赞人ID
    */
    private Integer userId;

    /**
    * 点赞时间
    */
    private Date createTime;
}