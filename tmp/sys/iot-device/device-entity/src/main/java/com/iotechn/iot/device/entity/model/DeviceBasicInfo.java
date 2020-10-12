package com.iotechn.iot.device.entity.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-25
 * Time: 上午11:18
 */
public class DeviceBasicInfo implements Serializable {

    private Integer count;

    private Integer onlineCount;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }
}
