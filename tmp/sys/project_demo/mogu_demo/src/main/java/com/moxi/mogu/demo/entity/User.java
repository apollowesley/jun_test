package com.moxi.mogu.demo.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 文件实体类
 * </p>
 *
 * @author xuzhixiang
 * @since 2018-11-19 14:37:31
 */
@TableName("t_user")
public class User extends Model<User> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@TableId(value = "uid", type = IdType.UUID)
	private String uid; // 唯一uid
	
	
	private int status; // 0 失效  1 生效
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createTime; //创建时间
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime; //更新时间
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}




	public int getStatus() {
		return status;
	}




	public void setStatus(int status) {
		this.status = status;
	}




	public Date getCreateTime() {
		return createTime;
	}




	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}




	public Date getUpdateTime() {
		return updateTime;
	}




	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}




	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}


}
