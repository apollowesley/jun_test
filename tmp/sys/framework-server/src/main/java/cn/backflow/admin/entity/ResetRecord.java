package cn.backflow.admin.entity;

import cn.backflow.admin.entity.base.BaseEntity;
import cn.backflow.lib.util.DateUtil;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class ResetRecord extends BaseEntity {

    @Length(max = 128)
    private String email;

    @Length(max = 32)
    private String resetKey;

    private Integer valid = 1;

    private Date requestTime;

    private Date expirationTime;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getResetKey() {
        return this.resetKey;
    }

    public void setResetKey(String value) {
        this.resetKey = value;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getRequestTimeString() {
        return DateUtil.formatDate(getRequestTime());
    }

    public void setRequestTimeString(String value) {
        setRequestTime(DateUtil.parseDate(value));
    }

    public Date getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(Date value) {
        this.requestTime = value;
    }

    public String getExpirationTimeString() {
        return DateUtil.formatDate(getExpirationTime());
    }

    public void setExpirationTimeString(String value) {
        setExpirationTime(DateUtil.parseDate(value));
    }

    public Date getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(Date value) {
        this.expirationTime = value;
    }
}