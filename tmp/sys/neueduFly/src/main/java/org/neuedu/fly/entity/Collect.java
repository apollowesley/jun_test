package org.neuedu.fly.entity;

import java.util.Date;
import lombok.Data;

/**
  * @description 
  * @auther: CDHONG.IT
  * @date: 2019/10/28-9:38
  **/
@Data
public class Collect {
    /**
    * 编号
    */
    private Integer id;

    /**
    * 用户编号
    */
    private Integer userId;

    /**
    * 帖子编号
    */
    private Integer postId;

    /**
    * 收藏时间
    */
    private Date createTime;
}