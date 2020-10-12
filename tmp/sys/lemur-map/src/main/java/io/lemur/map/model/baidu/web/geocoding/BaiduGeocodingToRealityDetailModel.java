package io.lemur.map.model.baidu.web.geocoding;

import io.lemur.map.model.baidu.web.base.LocationModel;
import io.lemur.map.model.baidu.web.base.PoiModel;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaiduGeocodingToRealityDetailModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 经纬度坐标
     */
    private LocationModel     location;
    /**
     * 结构化地址信息
     */
    @JsonProperty(value = "formatted_address")
    private String            formattedAddress;
    /**
     * 所属商圈
     */
    private String            business;
    /**
     * 兴趣点
     */
    private List<PoiModel>    pois;

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public List<PoiModel> getPois() {
        return pois;
    }

    public void setPois(List<PoiModel> pois) {
        this.pois = pois;
    }

}
