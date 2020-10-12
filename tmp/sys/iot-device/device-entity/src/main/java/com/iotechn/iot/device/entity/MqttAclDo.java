package com.iotechn.iot.device.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-19
 * Time: 下午12:16
 */
public class MqttAclDo implements Serializable {
    private Long id;
    private Long deviceId;
    private Integer allow;
    private String ipaddr;
    private String username;
    private String clientid;
    /**
     * 1: subscribe, 2: publish, 3: pubsub
     */
    private Integer access;
    private String topic;

    public static final int ACCESS_SUBSCRIBE = 1;

    public static final int ACCESS_PUBLISH = 2;

    public static final int ACCESS_PUBSUB = 3;

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

    public Integer getAllow() {
        return allow;
    }

    public void setAllow(Integer allow) {
        this.allow = allow;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
