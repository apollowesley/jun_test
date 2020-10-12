package org.neuedu.fly.entity;

import java.util.Date;
import lombok.Data;

/**
  * @description 
  * @auther: CDHONG.IT
  * @date: 2019/10/28-9:37
  **/
@Data
public class Sign {
    /**
    * 编号
    */
    private Integer id;

    /**
    * 用户编号
    */
    private Integer userId;

    /**
    * 签到总数
    */
    private Integer signCount;

    /**
    * 签到时间
    */
    private Date createTime;
}