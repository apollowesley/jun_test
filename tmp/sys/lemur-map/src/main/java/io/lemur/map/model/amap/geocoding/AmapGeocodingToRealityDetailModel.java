package io.lemur.map.model.amap.geocoding;

import io.lemur.map.model.amap.base.AmapAddressComponentModel;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 经纬度解析详情
 * @author JueYue
 * @date 2015年1月28日
 */
public class AmapGeocodingToRealityDetailModel implements Serializable {

    /**
     * 
     */
    private static final long         serialVersionUID = 1L;

    @JsonProperty("formatted_address")
    private String                    formattedAddress;

    private AmapAddressComponentModel addressComponent;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public AmapAddressComponentModel getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(AmapAddressComponentModel addressComponent) {
        this.addressComponent = addressComponent;
    }

}
