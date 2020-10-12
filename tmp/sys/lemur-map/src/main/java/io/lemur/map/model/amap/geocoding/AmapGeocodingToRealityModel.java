package io.lemur.map.model.amap.geocoding;

import io.lemur.map.model.amap.base.AmapBaseModel;

/**
 * 高德地图经纬度解析
 * @author JueYue
 * @date 2015年1月28日
 */
public class AmapGeocodingToRealityModel extends AmapBaseModel {

    /**
     * 
     */
    private static final long                 serialVersionUID = 1L;

    private AmapGeocodingToRealityDetailModel regeocode;

    public AmapGeocodingToRealityDetailModel getRegeocode() {
        return regeocode;
    }

    public void setRegeocode(AmapGeocodingToRealityDetailModel regeocode) {
        this.regeocode = regeocode;
    }

}
