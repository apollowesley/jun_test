package io.lemur.map.model.baidu.web.placesuggestion;

import java.io.Serializable;

/**
 * 名称搜索详情
 * @author JueYue
 * @date 2015年1月26日
 */
public class PlaceSuggestionDetailModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 名称
     */
    private String            name;
    /**
     * 城市
     */
    private String            city;
    /**
     * 区
     */
    private String            district;
    /**
     * 商圈
     */
    private String            business;
    /**
     * 城市ID
     */
    private String            cityid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

}
