package io.lemur.map.model.amap.base;

import java.io.Serializable;

/**
 * 街道信息
 * @author JueYue
 * @date 2015年1月28日
 */
public class AmapStreetModel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 街道名称
     */
    private String            street;
    /**
     * 号码
     */
    private String            number;
    /**
     * 经纬度
     */
    private String            location;
    /**
     * 方向
     */
    private String            direction;
    /**
     * 距离
     */
    private String            distance;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
