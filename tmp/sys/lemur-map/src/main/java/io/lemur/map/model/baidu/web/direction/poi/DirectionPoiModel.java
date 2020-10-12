package io.lemur.map.model.baidu.web.direction.poi;

import io.lemur.map.model.baidu.web.base.LocationModel;

import java.io.Serializable;

public class DirectionPoiModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 地点名称
     */
    private String            name;
    /**
     * 地点的地址
     */
    private String            address;
    /**
     * 地点的id
     */
    private String            uid;
    /**
     * 地点的电话
     */
    private String            telephone;
    /**
     * 坐标系
     */
    private LocationModel     location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

}
