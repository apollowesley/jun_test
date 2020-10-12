package io.lemur.map.model.amap.base;

import java.io.Serializable;

/**
 * 地址详情
 * @author JueYue
 * @date 2015年1月28日
 */
public class AmapAddressComponentModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 省
     */
    private String            province;
    /**
     * 市
     */
    private String            city;
    /**
     * 电话号
     */
    private String            citycode;
    /**
     * 区
     */
    private String            district;
    /**
     * 区号
     */
    private String            adcode;
    /**
     * 镇
     */
    private String            township;

    private AmapStreetModel   streetNumber;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public AmapStreetModel getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(AmapStreetModel streetNumber) {
        this.streetNumber = streetNumber;
    }

}
