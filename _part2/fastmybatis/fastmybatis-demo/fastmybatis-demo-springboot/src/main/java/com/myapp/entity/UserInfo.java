package com.myapp.entity;

import com.gitee.fastmybatis.core.annotation.LazyFetch;

import javax.persistence.Table;


/**
 * 表名：user_info
 * 备注：用户信息表; InnoDB free: 11264 kB
 */
@Table(name = "user_info")
public class UserInfo extends BaseEntity<Integer> {

    /** t_user外键, 数据库字段：user_id */
    private Integer userId;
    
    // 一对一配置，这里的user_id对应TUser主键
 	// 触发懒加载时，会拿user_id的值去查询t_user表
 	// 即：SELECT * FROM t_user WHERE id={user_id}
 	@LazyFetch(column = "user_id")
 	private TUser user;

    /** 城市, 数据库字段：city */
    private String city;

    /** 街道, 数据库字段：address */
    private String address;

    /** 类型, 数据库字段：status */
    private String status;

    /** 设置t_user外键, 数据库字段：user_info.user_id */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /** 获取t_user外键, 数据库字段：user_info.user_id */
    public Integer getUserId() {
        return this.userId;
    }

    /** 设置城市, 数据库字段：user_info.city */
    public void setCity(String city) {
        this.city = city;
    }

    /** 获取城市, 数据库字段：user_info.city */
    public String getCity() {
        return this.city;
    }

    /** 设置街道, 数据库字段：user_info.address */
    public void setAddress(String address) {
        this.address = address;
    }

    /** 获取街道, 数据库字段：user_info.address */
    public String getAddress() {
        return this.address;
    }

    /** 设置类型, 数据库字段：user_info.status */
    public void setStatus(String status) {
        this.status = status;
    }

    /** 获取类型, 数据库字段：user_info.status */
    public String getStatus() {
        return this.status;
    }

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}
    
    
}
