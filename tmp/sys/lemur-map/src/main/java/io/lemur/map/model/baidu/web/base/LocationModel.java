package io.lemur.map.model.baidu.web.base;

import java.io.Serializable;

/**
 * 经纬度信息
 * @author JueYue
 * @date 2015年1月26日
 */
public class LocationModel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 纬度值
     */
    private double lat;
    /**
     * 经度值
     */
    private double lng;
    
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }

}
