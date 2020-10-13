package com.myapp.entity;

import java.util.Date;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.oschina.durcframework.easymybatis.HasPk;

/**
  表:t_user 用户表
*/
@Table(name = "t_user")
public class TUser implements HasPk<Integer> {

	// ID
	@Id
	@GeneratedValue(
        	strategy = GenerationType.IDENTITY
        )
	private Integer id;
	
	// 用户名
	@Column(name="username")
	private String username;
	
	// 状态
	@Column(name="state")
	private Byte state;
	
	// 是否删除
	@Column(name="isdel")
	private Boolean isdel;
	
	// 备注
	@Column(name="remark")
	private String remark;
	
	// 添加时间
	@Column(name="add_time")
	private Date addTime;
	
	// 金额
	@Column(name="money")
	private BigDecimal money;
	
	// 剩下的钱
	@Column(name="left_money")
	private Float leftMoney;
	

	/** 设置 ID,对应字段 t_user.id */
	public void setId(Integer id){
		this.id = id;
	}
	/** 获取 ID,对应字段 t_user.id */
	public Integer getId(){
		return this.id;
	}

	/** 设置 用户名,对应字段 t_user.username */
	public void setUsername(String username){
		this.username = username;
	}
	/** 获取 用户名,对应字段 t_user.username */
	public String getUsername(){
		return this.username;
	}

	/** 设置 状态,对应字段 t_user.state */
	public void setState(Byte state){
		this.state = state;
	}
	/** 获取 状态,对应字段 t_user.state */
	public Byte getState(){
		return this.state;
	}

	/** 设置 是否删除,对应字段 t_user.isdel */
	public void setIsdel(Boolean isdel){
		this.isdel = isdel;
	}
	/** 获取 是否删除,对应字段 t_user.isdel */
	public Boolean getIsdel(){
		return this.isdel;
	}

	/** 设置 备注,对应字段 t_user.remark */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/** 获取 备注,对应字段 t_user.remark */
	public String getRemark(){
		return this.remark;
	}

	/** 设置 添加时间,对应字段 t_user.add_time */
	public void setAddTime(Date addTime){
		this.addTime = addTime;
	}
	/** 获取 添加时间,对应字段 t_user.add_time */
	public Date getAddTime(){
		return this.addTime;
	}

	/** 设置 金额,对应字段 t_user.money */
	public void setMoney(BigDecimal money){
		this.money = money;
	}
	/** 获取 金额,对应字段 t_user.money */
	public BigDecimal getMoney(){
		return this.money;
	}

	/** 设置 剩下的钱,对应字段 t_user.left_money */
	public void setLeftMoney(Float leftMoney){
		this.leftMoney = leftMoney;
	}
	/** 获取 剩下的钱,对应字段 t_user.left_money */
	public Float getLeftMoney(){
		return this.leftMoney;
	}


	@Override
	public Integer getPk() {
		return this.id;
	}
	
    @Override
	public String toString() {
    	StringBuilder sb = new StringBuilder();
        sb.append("TUser [");
        		        sb.append("id=").append(id);
        		sb.append(", ");        sb.append("username=").append(username);
        		sb.append(", ");        sb.append("state=").append(state);
        		sb.append(", ");        sb.append("isdel=").append(isdel);
        		sb.append(", ");        sb.append("remark=").append(remark);
        		sb.append(", ");        sb.append("addTime=").append(addTime);
        		sb.append(", ");        sb.append("money=").append(money);
        		sb.append(", ");        sb.append("leftMoney=").append(leftMoney);
                sb.append("]");
		return sb.toString();
	}

}