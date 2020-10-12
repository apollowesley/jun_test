package com.kld.bi.dto;

import java.io.Serializable;
import java.util.Date;


public class SysUser implements Serializable{

    private static final long serialVersionUID = -100489537485752374L;
    /**
     * 主键
     */
    private Integer sysUserId;

    /**
     * 商家主键
     */
    private Long sysMerchantId;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 性别 0-女  1-男
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 电话
     */
    private String phone;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 删除标记
     */
    private Integer delFlag;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 邮箱
     */
    private String email;

    /**
     * qq号码
     */
    private String qqCode;

    /**
     * 密码
     */
    private String password;

    /**
     * 工号
     */
    private String no;

    private String startTime;

    private String endTime;


    /**
     * 创建者
     */
    private String createBy;

    private String sysRoleIdArray;

    public String getSysRoleIdArray() {
        return sysRoleIdArray;
    }

    public void setSysRoleIdArray(String sysRoleIdArray) {
        this.sysRoleIdArray = sysRoleIdArray;
    }

    /**
     * 头像url
     */
    private String headUrl;

    private String skin;

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }


    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Long getSysMerchantId() {
        return sysMerchantId;
    }

    public void setSysMerchantId(Long sysMerchantId) {
        this.sysMerchantId = sysMerchantId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getQqCode() {
        return qqCode;
    }

    public void setQqCode(String qqCode) {
        this.qqCode = qqCode == null ? null : qqCode.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }


}