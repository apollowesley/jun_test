package org.neuedu.fly.entity;

import java.util.Date;
import lombok.Data;

/**
  * @description 
  * @auther: CDHONG.IT
  * @date: 2019/10/28-9:37
  **/
@Data
public class Post {
    /**
    * 编号
    */
    private Integer id;

    /**
    * 所在专栏
    */
    private Integer columnId;

    /**
    * 标题
    */
    private String title;

    /**
    * 内容详情
    */
    private String context;

    /**
    * 悬赏(飞吻)
    */
    private Integer kiss;

    /**
    * 帖子状态,0待审核，1已审核
    */
    private Integer status;

    /**
    * 是否精帖 0否，1是
    */
    private Integer isFine;

    /**
    * 是否置顶 0否，1是
    */
    private Integer isTop;

    /**
    * 是否已结 0否，1是
    */
    private Integer isClosed;

    /**
    * 发帖人
    */
    private Integer userId;

    /**
    * 人气(浏览数) 0否，1是
    */
    private Integer browseNum;

    /**
    * 回复数
    */
    private Integer replyNum;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 修改时间
    */
    private Date updateTime;
}