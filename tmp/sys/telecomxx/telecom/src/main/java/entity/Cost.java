package entity;

import java.io.Serializable;
import java.sql.Timestamp;


//name, base_duration, base_cost,
//unit_cost, status,  descr, creatime, 
//startime, cost_type
public class Cost implements Serializable {
	private Integer costId;
	private String name;
	private Double baseDuration; // 基本时长
	private Double baseCost; // 基本费用
	private Double unitCost; // 单位费用
	private String status; // 状态：暂停（0），开通（1）
	private String descr; // 描述
	private Timestamp creatime; // 创建时间
	private Timestamp startime; // 开通时间
	private String costType; // 资费类型：包月（0），套餐（1），计时（2）
	public Integer getCostId() {
		return costId;
	}
	public void setCostId(Integer costId) {
		this.costId = costId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getBaseDuration() {
		return baseDuration;
	}
	public void setBaseDuration(Double baseDuration) {
		this.baseDuration = baseDuration;
	}
	public Double getBaseCost() {
		return baseCost;
	}
	public void setBaseCost(Double baseCost) {
		this.baseCost = baseCost;
	}
	public Double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Timestamp getCreatime() {
		return creatime;
	}
	public void setCreatime(Timestamp creatime) {
		this.creatime = creatime;
	}
	public Timestamp getStartime() {
		return startime;
	}
	public void setStartime(Timestamp startime) {
		this.startime = startime;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	
	
}


