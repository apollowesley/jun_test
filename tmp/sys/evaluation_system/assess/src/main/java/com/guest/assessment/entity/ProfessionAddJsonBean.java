package com.guest.assessment.entity;

import javax.persistence.Column;

/**
 * @program: assess
 * @description: 专业指标json解析添加bean
 * @author: Xiaodalong
 * @create: 2018-10-06 20:09
 **/
public class ProfessionAddJsonBean {

    private String professionUid;

    private String indicatorUid;

    private String parentUid;

    private Double indicatorRatio;

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
}
