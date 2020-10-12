package org.neuedu.fly.entity;

import java.util.Date;
import lombok.Data;

/**
  * @description 
  * @auther: CDHONG.IT
  * @date: 2019/10/28-9:37
  **/
@Data
public class Column {
    /**
    * 编号
    */
    private Integer id;

    /**
    * 名称
    */
    private String name;

    /**
    * 排序号
    */
    private Integer sortNum;

    /**
    * 创建人
    */
    private Integer userId;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 修改时间
    */
    private Date updateTime;
}