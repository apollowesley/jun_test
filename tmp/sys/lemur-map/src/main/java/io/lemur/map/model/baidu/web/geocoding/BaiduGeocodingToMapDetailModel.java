package io.lemur.map.model.baidu.web.geocoding;

import io.lemur.map.model.baidu.web.base.LocationModel;

import java.io.Serializable;

/**
 * 经纬度地址详情
 * @author JueYue
 * @date 2015年1月27日
 */
public class BaiduGeocodingToMapDetailModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 位置的附加信息，是否精确查找。1为精确查找，0为不精确。
     */
    private String            precise;
    /**
     * 可信度
     */
    private String            confidence;
    /**
     * 地址类型
     */
    private String            level;
    /**
     * 经纬度坐标
     */
    private LocationModel     location;

    public String getPrecise() {
        return precise;
    }

    public void setPrecise(String precise) {
        this.precise = precise;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

}
