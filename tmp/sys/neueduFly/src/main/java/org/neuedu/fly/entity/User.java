package org.neuedu.fly.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * @description 
  * @auther: CDHONG.IT
  * @date: 2019/10/28-9:34
  **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
    * 编号
    */
    private Integer id;

    /**
    * 手机
    */
    private String tel;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 昵称
    */
    private String nickName;

    /**
    * 性别
    */
    private String sex;

    /**
    * 城市
    */
    private String city;

    /**
    * 签名
    */
    private String signature;

    /**
    * 头像
    */
    private String headImg;

    /**
    * 密码
    */
    private String password;

    /**
    * QQ
    */
    private String qq;

    /**
    * 新浪
    */
    private String sina;

    /**
    * 财富值
    */
    private BigDecimal wealth;

    /**
    * 飞吻
    */
    private Integer kiss;

    /**
    * 用户等级:无 vip1-9
    */
    private String grade;

    /**
    * 用户角色: -1管理员 0普通用户 1版主
    */
    private String role;

    /**
    * 账号状态: 0启用 1禁用
    */
    private Integer userStatus;

    /**
    * 邮箱状态: 0未激活 1激活
    */
    private Integer emailStatus;

    /**
    * 邮箱激活码,唯一不重复，使用UUID生成
    */
    private Integer emailCode;

    /**
    * 注册时间
    */
    private Date createTime;

    /**
    * 修改时间
    */
    private Date updateTime;
}