package com.iotechn.iot.device.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-19
 * Time: 下午12:13
 */
public class MqttUserDo implements Serializable {
    private Long id;
    private Long deviceId;
    private String username;
    private String password;
    private String salt;
    private Integer isSuperuser;
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getIsSuperuser() {
        return isSuperuser;
    }

    public void setIsSuperuser(Integer isSuperuser) {
        this.isSuperuser = isSuperuser;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
