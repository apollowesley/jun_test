package com.guest.assessment.entity;

import com.guest.assessment.emus.EStatus;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @program: assess
 * @description: 专业指标关系实体
 * @author: Xiaodalong
 * @create: 2018-10-06 23:13
 **/
@Entity
@Table(name = "profession_indicator_relation")
public class ProfessionIndicatorRelation {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String uid;

    @Column(name = "profession_uid")
    private String professionUid;

    @Column(name = "indicator_uid")
    private String indicatorUid;

    @Column(name = "parent_uid")
    private String parentUid;

    @Column(name = "indicator_ratio")
    private Double indicatorRatio;

    private Double grade = 0.0;

    @Transient
    private Indicator indicator;
    
    @Transient
    private Indicator parentIndicator;

    @Transient
    private  Profession profession;

    @Transient
    private List<ProfessionIndicatorRelation> relationList;

    @Transient
    private Double showGrade;

    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

    private Integer status = EStatus.ENABLE;

    public Double getShowGrade() {
        return showGrade;
    }

    public void setShowGrade(Double showGrade) {
        this.showGrade = showGrade;
    }

    public List<ProfessionIndicatorRelation> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<ProfessionIndicatorRelation> relationList) {
        this.relationList = relationList;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfessionUid() {
        return professionUid;
    }

    public void setProfessionUid(String professionUid) {
        this.professionUid = professionUid;
    }

    public String getIndicatorUid() {
        return indicatorUid;
    }

    public void setIndicatorUid(String indicatorUid) {
        this.indicatorUid = indicatorUid;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public Double getIndicatorRatio() {
        return indicatorRatio;
    }

    public void setIndicatorRatio(Double indicatorRatio) {
        this.indicatorRatio = indicatorRatio;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

	/**
	 * @return the parentIndicator
	 */
	public Indicator getParentIndicator() {
		return parentIndicator;
	}

	/**
	 * @param parentIndicator the parentIndicator to set
	 */
	public void setParentIndicator(Indicator parentIndicator) {
		this.parentIndicator = parentIndicator;
	}
   
}
