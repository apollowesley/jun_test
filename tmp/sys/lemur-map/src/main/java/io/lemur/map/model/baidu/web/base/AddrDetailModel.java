package io.lemur.map.model.baidu.web.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 地址详情
 * 
 * @author jueyue 2013年7月18日
 */
public class AddrDetailModel {
    /**
     * 所在城市
     */
    private String city;
    /**
     * 城市编码
     */
    @JsonProperty("city_code")
    private String cityCode;
    /**
     * 所在区县名
     */
    private String district;
    /**
     * 所在区县编码
     */
    private String districtCode;
    /**
     * 所在省名
     */
    private String province;
    /**
     * 省编码
     */
    private String provinceCode;
    /**
     * 街道名
     */
    private String street;
    /**
     * 街道门牌号
     */
    @JsonProperty("street_number")
    private String streetNumber;

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

}
