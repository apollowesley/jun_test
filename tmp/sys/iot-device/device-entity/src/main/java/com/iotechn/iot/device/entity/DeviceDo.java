package com.iotechn.iot.device.entity;

import com.iotechn.iot.commons.entity.CommonsDO;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-16
 * Time: 下午12:02
 */
public class DeviceDo extends CommonsDO implements Serializable {
    private Long userId;

    private String secretKey;

    /**
     * 用于用户鉴权
     */
    private String name;

    private String password;

    /**
     * 用户前端显示
     */
    private String title;

    private String description;

    /**
     * 0.不在线 1.在线
     */
    private Integer status;

    private String lastIp;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }
}
