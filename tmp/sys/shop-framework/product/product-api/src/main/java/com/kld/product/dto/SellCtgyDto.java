package com.kld.product.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by anpushang on 2016/3/13.
 */
public class SellCtgyDto implements Serializable {

    private static final long serialVersionUID = -1955741830498283761L;
    /** 类目ID */
    private String sellCtgyId;
    /** 类目名称 */
    private String sellCtgyName;
    /** 排序 */
    private Integer orderNo;
    /** 类目级别 */
    private String ctgyLvl;
    /** 上级类目 */
    private String parentCtgy;
    /** 是否冻结(0=否，1=是) */
    private String isFreeze;
    /** 标题 */
    private String title;
    /** 关键词 */
    private String keywords;
    /** 创建者ID */
    private Integer createrId;
    /** 创建者名称 */
    private String createrName;
    /** 创建时间 */
    private Date createTime;
    /** 修改者ID */
    private Integer modifierId;
    /** 修改者名称 */
    private String modifierName;
    /** 修改时间 */
    private Date modifyTime;

    public String getSellCtgyId() {
        return sellCtgyId;
    }

    public void setSellCtgyId(String sellCtgyId) {
        this.sellCtgyId = sellCtgyId;
    }

    public String getSellCtgyName() {
        return sellCtgyName;
    }

    public void setSellCtgyName(String sellCtgyName) {
        this.sellCtgyName = sellCtgyName;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getCtgyLvl() {
        return ctgyLvl;
    }

    public void setCtgyLvl(String ctgyLvl) {
        this.ctgyLvl = ctgyLvl;
    }

    public String getParentCtgy() {
        return parentCtgy;
    }

    public void setParentCtgy(String parentCtgy) {
        this.parentCtgy = parentCtgy;
    }

    public String getIsFreeze() {
        return isFreeze;
    }

    public void setIsFreeze(String isFreeze) {
        this.isFreeze = isFreeze;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getModifierId() {
        return modifierId;
    }

    public void setModifierId(Integer modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
