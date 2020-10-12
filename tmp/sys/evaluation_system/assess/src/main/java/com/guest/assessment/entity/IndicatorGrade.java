package com.guest.assessment.entity;

import com.guest.assessment.emus.EStatus;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * 指标成绩
 */
@Entity
@Table(name = "indicator_grade")
public class IndicatorGrade {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String uid;

    @Column(name = "indicator_uid")
    private String indicatorUid;

    @Column(name = "reference_uid")
    private String referenceUid;

    @Column(name = "profession_uid")
    private String professionUid;

    private Double grade;

    @CreatedDate
    @Column(name = "create_time")
    private Date createTime;

    @LastModifiedDate
    @Column(name = "update_time")
    private Date updateTime;

    private Integer status = EStatus.ENABLE;

    @Transient
    private Profession profession;

    @Transient
    private  IndicatorGradeReference reference;

    @Transient
    private  Indicator indicator;

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public IndicatorGradeReference getReference() {
        return reference;
    }

    public void setReference(IndicatorGradeReference reference) {
        this.reference = reference;
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

    public String getIndicatorUid() {
        return indicatorUid;
    }

    public void setIndicatorUid(String indicatorUid) {
        this.indicatorUid = indicatorUid;
    }

    public String getReferenceUid() {
        return referenceUid;
    }

    public void setReferenceUid(String referenceUid) {
        this.referenceUid = referenceUid;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
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

    public String getProfessionUid() {
        return professionUid;
    }

    public void setProfessionUid(String professionUid) {
        this.professionUid = professionUid;
    }
}
